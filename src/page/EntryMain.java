package page;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;
import javaBean.MemberException;
import property.commandAction;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberState;

	public class EntryMain implements commandAction{

		@Override
		public HashMap<String, Object> requestPro(HttpServletRequest request,
				HttpServletResponse response) {
						
			HashMap<String , Object> r = new HashMap<String , Object>();
			
			try{
		
				String sId = request.getRequestedSessionId();
				Member member = Member.getMember(sId);
				
				if(!Member.isContainsMember(sId))
					throw new MemberException(enumMemberState.NOT_EXIST_MEMBER_FROM_MAP, enumPage.MAIN);
				
				if(!member.isLogin())
					throw new MemberException("로그인 한 유저가 아닙니다.", enumMemberState.NOT_LOGIN, enumPage.MAIN);
				
			}
			catch(MemberException e){
				r.put("initSession", true);
			}catch(Exception e){
				r.put("initSession", true);
			}
			
			r.put("view", enumPage.MAIN.getString());
			
			return r;
		}
	}

