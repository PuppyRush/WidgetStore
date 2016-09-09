package store.develop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javaBean.Member;
import javaBean.MemberException;
import property.commandAction;
import property.enums.*;

public class UploadWidget implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		
		String _contents, _gitUrl, _gitRepo, _gitId, sessionId;
		final String _widgetName;
		
		try{
			
		
	
			int sizeLimit = 1024*1024*15;
			
			MultipartRequest multi = new MultipartRequest(request,"/home/cmk/workspace/WidgetStore/WebContent/upload/TemporaryUploadWidget/", sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
			
		
			if(multi.getParameter("contents")==null || multi.getParameter("git-url")==null || 
					multi.getParameter("git-id")==null || multi.getParameter("repository-name")==null || multi.getParameter("widget-name")!= null)
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");

			_contents = (String)multi.getParameter("contents");
			_gitUrl= (String)multi.getParameter("git-url");
			_gitId= (String)multi.getParameter("git-id");
			_gitRepo = (String)multi.getParameter("repository-name");
			_widgetName = (String)multi.getParameter("widget-name");
			sessionId = (String)multi.getParameter("sessionId");
			
			@SuppressWarnings("unchecked")
			Enumeration<String> e = multi.getFileNames();
			while(e.hasMoreElements()){
				String name = e.nextElement();
				if(multi.getFile(name) == null)
					throw new IOException(name + " 파일 업로드에 실패하였습니다");
				
			}
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
		
		if(!Member.isContainsMember(sessionId))
			throw new MemberException(enumMemberState.NOT_EXIST_MEMBER_FROM_MAP);
		
		Member m = Member.getMember(sessionId);
		
		if(!m.isJoin())
			throw new MemberException("이상 접근, 가입하지 않은 유저가 접근하였습니다", enumMemberState.NOT_JOIN);
		
		else if(!m.isLogin())
			throw new MemberException("이상 접근, 로그인하지 않은 유저가 접근하였습니다", enumMemberState.NOT_LOGIN);
		
		
		returns.put("view", "/Develop/Registration/RegistrationGit");
		returns.put("isSuccessUpload", "true");
		
		
		
		ManageEvaluation me = new ManageEvaluation(m.getNickname(), _widgetName);
		
		return returns;
	}
	

	public static void wholeHtmlPaser(String url){
			
		Document doc = null;
		try {
			doc = Jsoup.connect("https://raw.githubusercontent.com/PuppyRush/WidgetStore/master/WebContent/Item.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
}
