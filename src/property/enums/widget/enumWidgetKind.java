package property.enums.widget;

public enum enumWidgetKind {

	SPORTS("sports"),
	WEATHER("weather"),
	GAMES("games"),
	SECURITY("security"),
	HEALTH("health"),
	VIDEO("video"),
	FINANCE("finance"),
	TOOL("development tool"),
	BLOG("blog"),
	NEWS("news"),
	SHOPPING("shopping"),
	UTIILTY("utility");
	
	private String kind;
	
	enumWidgetKind(String str){
		kind = str;
	}
	
	public String getString(){
		return kind;
	}
	
	
}