package property.enums;

public enum enumMemberState
{
	NOT_EXIST_MEMBER_FROM_MAP("내부오류. MAP에 멤버변수가 존재하지 않음."),
	NOT_LOGIN("로그인하지 않으셨습니다."),
	NOT_JOIN("가입하지 않으셨습니다."),
	NOT_LOGOUT("로그아웃되어있지 않습니다."),
	NOT_DEVELOPER("개발자로 등록이 되어 있지 않습니다. 개발자로 등록 후 이용해주세요."),
	ERROR("unknow error");
	
	private String enumStr;
	
	enumMemberState(String str){
		enumStr = str;
	}
	
	public String getString(){
		return enumStr;
	}
}