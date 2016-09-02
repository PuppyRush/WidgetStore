package manageMember;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.MemberProcessBean;
import javaBean.MemberDataBean;
import property.commandAction;
import property.constUserstate;

/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
   *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
*/
public class ChangePasswd implements commandAction {

	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		

		MemberDataBean mdb = new MemberDataBean();
		HashMap<String , String> returns = new HashMap<String , String>();
	
		if(request.getParameter("email") == null)
			throw new NullPointerException("이메일값이 클라이언트로부터 전송되지 않았습니다");

		mdb.setEmail((String)request.getParameter("email"));
		
		
		return returns;
		
	}
	
}
