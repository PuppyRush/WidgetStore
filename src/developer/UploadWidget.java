package developer;

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
import mail.PostMan;
import property.commandAction;
import property.enums.member.enumMemberState;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

public class UploadWidget implements commandAction {
	

	private String _sourcePath;
	private String _imagePath;
	private String _defaultTempPath;
	private String _defaultPath;
	
	private final int sizeLimit = 10*1024*1024;

	private String contents, sessionId;
	private int widgetId;
	private String zipFileName;
	private boolean isUpdate;
	private String widgetName;
	private Member member;
	private String kind;
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		
		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
	

		try{
			

			String realFolder = "/upload/"; //properties파일이 저장된 폴더
			ServletContext context = request.getServletContext();
			_defaultPath = context.getRealPath(realFolder);
			
			_defaultTempPath = new StringBuilder(_defaultPath).append("temp/").append( UUID.randomUUID().toString() ).append("/").toString();			
		
			File tempFolder = new File(_defaultTempPath);
			if(!tempFolder.exists())
				tempFolder.mkdirs();
					
			MultipartRequest multi = new MultipartRequest(request,_defaultTempPath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
			
		
			if(multi.getParameter("contents")==null){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}
			else if(multi.getParameter("widget-name")== null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}else if(multi.getParameter("sessionId")== null){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}else if(multi.getParameter("kind")==null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}else if(multi.getParameter("isUpdate")==null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}
							
		

			contents = (String)multi.getParameter("contents");
			widgetName = (String)multi.getParameter("widget-name");
			sessionId = (String)multi.getParameter("sessionId");
			kind = (String)multi.getParameter("kind");
			isUpdate = multi.getParameter("isUpdate").equals("true");
			if(multi.getParameter("widgetId")==null)
				widgetId = -1;
			else
				widgetId = Integer.valueOf((String)multi.getParameter("widgetId"));
			
			//멤버 초기화 함수 만들기 테스트를 위한.
			//Member.addMember(Member.makePerfectMember(sessionId));
	
			member = Member.getMember(sessionId);
			
			
			_imagePath = (new StringBuilder(_defaultPath)).append("/").append(member.getId()).append("/").append(widgetName).append("/").append("representiveImages/").toString();
			_sourcePath =  (new StringBuilder(_defaultPath)).append("/").append(member.getId()).append("/").append(widgetName).append("/").append("source/").toString();
			
			
			File folder = new File(_imagePath);
			if(!folder.exists())
				folder.mkdirs();
			
			folder = new File(_sourcePath);
			if(!folder.exists())
				folder.mkdirs();
			
			if(!member.isJoin())
				throw new MemberException("이상 접근, 가입하지 않은 유저가 접근하였습니다", enumMemberState.NOT_JOIN, enumPage.MAIN);
			
			else if(!member.isLogin())
				throw new MemberException("이상 접근, 로그인하지 않은 유저가 접근하였습니다", enumMemberState.NOT_LOGIN, enumPage.MAIN);
		
			
			
		}

		catch(IOException e){
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());
			returns.put("view", enumPage.DEVELOPER.getString());
			returns.put("message", "업로드파일 저장중 문제가 발생하였습니다.");
			returns.put("messageKind", enumCautionKind.ERROR);
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return returns;
		}
		catch(Exception e){
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());
			returns.put("view", enumPage.DEVELOPER.getString());
			returns.put("message", "알수 없는 오류 발생.");
			returns.put("messageKind", enumCautionKind.INTERNAL_ERROR);
			returns.put("isSuccessUpload", "false");
			e.printStackTrace();
			return returns;
		}finally{
			
		}
		
	
		
		ManageEvaluation me = new ManageEvaluation(member, widgetName,contents, _defaultTempPath,kind,widgetId, false);
		Thread t = new Thread(me);
		t.run();

		returns.put("message","위젯 업로드가 완료되었습니다. 심사가 완료되면 개발자 페이지에 위젯이 추가 되고 메일로 결과가 전송됩니다.");
		returns.put("messageKind", enumCautionKind.NORMAL);
		returns.put("view", enumPage.DEVELOPER.toString());
		returns.put("isSuccessUpload", "true");

		
		return returns;
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
