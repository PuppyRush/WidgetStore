package store.develop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javaBean.Member;
import javaBean.MemberException;
import property.commandAction;
import property.enums.member.enumMemberState;
import property.enums.enumPage;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

public class UploadWidget implements commandAction {
	
	private String _userPath;
	private String _sourcePath;
	private String _imagePath;
	private String _defaultTempPath;
	private String _defaultPath;
	
	private final int sizeLimit = 10*1024*1024;

	private String _contents, _sessionId, _kind;
	private String _position;
	private String _zipFileName;
	private HashMap<Integer, String> _ImagesName;
	private boolean _isSuccessZipFile; 
	private String _widgetName;
	private Member m;
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		_isSuccessZipFile = false;
		_ImagesName = new HashMap<Integer, String>();
		
		String realFolder = "/upload/"; //properties파일이 저장된 폴더
		ServletContext context = request.getServletContext();
		_defaultPath = context.getRealPath(realFolder);
		
		_defaultTempPath = new StringBuilder(_defaultPath).append("temp/").append( UUID.randomUUID().toString() ).append("/").toString();

		try{
			
		
			File tempFolder = new File(_defaultTempPath);
			if(!tempFolder.exists())
				tempFolder.mkdirs();
					
			MultipartRequest multi = new MultipartRequest(request,_defaultTempPath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
			
		
			if(multi.getParameter("contents")==null){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}
			else if(multi.getParameter("widget-name")== null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}else if(multi.getParameter("sessionId")== null){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}else if(multi.getParameter("kind")==null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}
							
		

			_contents = (String)multi.getParameter("contents");
			_widgetName = (String)multi.getParameter("widget-name");
			_sessionId = (String)multi.getParameter("sessionId");
			_kind = (String)multi.getParameter("kind");
		
			
			//멤버 초기화 함수 만들기 테스트를 위한.
			Member.addMember(Member.makePerfectMember(_sessionId));
	
			m = Member.getMember(_sessionId);
			
			
			_imagePath = (new StringBuilder(_defaultPath)).append("/").append(m.getId()).append("/").append(_widgetName).append("/").append("representiveImages/").toString();
			_sourcePath =  (new StringBuilder(_defaultPath)).append("/").append(m.getId()).append("/").append(_widgetName).append("/").append("source/").toString();
			
			
			File folder = new File(_imagePath);
			if(!folder.exists())
				folder.mkdirs();
			
			folder = new File(_sourcePath);
			if(!folder.exists())
				folder.mkdirs();
			
			if(!m.isJoin())
				throw new MemberException("이상 접근, 가입하지 않은 유저가 접근하였습니다", enumMemberState.NOT_JOIN, enumPage.MAIN);
			
			else if(!m.isLogin())
				throw new MemberException("이상 접근, 로그인하지 않은 유저가 접근하였습니다", enumMemberState.NOT_LOGIN, enumPage.MAIN);
			
			@SuppressWarnings("unchecked")
			int imageNumber=1;
			String[] e = new File(_defaultTempPath).list();
			for(String name : e){ 
				String fullPath = _defaultTempPath + name;
				int duplicatedNum=1;
				File file = new File(fullPath);
				//File file = multi.getFile(name);
				if(!_isSuccessZipFile && name.contains(".zip") || name.contains(".7z")){
					_zipFileName = name;
					_isSuccessZipFile = true;
					fileMove(fullPath, _sourcePath+name);
				}
				else if(name.contains(".jpg") || name.contains(".jpeg") ||
						name.contains(".tif") || name.contains(".bmp") ||  name.contains(".png") ){
				
					String _tempString = 	name.substring(0, name.indexOf("."));
					if(isInteger(_tempString)){
						int _tempInt = Integer.valueOf(_tempString);
						if(_ImagesName.containsKey(_tempInt))
							throw new EvaluationException(enumEvalFailCase.IMAGE_ERROR);
							
						_ImagesName.put(_tempInt, name);
					}
									
					fileMove(fullPath, _imagePath+name);
				}
				
			}
			if(!_isSuccessZipFile){
				throw new EvaluationException("압축파일이 업로드 되지 않았습니다.",enumEvalFailCase.NO_ZIPFILE);
			}		
			else if( _ImagesName.size()>0 &&  !_ImagesName.containsKey(new Integer(1))){
				throw new EvaluationException("대표사진이 없습니다. 대표사진의 이름은 1로 설정하세요.",enumEvalFailCase.NO_IMAGE);
			}		
			else if(_ImagesName.containsKey(new Integer(2))){
				throw new EvaluationException("예제사진이 없습니다.",enumEvalFailCase.NO_IMAGE);
			}
			fileDelete(_defaultTempPath);
		
		}
		catch(EvaluationException e){
			
			//실패결과 통보하기 
			returns.put("view", enumPage.UPDATE_WIDGET.getString());
			returns.put("message", e.getMessage());
			returns.put("isSuccessUpload", "false");
			return returns;
		}
		catch(IOException e){
			returns.put("view", enumPage.UPDATE_WIDGET.getString());
			returns.put("message", "업로드파일 저장중 문제가 발생하였습니다.");
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());
	
			return returns;
		}
		catch(Exception e){
			returns.put("view", enumPage.UPLOAD_WIDGET.getString());
			returns.put("message", "알수 없는 오류 발생.");
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());

			return returns;
		}
		
	
		
		Runnable me = new ManageEvaluation(m, _widgetName,_contents, _zipFileName, _ImagesName,_kind,false );
		Thread t = new Thread(me);
		t.run();
				
		
		
		
		
		returns.put("view", enumPage.DEVELOPER.getString());
		returns.put("isSuccessUpload", "true");

		
		return returns;
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
	
}
