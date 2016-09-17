package page;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;
import javaBean.MemberException;
import javaBean.MemberProcess;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumMemberState;
import property.enums.enumPage;

	public class EntryFromMain implements commandAction{

		@Override
		public HashMap<String, Object> requestPro(HttpServletRequest request,
				HttpServletResponse response) {
				
			HashMap<String , Object> returns = new HashMap<String , Object>();
			Member member = null;
			String from = null;
			
			try{
					
				
				if(request.getParameter("sessionId_entryForm")==null){
					returns.put("view", "/");		
					returns.put("message", "내부오류. 관리자에게 문의하세요.");
					throw new NullPointerException("sessionId값이 서버로 전송되지 않았습니다.");
				}else
					member = Member.getMember( (String)request.getParameter("sessionId_entryForm"));
				
				if(request.getParameter("from")==null){
					
					throw new NullPointerException("referer값이 서버로 전송되지 않았습니다.");
				}
				else 
					from = (String)request.getParameter("from");
				
	
				member.setLogin(true);
				member.setJoin(true);
				member.setDeveloper(true);
				
				
				switch(from){
					
					case "custom":
						
						returns.put("view", enumPage.CUTSOM);
						
						break;
						
					case "developer":
					
						if(!member.isJoin()){
							returns.put("message", enumMemberState.NOT_JOIN);
							returns.put("view", enumPage.JOIN);
						}
						else if(!member.isLogin()){
							returns.put("message", enumMemberState.NOT_LOGIN);
							returns.put("view", enumPage.LOGIN);
						}
						else if(!member.isDeveloper()){
							returns.put("message", enumMemberState.NOT_DEVELOPER);
							returns.put("view", enumPage.REGSTRY_DEVELOPER);
						}
						else
							returns.put("view", enumPage.DEVELOPER.getString());
						
						break;
						
					case "settings":
						if(!member.isJoin()){
							returns.put("message", enumMemberState.NOT_JOIN);
							returns.put("view", enumPage.JOIN);
						}
						else if(!member.isLogin()){
							returns.put("message", enumMemberState.NOT_LOGIN.getString() );
							returns.put("view", enumPage.LOGIN);
						}
						
						returns.put("view", enumPage.SETTINGS.getString());
						
						break;
					default:
						
						returns.put("view", enumPage.MAIN.getString());
						returns.put("message", "내부오류. ");
						
						break;
				}
			
		
			}catch(MemberException e){
				returns.put("view", "/");		
				returns.put("messageKind", enumCautionKind.ERROR);
				returns.put("message", "내부오류. 관리자에게 문의하세요.");
				e.printStackTrace();
			}catch(Exception e){
				returns.put("view", "/");		
				returns.put("messageKind", enumCautionKind.INTERNAL_ERROR);
				returns.put("message", "내부오류. 관리자에게 문의하세요.");
				e.printStackTrace();
			} catch (Throwable e) {
				returns.put("view", "/");		
				returns.put("messageKind", enumCautionKind.INTERNAL_ERROR);
				returns.put("message", e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			returns.put("from", from);
			return returns;
		}
	}

