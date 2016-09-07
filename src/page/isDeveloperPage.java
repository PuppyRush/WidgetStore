package page;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;
import javaBean.MemberProcess;
import property.commandAction;

	public class isDeveloperPage implements commandAction{

		@Override
		public HashMap<String, Object> requestPro(HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
				
			HashMap<String , Object> returns = new HashMap<String , Object>();
			Member member = null;
			
			if(request.getParameter("sessionId")==null){
				returns.put("is_successLogin", "false");
				returns.put("view", "/");		
				throw new NullPointerException("sessionId값이 서버로 전송되지 않았습니");
			}else
				member = Member.getMember( (String)request.getParameter("sessionId"));
			
			boolean isLogin = (int)MemberProcess.getSthJustOne("userstate", "u_num", member.getId(), "isLogin") == 1 ? true : false;
			if(!isLogin){
				returns.put("view", "Login.jsp");
				returns.put("from", "isDeveloper");
				return returns;
			}
			
			boolean isDeveloper = (int)MemberProcess.getSthJustOne("userstate", "u_num", member.getId(), "isDeveloper") == 1 ? true : false;
			
			//테스트//
			isDeveloper=true;
			//////////////
			
			if(isDeveloper)
				returns.put("view", "Develop.jsp");
			else
				returns.put("view", "entryDevelop.jsp");					
			
			return returns;
		}
	}

