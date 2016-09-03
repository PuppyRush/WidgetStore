package manageMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.MemberProcess;
import javaBean.Member;
import property.commandAction;

/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
 *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
*/
public class Join implements commandAction {

	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		
		Member mdb = new Member();
		HashMap<String , String> returns = new HashMap<String , String>();
		String idType = (String) request.getParameter("idType");

		mdb.setId(0);
		mdb.setEmail((String) request.getParameter("register_email"));
		mdb.setIdType((String) request.getParameter("idType"));
		mdb.setNickname((String) request.getParameter("register_username"));
		mdb.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		switch (idType) {
				
			case "facebook":
			case "google":
			case "naver":
		
				mdb.setPassword(" ");
				
				break;
	
			case "inner":
				
				mdb.setPassword((String) request.getParameter("register_password"));
				
				break;
	
			default:
				returns.put("message", "kind의 종류가 명확하지 않습니다");
				return returns;
				
		}

		if (MemberProcess.joinMember(mdb))		
			returns.put("message", "가입에 성공하였습니다.");
		else
			returns.put("message", "이전에 가입하셨습니다.");
		
		returns.put("view", "/");
		returns.put("innerJoin", "true");
				
	return returns;
    }
}
