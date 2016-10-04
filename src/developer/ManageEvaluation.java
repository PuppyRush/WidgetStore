package developer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;

import javaBean.*;
import mail.PostMan;
import property.enums.enumPage;
import property.enums.enumSystem;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.portlet.WindowStateException;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**	최초로 위젯으 업로드하거나 업데이트 위젯이 올라온 경우 이를 평가하기 위한 클래스. 
 *     평가가 진행되는데 일정 시간이 소요 되므로 클라이언트에게는 바로 응답하게 하기 위해 스레드를 이용한다.
 * @author PuppyRush / gooddaumi@naver.com<br>
 *
 * 업로드된 위젯의 등록 및 평가를 위해 다음과 같은 순서로 진행된다.<br>
 * 1. 사용자명과 위젯명을 토대로 기본 폴더 생성 	( /upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>)<br>
 * 2. 제출된 대표사진을 representiveImages 폴더를 생성하여 저장한다.<br> 
 * <b>1,2는 UploadWidget에서 수행한다.</b>
 * 3.  제출된 압축파일을 widget폴더를 생성하여 해제한다.<br>
 * 경로 :  upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>/source/)<br>
 * 4. 기본적인 파일이 있는지 조사한다. (매니페스트, 루트파일)<br>
 * 5. 참조하는 js에 문제가 없는지 조사한다. (실행이 잘 될 요건의 최소사항.)<br> 
 * 6. 매니페스트에 근거하여 위젯정보를 DB에 저장한다.<br>
 * **각 순서에서의 설명은 메서드를 참고.
 */
public class ManageEvaluation implements Runnable{
	
	private HashMap<Integer, String> imagesNames;
	private enumWidgetEvaluation evaluationResult;
	private ManageManifest manifest;
	private final String widgetRoot;
	
	
	private String zipFileName;
	private enumWidgetKind kind;
	private boolean isSuccessZipFile; 
	private String mainImageFullPath;
	private String subImageFullPath;
	
	private final int WidgetId;
	private final String defaultTempUUIDPath;
	private final Member member;
	private final String widgetName;
	private final String contents;
	private final boolean isUpdate;
	Connection conn = ConnectMysql.getConnector();
	
