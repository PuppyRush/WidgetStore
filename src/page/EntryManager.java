package page;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.ManageMember;
import javaBean.Member;
import javaBean.MemberException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.enumSystem;
import property.enums.member.enumMemberState;

public class EntryManager implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
	
		HashMap<String , Object> returns = new HashMap<String , Object>();
		returns.put("view", enumPage.LOGIN_MANAGER.toString());
		
		return returns;
	}
}
	
