package manageMember;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.Member;
import javaBean.MemberException;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.*;

/**
 *     비밀번호를 분실했을때 
   *  	  
*/
public class Lostpasswd implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		

		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		try{
			if(request.getParameter("email")==null || request.getParameter("session_id")==null)
				throw new PageException(enumPageError.NO_PARAMATER);
							
			String sId = request.getParameter("sessioId");
			String email = (String)request.getParameter("email");
			mdb.setEmail(email);
			if(!mdb.equals( Member.getMember( sId )))
				throw new MemberException(enumMemberState.NOT_SAME_MEMBER, enumPage.MAIN);
			
			
			if(ManageMember.isExist("user", "email", email) == false)
				throw new MemberException(enumMemberState.NOT_EXIST_IN_DB, enumPage.MAIN);		
			else{				
				if(ManageMember.isSendmail(mdb))
					returns.put("view", enumPage.CHECK_PWD_AUTH_NUM.getString());
				else
					returns.put("view", enumPage.INPUT_MAIL_FOR_CERIFICATION.getString());				
			}
						
		}catch(MemberException e){
			returns.put("view", e.getToPage());
			returns.put("message", e.getMessage());
			returns.put("messageKind", enumCautionKind.ERROR);

		}catch(PageException e){
			returns.put("view", enumPage.MAIN.getString());
			returns.put("message", "내부오류. 관리자에게 문의하세요.");
			returns.put("messageKind", enumCautionKind.ERROR);
		
		}
		return returns;
		
	}
	
}