	public ManageEvaluation(Member member, String widgetName,String contents, String tempFolderPath, String kind,int widgetId, boolean isUpdate) throws Exception{
							
		if(member==null || widgetName == null || contents==null || tempFolderPath ==null)
			throw new NullPointerException("ManageEvaluation 생성자의 파라메타에 null 존재합니다.");
	
		this.kind = null;		
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equalsIgnoreCase(kind)){
				this.kind = k;
				break;
			}
		}
		this.WidgetId = widgetId;
		imagesNames = new HashMap<Integer,String>();
		widgetRoot =  (new StringBuilder(enumSystem.UPLOAD_PATH.toString()).append(member.getId()).append("/").append(widgetName).append("/")).toString();
		
		this.contents = contents;
		this.widgetName = widgetName;
		this.member = member;
		this.defaultTempUUIDPath = tempFolderPath;
		this.isUpdate = isUpdate;
		
		conn = ConnectMysql.getConnector();
	}


	public void beginSetFiles() throws EvaluationException, IOException, Throwable{

		String originImagePath = widgetRoot+enumSystem.IMAGE_FOLDER_NAME.toString()+"/";
		String originSourcePath = widgetRoot+enumSystem.SOURCE_FOLDER_NAME.toString()+"/";
		
		String[] e = new File(defaultTempUUIDPath).list();
		for(String name : e){ 
			String tempFileFullPath = defaultTempUUIDPath + name;
							
			//File file = multi.getFile(name);
			if(!isSuccessZipFile && name.contains(".zip")){
				zipFileName = name;
				isSuccessZipFile = true;
				fileMove(tempFileFullPath, originSourcePath+name);
			}
			else if(name.contains(".jpg") ||name.contains(".JPG") || name.contains(".jpeg") ||name.contains(".JPEG") ||
					name.contains(".tif") ||name.contains(".TIF") || name.contains(".bmp") || name.contains(".BMP") ||  name.contains(".png") || name.contains(".PNG") ){
			
				String _tempString = 	name.substring(0, name.indexOf("."));
				if(isInteger(_tempString)){
					int _tempInt = Integer.valueOf(_tempString);
					if(imagesNames.containsKey(_tempInt))
						throw new EvaluationException(enumEvalFailCase.IMAGE_ERROR);
						
					imagesNames.put(_tempInt, name);
				}
								
				fileMove(tempFileFullPath, originImagePath+name);
			}
		}
		mainImageFullPath = (widgetRoot+enumSystem.IMAGE_FOLDER_NAME.toString()+"/"+imagesNames.get(1)).split("WebContent")[1];
		subImageFullPath =( widgetRoot+enumSystem.IMAGE_FOLDER_NAME.toString()+"/"+imagesNames.get(2)).split("WebContent")[1];
		zipDecompress(zipFileName, originSourcePath);
		
		if(!isSuccessZipFile){
			throw new EvaluationException("압축파일이 업로드 되지 않았습니다.",enumEvalFailCase.NO_ZIPFILE);
		}		
		if(isUpdate){
			if( imagesNames.size()>0 &&  !imagesNames.containsKey(new Integer(1))){
				throw new EvaluationException("대표사진이 없습니다. 대표사진의 이름은 1로 설정하세요.",enumEvalFailCase.NO_IMAGE);
			}		
			else if(!imagesNames.containsKey(new Integer(2))){
				throw new EvaluationException("예제사진이 없습니다.",enumEvalFailCase.NO_IMAGE);
			}
			
		}
		
	}
		
	public void run(){
		
	
		enumWidgetEvaluation eval = enumWidgetEvaluation.EVALUATING;
		try {			
			beginSetFiles();
			
			manifest = new ManageManifest(widgetRoot, widgetName);
			
			
			eval = manifest.doCollectAll();

			if(isUpdate){
				
				eval = manifest.VerifyUpdatedWidget(member.getDeveloperId(), eval);
				if(eval.equals(enumWidgetEvaluation.UNALLOWANCE))
					throw new EvaluationException(eval.getFailCase());
				

				eval = ManageWidget.addUpdatingWidget(this, eval, WidgetId);

			}
			else	
				eval = ManageWidget.addEvaluatingWidget(this, eval);
			
		
			
		}catch(EvaluationException e){
			e.printStackTrace();
			
			fileDelete(widgetRoot);
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());

		}
		catch (Exception e){
			e.printStackTrace();
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());
			fileDelete(widgetRoot);
		}
		
		catch (Throwable e) {
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());
			fileDelete(widgetRoot);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fileDelete(defaultTempUUIDPath);
			
			if(eval.equals( enumWidgetEvaluation.PASS))
				PostMan.sendSuccessAutoEvaluation(widgetName, member.getNickname(), member.getEmail());
			else
				PostMan.sendFailEvaluation(widgetName, member.getNickname(), eval.getErrMsg(), member.getEmail());
		}
				
		
	}
	
   private void zipDecompress(String zipFileName, String outFolder) throws IOException, Throwable{
	   

	   File zipFile = new File(outFolder+zipFileName);
	   FileInputStream fis = null;
	   ZipInputStream zis = null;
	   ZipEntry zipentry = null;
	   try {
		//파일 스트림
		   System.out.println(zipFile);
		   fis = new FileInputStream(zipFile);
		//Zip 파일 스트림
		   zis = new ZipInputStream(fis);
		//entry가 없을때까지 뽑기
		   while ((zipentry = zis.getNextEntry()) != null) {
			   String filename = zipentry.getName();
			   File file = new File(outFolder, filename);
        //entiry가 폴더면 폴더 생성
			   if (zipentry.isDirectory()) {
				   file.mkdirs();
			   }else{
				   //파일이면 파일 만들기
				   createFile(file, zis);
			   	}
		   	}
		   fileDelete(outFolder+zipFileName);
		   
		}finally {
	    if (zis != null)
	        zis.close();
	    if (fis != null)
	        fis.close();
		}
   	  }
   

   private void createFile(File file, ZipInputStream zis) throws Throwable {
        //디렉토리 확인
        File parentDir = new File(file.getParent());
        //디렉토리가 없으면 생성하자
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        //파일 스트림 선언
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[256];
            int size = 0;
            //Zip스트림으로부터 byte뽑아내기
            while ((size = zis.read(buffer)) > 0) {
                //byte로 파일 만들기
                fos.write(buffer, 0, size);
            }
        } catch (Throwable e) {
            throw e;
        }
   }
	      
  
   public void wholeHtmlPaser(String url){
			
		org.jsoup.nodes.Document doc = null;
		try {
			doc = Jsoup.connect("https://raw.githubusercontent.com/PuppyRush/WidgetStore/master/WebContent/Item.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}


   private static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}


   private void fileDelete(String deleteFileName) {

	   
		  File I = new File(deleteFileName);
		  I.delete();
		 }
	
	private void fileMove(String inFileName, String outFileName) {
	try {
		
	   FileInputStream fis = new FileInputStream(inFileName);
	   FileOutputStream fos = new FileOutputStream(outFileName);
	   
	   int data = 0;
	   while((data=fis.read())!=-1) {
	    fos.write(data);
	   }
	   fis.close();
	   fos.close();
	   
	   //복사한뒤 원본파일을 삭제함
	   fileDelete(inFileName);
	   
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 }
   
	
	public HashMap<Integer, String> getImagesNames() {
		return imagesNames;
	}
	
	
	public enumWidgetEvaluation getEvaluationResult() {
		return evaluationResult;
	}


	public ManageManifest getManifest() {
		return manifest;
	}


	public String getWidgetRoot() {
		return widgetRoot;
	}


	public String getZipFileName() {
		return zipFileName;
	}


	public enumWidgetKind getKind() {
		return kind;
	}


	public boolean isSuccessZipFile() {
		return isSuccessZipFile;
	}


	public String getDefaultTempUUIDPath() {
		return defaultTempUUIDPath;
	}


	public Member getMember() {
		return member;
	}


	public String getWidgetName() {
		return widgetName;
	}


	public String getContents() {
		return contents;
	}


	public boolean isUpdate() {
		return isUpdate;
	}


	public Connection getConn() {
		return conn;
	}


	public String getMainImageFullPath() {
		return mainImageFullPath;
	}


	public String getSubImageFullPath() {
		return subImageFullPath;
	}
	}

	

