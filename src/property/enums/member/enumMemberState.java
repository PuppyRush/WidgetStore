package property.enums.member;

public enum enumMemberState
{
	NOT_SAME_MEMBER("equals함수 결과 두 멤버가 일치하지 않습니다."),
	NOT_EXIST_IN_DB("db에 해당하는 유저가 존재하지 않습니다."),
	NOT_EXIST_MEMBER_FROM_MAP("내부오류. MAP에 멤버변수가 존재하지 않음."),
	NOT_LOGIN("로그인하지 않으셨습니다."),
	NOT_JOIN("가입하지 않으셨습니다."),
	NOT_LOGOUT("로그아웃되어있지 않습니다."),
	NOT_DEVELOPER("개발자로 등록이 되어 있지 않습니다.  등록 후 사용하세요"),
	NOT_ADMIN("관리자가 아닙니다."),
	NOT_CERTIFICATION("가입로 메일인증을 하지 않았습니다."),
	NOT_EQUAL_PASSWORD("비밀번호가 일치하지 않습니다"),
	ERROR("unknow error");
	
	private String enumStr;
	
	enumMemberState(String str){
		enumStr = str;
	}
	
	public String getString(){
		return enumStr;
	}
}