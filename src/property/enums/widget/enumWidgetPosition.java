package property.enums.widget;

public enum enumWidgetPosition {

	NONE("none"),
	GIT("git");
	
	
	private String kind;
	
	enumWidgetPosition(String str){
		kind = str;
	}
	
	public String getString(){
		return kind;
	}
	
}
