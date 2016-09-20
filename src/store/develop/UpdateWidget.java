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
import property.enums.enumMemberState;

/**
 * 
 *    개발자가 기존의 위젯을 업데이트하는 행위를 위한 클래 
 *    압축파일에 manifest와 소스파일을 기본이며 선택적으로 이미지 파일이 업로드 되면 기존의 대표사진을 새로 교체한다. 
 * @author cmk  
 *
 */
public class UpdateWidget implements commandAction {
	
	private String _userPath;
	private String _sourcePath;
	private String _imagePath;
	private String _defaultTempPath;
	private String _defaultPath;
	
	private final int sizeLimit = 10*1024*1024;

	private String _contents, _sessionId, _kind;
	private String _position;
	private String _zipFileName;
	private Map<String, String> _ImagesName;
	private boolean _isSuccessZipFile; 
	private String _widgetName;
	private Member m;
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		_isSuccessZipFile = false;
		_ImagesName = new HashMap<String, String>();
		
		String realFolder = "/upload/"; //properties파일이 저장된 폴더
		ServletContext context = request.getServletContext();
		_defaultPath = context.getRealPath(realFolder);
		
		_defaultTempPath = new StringBuilder(_defaultPath).append("temp/").append( UUID.randomUUID().toString() ).append("/").toString();

		try{
			
		
			File tempFolder = new File(_defaultTempPath);
			if(!tempFolder.exists())
				tempFolder.mkdirs();
					
			MultipartRequest multi = new MultipartRequest(request,_defaultTempPath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
			
		
			if(multi.getParameter("contents")==null || multi.getParameter("widget-name")== null ||multi.getParameter("sessionId")== null 
					||	multi.getParameter("kind")==null )
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");

			_contents = (String)multi.getParameter("contents");
			_widgetName = (String)multi.getParameter("widget-name");
			_sessionId = (String)multi.getParameter("sessionId");
			_kind = (String)multi.getParameter("kind");
		
			
			//멤버 초기화 함수 만들기 테스트를 위한.
			Member.addMember(Member.makePerfectMember(_sessionId));
	
			m = Member.getMember(_sessionId);
			
			
			_imagePath = (new StringBuilder(_defaultPath)).append("/").append(m.getId()).append("/").append(_widgetName).append("/").append("representiveImages/").toString();
			_sourcePath =  (new StringBuilder(_defaultPath)).append("/").append(m.getId()).append("/").append(_widgetName).append("/").append("source/").toString();
		
			
			if(!m.isJoin())
				throw new MemberException("이상 접근, 가입하지 않은 유저가 접근하였습니다", enumMemberState.NOT_JOIN);
			
			else if(!m.isLogin())
				throw new MemberException("이상 접근, 로그인하지 않은 유저가 접근하였습니다", enumMemberState.NOT_LOGIN);
			
			@SuppressWarnings("unchecked")
			String[] e = new File(_defaultTempPath).list();
			for(String name : e){ 
				String fullPath = _defaultTempPath + name;
				int duplicatedNum=1;
				File file = new File(fullPath);
				//File file = multi.getFile(name);
				if(!_isSuccessZipFile && file.getName().contains(".zip") || file.getName().contains(".7z")){
					_zipFileName = name;
					_isSuccessZipFile = true;
					fileMove(fullPath, _sourcePath+name);
				}
				else if(file.getName().contains("jpg") || file.getName().contains("jpeg") ||
					file.getName().contains("tif") || file.getName().contains("bmp") || file.getName().contains("png") ){
					if(!_ImagesName.containsKey(name))
						_ImagesName.put(name,name);
					else{
						duplicatedNum++;
						name = name+String.valueOf(duplicatedNum);
						_ImagesName.put(name,name);
					}
						
					fileMove(fullPath, _imagePath+name);
				}
				
			}
			if(_ImagesName.size()==0)
				throw new IOException("대표사진이 없습니다");
			else if(!_isSuccessZipFile){
				throw new IOException("압축파일이 전송되지 않았습니다.");
			}
						
			fileDelete(_defaultTempPath);
		
		}
		
		catch(IOException e){
			returns.put("view", "/Develop/Registration/RegistrationGit");
			returns.put("message", "업로드파일 저장중 문제가 발생하였습니다.");
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return returns;
		}
		catch(Exception e){
			returns.put("view", "/Develop/Registration/RegistrationGit");
			returns.put("message", "알수 없는 오류 발생.");
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return returns;
		}
		
	
		
		Runnable me = new ManageEvaluation(m, _widgetName, _zipFileName, (HashMap<String,String>)_ImagesName,_kind,true);
		Thread t = new Thread(me);
		t.run();
				
		
		returns.put("view", "/Develop/Registration/RegistrationGit");
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
}
