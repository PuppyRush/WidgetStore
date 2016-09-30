package page;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;
import javaBean.MemberException;
import javaBean.ManageMember;
import property.commandAction;
import property.enums.member.*;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumSystem;

public class VerifyPage {

	/**
	 * 페이지 마다 요청되는 유저의 권한(가입, 로그인, 개발자등록 여부등의 상태)을 점검한다.
	 * 
	 * @param uId  브라우져의 sessionId
	 * @param from 어느 페이지에서 왔는지를 명시하는 변수
	 * @return	   인증 성공여부 실패 할 경우 가야할 페이지, 오류메시지를 반환한다.
	 * 	(HashMap 형태로 isSuccessVerify, to, messageKind, message를 반환한다)
	 */
	public static HashMap<String,Object> Verify(String uId, enumPage fromPage){
			
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
	
		
		try{				
			enumPage page = fromPage;
			member = Member.getMember(uId);

			switch(page){
			
				case MAIN:
					
					
					break;
					
				case DEVELOPER:
					System.out.println(member.isDeveloper());
					if(!member.isJoin()){
						throw new MemberException(enumMemberState.NOT_JOIN, enumPage.JOIN);
						
					}
					else if(!member.isLogin()){
						throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.LOGIN);
					}
					else if(!member.isDeveloper())
						throw new MemberException(enumMemberState.NOT_DEVELOPER, enumPage.REGSTRY_DEVELOPER);
					
					break;
					
				case SETTINGS:
					
					if(!member.isJoin()){
						throw new MemberException(enumMemberState.NOT_JOIN, enumPage.JOIN);
						
					}
					else if(!member.isLogin()){
						throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.LOGIN);
					}
					
					break;
					
				case STORE:
					
					if(!member.isJoin()){
						throw new MemberException(enumMemberState.NOT_JOIN, enumPage.JOIN);
						
					}
					else if(!member.isLogin()){
						throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.LOGIN);
					}
					
					break;
			
					
				case CUSTOM:
					
					if(!member.isJoin()){
						throw new MemberException(enumMemberState.NOT_JOIN, enumPage.JOIN);
						
					}
					else if(!member.isLogin()){
						throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.LOGIN);
					}
					
					break;
					
				
				case LOGIN_MANAGER:
					
					if(member.getEmail().equals(enumSystem.ADMIN.toString()))
							throw new MemberException(enumMemberState.NOT_ADMIN, enumPage.LOGIN_MANAGER);
					
					
					break;
					
				case MEMBER_MANAGER:
				case WIDGET_MANAGER:
					

					if(!member.getEmail().equals(enumSystem.ADMIN.toString()))
							throw new MemberException(enumMemberState.NOT_ADMIN, enumPage.LOGIN_MANAGER);
					
					
					break;
					
				default:
					
					break;

			
			}//switch
			
		
		
			returns.put("isSuccessVerify", true);
	
		}catch(MemberException e){
			returns.put("isSuccessVerify", false);
			returns.put("to", e.getToPage());
			returns.put("message", e.getMessage());
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}catch(Exception e){
			returns.put("isSuccessVerify", false);
			returns.put("to", "/");		
			returns.put("messageKind", enumCautionKind.INTERNAL_ERROR);
			returns.put("message", "내부오류. 관리자에게 문의하세요.");
			e.printStackTrace();
		} catch (Throwable e) {
			returns.put("isSuccessVerify", false);
			returns.put("to", "/");		
			returns.put("messageKind", enumCautionKind.INTERNAL_ERROR);
			returns.put("message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return returns;
	}
}

