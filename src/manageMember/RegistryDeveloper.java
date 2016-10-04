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

public class RegistryDeveloper implements commandAction{
	
	
	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
		String _sId = request.getRequestedSessionId();
		try{
			
			//필요조건
				
			member = Member.getMember(_sId);
			
			ManageMember.registryDeveloper(member);
		
			if(request.getParameter("to")!=null){
				returns.put("view", request.getParameter("to"));
			}
			else{
			
				returns.put("view", enumPage.DEVELOPER.toString());
			}
			returns.put("message", "개발자로 등록에 성공하였습니다.");
			returns.put("messageKind", enumCautionKind.NORMAL);
			
		}catch(MemberException e){
			e.printStackTrace();
			returns.put("view", e.getToPage().toString());
			returns.put("message", e.getErrCode().getString());
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}
		catch(Exception e){
			e.printStackTrace();
			returns.put("view", enumPage.MAIN.toString());
			returns.put("message", e.getMessage());
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}
		
		return returns;
		
	
	}
}
