package manageMember;

import page.PageException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.Member;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberType;

/**
 * JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다. 외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면
 * 가입절차를 밟지 않는다.
 */
public class Join implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member member = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		try{
			if(request.getParameter("register_email")==null || request.getParameter("idType")==null || request.getParameter("register_username")==null ||
					request.getParameter("sessionId")==null || request.getParameter("register_password")==null)
				throw new PageException(enumPageError.NO_PARAMATER);
							
						
			String _type = (String) request.getParameter("idType");
			boolean _isexist=false;
			for(enumMemberType e : enumMemberType.values()){
				if(e.getString().equals(_type)){
					member.setIdType(e);
					_isexist = true;
				}
			}
			if(!_isexist)
				throw new PageException(enumPageError.UNKNOWN_PARA_VALUE);
		
			member = Member.getMember( (String)request.getParameter("sessionId"));	
			member.setId(0);
			member.setEmail((String) request.getParameter("register_email"));
			member.setNickname((String) request.getParameter("register_username"));
			member.setRegDate(new Timestamp(System.currentTimeMillis()));
			
			switch (member.getIdType()) {
					
				case GOOGLE:
				case NAVER:			
					member.setPlanePassword(" ");
					
					break;
		
				case NOTHING:
					member.setPlanePassword((String) request.getParameter("register_password"));
					
					break;
		
				default:
					throw new PageException(enumPageError.UNKNOWN_PARA_VALUE);
										
			}
			
			if (ManageMember.joinMember(member)){
				returns.put("isSuccessJoin", "true");
				returns.put("message", "가입에 성공하였습니다. 메일인증을 하신 후 로그인하세요.");
				returns.put("messageKind", enumCautionKind.NORMAL);
				
			}
			else{
				returns.put("isSuccessJoin", "false");
				returns.put("message","가입에 실패하였습니다.");
				returns.put("messageKind", enumCautionKind.ERROR);
			}
			
			returns.put("view",enumPage.MAIN.getString());
			
			
		}catch(PageException e){
			returns.put("view", enumPage.JOIN.getString());
		}
	return returns;
    }
}
