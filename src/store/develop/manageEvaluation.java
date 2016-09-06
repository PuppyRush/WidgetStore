package store.develop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import property.enums.widget.enumWidgetKind;

/**
 * 
 * @author PuppyRush_NB
 *
 * 업로드된 위젯의 등록 및 평가를 위해 다음과 같은 순서로 진행된다.<br>
 * 1. 사용자명과 위젯명을 토대로 기본 폴더 생성 	( /upload/<i>username</i>/<i>widgetname</i>)<br>
 * 2. 제출된 압축파일을 widget 폴더를 생성하여 해제한다.<br>
 * 3. 제출된 대표사진을 representiveImages 폴더를 생성하여 저장한다.<br>
 * 4. 
 */
public class manageEvaluation {

	private static final String defaultPath = "/upload/widget/";
	private static final String defaultImageFolderName = "representiveu'; "
	
	public static void makeDefaultUserFolder(String userName){
		
		
		String realPath = defaultPath + userName;
		try{
		
			File folder = new File(realPath);
			if(!folder.exists())
				folder.mkdirs();
		}
		catch( Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();					
		}
	}

   public static void zipDecompress(String zipFileName, String directory) throws Throwable {
	   
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
   

   private static void createFile(File file, ZipInputStream zis) throws Throwable {
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
	    	
   private static boolean isExistRootFile(String userName,String widgetName){
	   
	   
	   
   }
   
   private static boolean isExistManifest(String userName,String widgetName){
	   
	   StringBuilder realPath = new StringBuilder(defaultPath);
	   realPath.append(userName);
	   realPath.append("/");
	   realPath.append(widgetName);
	   
	   File folder = new File(realPath.toString());
	   if(!folder.exists()){
		   folder.mkdirs();
	   }
	   
	   realPath.append("/");
	   realPath.append("manifest.xml");
	   File file = new File(realPath.toString());
	   
	   if(!file.exists())
		   return false;
	   else
		   return true;
	   
	   
   }
   
}
	

