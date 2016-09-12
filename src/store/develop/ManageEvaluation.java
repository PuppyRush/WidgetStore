package store.develop;

import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;

import javaBean.Member;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
/**
 * 
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
	
	
	private float _version;
	private final String _defaultPath;
	private final String _zipFileName;
	private final String _widgetRoot;
	private enumWidgetKind _kind;
	private enumWidgetPosition _position;
	private final Member _member;
	private String _widgetName;
	private String _repositoryName;
	private String _gitId;
	private String _rootUrl;
	private final HashMap<String, String> _imagesNames;
	private enumWidgetEvaluation _evaluationResult;

	
	public ManageEvaluation(Member member, String widgetName, String zipFileName, HashMap<String, String> images, String kind) throws Exception{
	
		if(member==null || widgetName == null || zipFileName == null || images ==null || kind==null)
			throw new NullPointerException("ManageEvaluation 생성자의 파라메타에 null 존재합니다.");
		
		
		this._kind = null;
		
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equals(kind)){
				this._kind = k;
				break;
			}
		}
		if(this._kind == null){
			throw new Exception("파라미터로 넘어온 position 값이 시스템에 존재하지 않습니다. ");
		}


		this._imagesNames = images;
		this._zipFileName = zipFileName;	
		this._defaultPath = "/home/cmk/workspace/WidgetStore/WebContent/upload/"; 
		this._widgetName = widgetName;
		this._member = member;
		this._widgetRoot = (new StringBuilder(_defaultPath).append(member.getId()).append("/").append(widgetName).append("/")).toString();

		
	}

		

	public void run(){
		
		try {
			
			zipDecompress(_zipFileName);
			check_parseXml();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


   private void zipDecompress(String zipFileName) throws Throwable {
	   
	   String directory = _widgetRoot +"source/";
	   	   
	   File zipFile = new File(directory+zipFileName);
	   FileInputStream fis = null;
	   ZipInputStream zis = null;
	   ZipEntry zipentry = null;
	   try {
		//파일 스트림
		   fis = new FileInputStream(zipFile);
		//Zip 파일 스트림
		   zis = new ZipInputStream(fis);
		//entry가 없을때까지 뽑기
		   while ((zipentry = zis.getNextEntry()) != null) {
			   String filename = zipentry.getName();
			   File file = new File(directory, filename);
        //entiry가 폴더면 폴더 생성
			   if (zipentry.isDirectory()) {
				   file.mkdirs();
			   }else{
				   //파일이면 파일 만들기
				   createFile(file, zis);
			   	}
		   	}
		   fileDelete(directory+zipFileName);
		   
		} catch (Throwable e) {
		    throw e;
		} finally {
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
	      
   private enumWidgetEvaluation check_parseXml(){
	   
	   enumWidgetEvaluation eval = enumWidgetEvaluation.PASS;
	   
	   String _xmlPath = _widgetRoot + "source/manifest.xml";
	   File _mf = new File(_xmlPath );
	   File _origin = new File("/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml");
   	if(!_mf.exists()){
   			enumWidgetEvaluation e = enumWidgetEvaluation.UNALLOWANCE;
   			e.setFailCase(enumEvalFailCase.NO_MANIFEST);
   			return e;
   		}

		try {
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			//_는 사용자의 매니페스트 값을 위한 변수
			
			DocumentBuilderFactory __dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder __dBuilder = __dbFactory.newDocumentBuilder();
         Document __doc = __dBuilder.parse(_origin);
         __doc.getDocumentElement().normalize();
         Element __root = __doc.getDocumentElement();
			
         DocumentBuilderFactory _dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder _dBuilder = _dbFactory.newDocumentBuilder();
         Document _doc = _dBuilder.parse(_xmlPath);
         _doc.getDocumentElement().normalize();
         Element _root = _doc.getDocumentElement();
      
			///// 비교 시작.  기본파일인 /proprty/manifest.xml과 비교하면서 없는 경우 failcase를 맞춰 리턴한다.
         //root
			if(! __root.getNodeName().equals( _root.getNodeName())){
				throw new SAXException(__root.getNodeName()+"이 존재하지 않습니다.");
			}
			
			NodeList __requird = __root.getElementsByTagName("requird").item(0).getChildNodes();
			NodeList _requird = _root.getElementsByTagName("required").item(0).getChildNodes();
			Node _node = _requird.item(0);
			Node __node = __requird.item(0);
			//manifest_version
			if(!__node.getNodeName().equals(_node.getNodeName())){
				throw new SAXException("manifest_version이 존재하지 않습니다.");
			}
			else{
				String __manifest_version = __node.getTextContent();
				String _manifest_version = __node.getTextContent();
				if( !isInteger(_manifest_version) )
					throw new SAXException("매니퍼스트 버전의 값이 올바르지 않습니다.(정수만 입력 가능합니다.)");
								
				if(  Integer.valueOf(__manifest_version)!= Integer.valueOf(_manifest_version))
					throw new SAXException("매니페스트 버전이 현재 위젯스토어의 버전과 일치하지 않습니다.");
			}
			
			enumWidgetEvaluation e = enumWidgetEvaluation.EVALUATING;
			
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(enumEvalFailCase.MANIFEST_ERROR);
			
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(enumEvalFailCase.MANIFEST_ERROR);
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(enumEvalFailCase.MANIFEST_ERROR);
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(enumEvalFailCase.UNKWON_ERROR);
			e1.printStackTrace();
		}
		
	   
   	
		return eval;
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
   
	public static boolean isInteger(String s) {
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
	 
	 private void fileDelete(String deleteFileName) {
		  File I = new File(deleteFileName);
		  I.delete();
		 }
}
	

