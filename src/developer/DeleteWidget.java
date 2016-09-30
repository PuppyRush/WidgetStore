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

public class DeleteWidget implements commandAction {
	

	private String _sourcePath;
	private String _imagePath;
	private String _defaultTempPath;
	private String _defaultPath;
	
	private final int sizeLimit = 10*1024*1024;

	private String contents, sessionId;

	private String zipFileName;
	private boolean isUpdate;
	private String widgetName;
	private Member member;
	private String kind;
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
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
			}else if(multi.getParameter("isUpdate")==null ){
				throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");
			}
							
		

			
			
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
