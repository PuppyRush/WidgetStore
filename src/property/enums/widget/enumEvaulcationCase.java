package property.enums.widget;

public enum enumEvaulcationCase {
	
	
	FAILD_MANUAL("1"),
	FAILD_AUTO("2"),
	NO_FILE_ABOUT_MANIFEST("3"),
	NO_FILE_ABOUT_ROOTFILE("4");
	
	
	private String UnallowanceReason;
	
	enumEvaulcationCase(String str){
		UnallowanceReason = str;
	}
	
	public String getString(){
		return UnallowanceReason;
	}
}
