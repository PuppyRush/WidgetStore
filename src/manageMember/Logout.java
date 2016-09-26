package manageMember;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.Member;
import javaBean.MemberException;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberAbnormalState;
import property.enums.member.enumMemberState;

/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
 *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
 *  
 *       해당 클래스의 기능순서도는  114.129.211.123/boards/2/topics/64 참고
*/
public class Logout implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
		String nick_or_mail = null;
		
		try{
						
			if(request.getParameter("sessionId")==null)				
				throw new PageException(enumPageError.UNKNOWN_PARAMATER);
			
			member = Member.getMember( (String)request.getParameter("sessionId"));	
			if(!member.isLogin())
				throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.MAIN);
			
			ManageMember.logoutMember(member);
			
			returns.put("view", enumPage.MAIN.getString());	
			returns.put("doLogout", "true");
			returns.put("message", "로그아웃에 성공하셨습니다.");
			returns.put("messageKind", enumCautionKind.NORMAL);
			
		}catch( PageException e){
			returns.put("doLogout", "false");
			returns.put("view", enumPage.MAIN.getString());		
			returns.put("message", "로그아웃에 실패하셨습니다. 관리자에게 문의하세요.");
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}
	
		
		return returns;
	}
	
}