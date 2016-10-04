package manageMember;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.ManageMember;
import javaBean.Member;
import javaBean.MemberException;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberState;

public class LoginManager implements commandAction{
	
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
		String nick_or_mail = null;
		String userType="";
		String _sId;
		try{
			
			//필요조건
			if(request.getParameter("sessionId")==null || request.getParameter("login_username")==null || request.getParameter("login_password")==null)
				throw new PageException(enumPageError.NO_PARAMATER);
	
			_sId = (String)request.getParameter("sessionId");
			member = Member.getMember(_sId);
			String id = (String)request.getParameter("login_username");
			member.setEmail(id);
			member.setSessionId(_sId);
			member.setPlanePassword( (String)request.getParameter("login_password"));
			
			if(!ManageMember.loginManager(member))
				throw new MemberException(enumMemberState.NOT_EQUAL_PASSWORD, enumPage.LOGIN_MANAGER);
			
			returns.put("view", enumPage.MANAGE_MEMBER.toString());
			
		}catch(MemberException e){
			
			returns.put("view", enumPage.LOGIN_MANAGER.toString());
			returns.put("message", e.getErrCode().getString());
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}
		
		return returns;
		
	
	}
}
