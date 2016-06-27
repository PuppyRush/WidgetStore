package property;


public enum constString {
	
	dbId("root"),
	dbPasswd("rksk!@12"),
	dbUrl("jdbc:mysql://localhost:3306/buzzcloud"),
	salt("12"),
	dbDriver("com.mysql.jdbc.Driver");
	
	private String enumStr;
	
	constString(String str){
		enumStr = str;
	}
	
	public String getString(){
		return enumStr;
	}
	
}
