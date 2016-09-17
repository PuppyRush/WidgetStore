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

public class VerifyMemberStateFromPage {

	/**
	 * 페이지 마다 요청되는 유저의 권한(가입, 로그인, 개발자등록 여부등의 상태)을 점검한다.
	 * 
	 * @param uId  브라우져의 sessionId
	 * @param from 어느 페이지에서 왔는지를 명시하는 변수
	 * @param to   어느 페이지로 갈 것인지 명시하는 변수
	 * @return	   인증 성공여부 실패 할 경우 가야할 페이지, 오류메시지를 반환한다.
	 * 	(HashMap 형태로 isSuccessVerify, to, messageKind, message를 반환한다)
	 */
	public HashMap<String,Object> Verify(String uId,String from, String to){
			
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
	
		
		try{				
			
			member = Member.getMember(uId);
			
			member.setLogin(true);
			member.setJoin(true);
			member.setDeveloper(true);
			
			
			switch(from){
				
				case "main":
					
					switch(to){
					
					case "custom":
						

						if(!member.isJoin()){
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.JOIN);
							
						}
						else if(!member.isLogin()){
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.LOGIN);
						}
						
						returns.put("to", to);
						
						break;
						
					case "developer":
					
						if(!member.isJoin())
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.JOIN);
													
						else if(!member.isLogin())
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.LOGIN);
						
						else if(!member.isDeveloper())
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.REGSTRY_DEVELOPER);
						else{
							
							//개발자의 개발위젯 정보들(위젯번호, 위젯 이름, 업데이트 날짜, 위젯 설명 정보, 대표사진 이름)
												
							
							
						}
						break;
						
					case "settings":

						if(!member.isJoin())
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.JOIN);
													
						else if(!member.isLogin())
							throw new MemberException(enumMeberState.NOT_JOIN, enumPage.LOGIN);
						
						returns.put("to", enumPage.SETTINGS.getString());
						
						break;
					default:
						
						returns.put("to", enumPage.MAIN.getString());
						returns.put("message", "내부오류. ");
						
						break;


					}//switch to
					
					break;//case main
					
				case "custom":
					
					break;					
					
				default:
					
					break;;
			
				
			}
		
			returns.put("isSuccessVerify", true);
	
		}catch(MemberException e){
			returns.put("isSuccessVerify", false);
			returns.put("to", e.getToPage());
			returns.put("message", e.getMessage());
			returns.put("messageKind", enumCautionKind.ERROR);
			
			e.printStackTrace();
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
		
		returns.put("from", from);
		return returns;
	}
}

