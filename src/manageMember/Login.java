package manageMember;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.Member;
import javaBean.MemberException;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.member.*;
import property.enums.enumPage;
import property.enums.enumPageError;


/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
 *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
 *  
 *       해당 클래스의 기능순서도는  114.129.211.123/boards/2/topics/64 참고
*/
public class Login implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
	
		HashMap<String , Object> returns = new HashMap<String , Object>();
		Member member = null;
		String nick_or_mail = null;
		String userType="";
		String _sId;
		try{
			//필요조건
			if(request.getParameter("sessionId")==null || request.getParameter("idType")==null || request.getParameter("login_username")==null ||
					request.getParameter("login_password")==null)
				throw new PageException(enumPageError.NO_PARAMATER);
			
			nick_or_mail = (String)request.getParameter("login_username");
					
			//가입여부 확인
			_sId = (String)request.getParameter("sessionId");
			member = Member.getMember(_sId);
			if( nick_or_mail.contains("@") )
				member.setEmail(nick_or_mail);
			else
				member.setNickname(nick_or_mail);
			
			if(!ManageMember.isMember(member))
				throw new MemberException("가입되지 않은 유저입니다. 가입후 사용허세요. ", enumMemberState.NOT_JOIN, enumPage.MAIN);
			
			//멤버객체 설정
			
			if( nick_or_mail.contains("@") )				
				Member.setMemberFromDB_Email(member, _sId, nick_or_mail);			
			else
				Member.setMemberFromDB_Nickname(member, _sId, nick_or_mail);
			member.setPlanePassword((String)request.getParameter("login_password"));		
			
			//가입 방식에 따라 달리 처리한다.
			userType = (String)request.getParameter("idType");
			if( userType.equals( enumMemberType.NOTHING.getString() ) ){
	
				EnumMap<enumMemberAbnormalState, Boolean> state = ManageMember.getMemberStates(member);
				//잠김상태인가?
				if( state.containsKey(enumMemberAbnormalState.ABNORMAL) &&  state.get(enumMemberAbnormalState.ABNORMAL)){
					
					//아직 인증 메일을 하지 않은 경우
					if(state.get(enumMemberAbnormalState.JOIN_CERTIFICATION)){
						returns.put("view", enumPage.MAIN.getString());
						returns.put("message", "메일 인증 후 로그인 해주세요.");
					}
					//비밀번호 분실상태인가?
					//아래의 두 상태는 비밀번호 일치여부를 검사할 필요 없음.
					if( state.get(enumMemberAbnormalState.LOST_PASSWORD) ){
						returns.put("userState","lostpw");		
						
						if(ManageMember.isSendmail(member))
							returns.put("view", enumPage.CHECK_PWD_AUTH_NUM.getString());
						else
							returns.put("view", enumPage.INPUT_MAIL_FOR_CERIFICATION.getString());
						
						
						return returns;
	
					}
					//비밀번호 초과상태인가
					else if(state.get(enumMemberAbnormalState.FAILD_LOGIN) ){
						
						returns.put("userState","faild_login");
						returns.put("excessFaildcount","true");
						
						if(ManageMember.isSendmail(member))
							returns.put("view", enumPage.CHECK_PWD_AUTH_NUM.getString());
						else
							returns.put("view", enumPage.INPUT_MAIL_FOR_CERIFICATION.getString());
						
						
						return returns;
					}
							
					//잠김 상태중에서도 아래 두가지는 확인이 필요함.
					if(state.get(enumMemberAbnormalState.LOST_PASSWORD )){
					
						//로그인 실패하면 3개월 이상 변경여부 검사 안함
						if(ManageMember.loginMember(member)==false){
							returns.put("message", "패스워드가 일치하지 않거나 아이디혹은 메일이 존재하지 않습니다.");
						}
						else{
							
							if(ManageMember.isPassingDate(member)){
								returns.put("excessDateOfChange", "ture");
								returns.put("view", enumPage.RESET_PASSWORD.getString());
							}
							else{
								returns.put("innerLogin", "false");
								returns.put("view", enumPage.MAIN.getString());
							}
						}
						
					}
					else if(state.get(enumMemberAbnormalState.SLEEP) ){
					
						
						
					}
					
					
				}//isn't Locking member and Success login process.
				else if(state.containsKey(enumMemberAbnormalState.ABNORMAL) && state.get(enumMemberAbnormalState.ABNORMAL) == false){
				
					if(ManageMember.loginMember(member)==false){
						returns.put("message", "패스워드가 일치하지 않거나 아이디혹은 메일이 존재하지 않습니다.");
						returns.put("isSuccessLogin", "false");
						returns.put("messageKind", enumCautionKind.ERROR);
					}
					else{
						member.setSessionId((String)request.getParameter("sessionId"));
						returns.put("message", "로그인에 성공하셨습니다.");
						returns.put("messageKind", enumCautionKind.NORMAL);
						returns.put("isSuccessLogin", "true");
						returns.put("id", nick_or_mail);
					}
					
					returns.put("view", enumPage.MAIN.getString());				
					
				}	
	
				
			}//userType = nothing
			else if(userType.equals( enumMemberType.GOOGLE.getString() ) ){
				
			}
			else if(userType.equals( enumMemberType.NAVER.getString() ) ){
				
			}
			else
				returns.put("view", enumPage.ERROR404);
			
		}catch(PageException e){
			returns.put("is_successLogin", "false");
			returns.put("view", enumPage.MAIN.getString());		
			returns.put("message", "로그인에 실패하였습니다");
			returns.put("messageKind", enumCautionKind.ERROR);
		
		}catch(MemberException e){
			returns.put("view", e.getToPage().getString());	
			returns.put("message", e.getMessage());
			returns.put("messageKind", enumCautionKind.ERROR);
			
		}catch(SQLException e){
			returns.put("view", enumPage.MAIN.getString());
			returns.put("message", "로그인에 실패하였습니다");
			returns.put("messageKind", enumCautionKind.ERROR);
		}catch(Exception e){
			returns.put("view", enumPage.MAIN.getString());
			returns.put("message", "알수없는 오류 발생. 관리자에게 문의하세요");
			returns.put("messageKind", enumCautionKind.ERROR);
		}

		
		return returns;
	}
	
}