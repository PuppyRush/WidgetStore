package store.develop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javaBean.Member;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetKind;

/**
 * 
 * @author PuppyRush / gooddaumi@naver.com<br>
 *
 * 업로드된 위젯의 등록 및 평가를 위해 다음과 같은 순서로 진행된다.<br>
 * 1. 사용자명과 위젯명을 토대로 기본 폴더 생성 	( /upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>)<br>
 * 2. 제출된 압축파일을 widget폴더를 생성하여 해제한다.<br>
 * 3. 제출된 대표사진을 representiveImages 폴더를 생성하여 저장한다.<br>
 *   upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>/representiveImages/)<br>
 * 4. 기본적인 파일이 있는지 조사한다. (매니페스트, 루트파일)<br>
 * 5. 참조하는 js에 문제가 없는지 조사한다. (실행이 잘 될 요건의 최소사항.)<br>
 * 6. 매니페스트에 근거하여 위젯정보를 DB에 저장한다.<br>
 * **각 순서에서의 설명은 메서드를 참고.
 */
public class ManageEvaluation implements Runnable{

	private String defaultPath;
	private final String IMAGE_FOLDER_NAME = "RepresentiveImages";
	
	private String zipFileName;
	private String rootPath;
	private enumWidgetKind kind;
	private Member member;
	private String widgetName;
	
	public ManageEvaluation(Member member, String widgetName, String zipFileName, List<String> images){
		
		if(member==null)
			throw new NullPointerException("username 파라메터가 널입니다.");
		if(widgetName==null)
			throw new NullPointerException("widgetName 파라메터가 널입니다.");		
		if(zipFileName==null)
			throw new NullPointerException("zipFileName 파라메터가 널입니다.");
		if(images==null)
			throw new NullPointerException("images 파라메터가 널입니다.");	
		
		this.widgetName = widgetName;
		this.member = member;
		rootPath = (new StringBuilder(defaultPath).append("/").append(userName)).toString();
		
		defaultPath = "/upload/widget/"+widgetName;
	}
		

	public void run(){
		
		try {
			makeDefaultFolder();
			zipDecompress(zipFileName);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void makeDefaultFolder() throws IOException{
		
	
		File folder = new File(rootPath + "/" + IMAGE_FOLDER_NAME +"/");
		if(!folder.exists())
			folder.mkdirs();
		
		folder = new File(rootPath + "/source/");
		if(!folder.exists())
			folder.mkdirs();
	}

   private void zipDecompress(String zipFileName) throws Throwable {
	   
	   String directory = rootPath +"/source/";
	   
	   
	   File zipFile = new File(zipFileName);
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
	    	
   private void makeRepresentiveImages(){

	   
   	}
   
   private boolean isExistRootFile(String rootFileName){
	   	
	   File file = new File(defaultPath+"/source/" + rootFileName );
	   
	   if(!file.exists())
		   return false;
	   else
		   return true;
   	
   	}
   
   private boolean isExistManifest(){
	   

	   File file = new File(defaultPath+"/source/manifest.xml");
	   
	   if(!file.exists())
		   return false;
	   else
		   return true;
	   
	   
   }
   
}
	

