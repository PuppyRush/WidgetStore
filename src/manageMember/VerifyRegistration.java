package manageMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.Member;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.member.enumMemberState;
import property.enums.enumPage;
import property.enums.enumPageError;

/**
 * JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다. 외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면
 * 가입절차를 밟지 않는다.
 */
public class VerifyRegistration implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String , Object> returns = new HashMap<String , Object>();
		try{

			
			if(request.getParameter("email")==null || request.getParameter("number")==null)
				throw new PageException(enumPageError.NO_PARAMATER);
			
			String email = request.getParameter("email");
			String uuid = request.getParameter("number");
			
			if(ManageMember.certificateJoin(request.getRequestedSessionId(), email, uuid)){
				returns.put("view",enumPage.MAIN.toString());
				returns.put("message","가입인증에 성공하셨습니다. 로그인 하세요.");
				returns.put("messageKind", enumCautionKind.NORMAL);
			}
			else
				returns.put("view", enumPage.ERROR404.toString());
				
		}catch(PageException e){
			returns.put("message", e.getErrorMessage());
			returns.put("view",enumPage.ERROR404.toString());
		}
		
		return returns;
		
	}

}
