package manageMember;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.memberProcessBean;
import javaBean.memberDataBean;
import property.commandAction;

/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
 *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
*/
public class login implements commandAction {

	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		memberProcessBean lb = new memberProcessBean();
		HashMap<String , String> returns = new HashMap<String , String>();
		memberDataBean mdb = new memberDataBean();
		
		if(request.getParameter("idType") == null)
			throw new Exception( request.getRequestURI() + "에 kind가 없습니다");
		
		if( ((String)request.getParameter("idType")).equals("inner") ){
			
			String nick_or_mail = (String)request.getParameter("login_username");
						
			if( nick_or_mail.contains("@") ){			
				mdb.setEmail(nick_or_mail);
			}
			else{
				mdb.setNickname(nick_or_mail);
			}
			String pw = (String)request.getParameter("login_password");
			mdb.setPassword(pw);			
						
			if(lb.loginMember(mdb)==false){
				returns.put("message", "패스워드가 일치하지 않거나 아이디혹은 메일이 존재하지 않습니다.");
			}
			else{
				
				returns.put("innerLogin", "true");
			}
		}
		
		returns.put("view", "/");
		
		return returns;
		
	}
	
}