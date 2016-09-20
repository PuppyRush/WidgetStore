package javaBean;

import property.enums.enumMemberState;
import property.enums.enumPage;

public class MemberException extends Exception {
	
	
	private enumPage toPage;
	private final enumMemberState ERR_CODE;// 생성자를 통해 초기화 한다.
	
	public MemberException(String msg, enumMemberState errcode){ //생성자
		super(msg);
		ERR_CODE=errcode;
		toPage = null;
	}
	
	public MemberException(enumMemberState errcode, enumPage page){// 생성자
		this(errcode.getString(), errcode);// ERR_CODE를 100(기본값)으로 초기화한다.
		toPage = page;
	}
	
	public enumPage getToPage(){
		return toPage;
	}
	
	public String getMessage(){
		return super.getMessage();
	}
	
	public enumMemberState getErrCode(){
		return ERR_CODE;
	}
}
