package property.enums;

public enum enumPage {

	MAIN("Main.jsp"),
	STORE("/Store.jsp"),
	LOGIN("Login.jsp"),	//  /Member 폴더에서 시작.
	JOIN("Join.jsp"),
	REGSTRY_DEVELOPER("Member/RegistryDeveloper.jsp"),
	DEVELOPER("Developer/ManageDeveloper.jsp"),
		UPLOAD_WIDGET("UploadWidget.jsp"),		//  /Developer 폴더에서 시작.
		UPDATE_WIDGET("UpdateWidget.jsp"),
	SETTINGS("Settings.jsp"),
	CUSTOM("Custom.jsp");
	
	
	private String pageDirection;
	
	enumPage(String str){
		pageDirection = str;
	}
	
	public String getString(){
		return pageDirection;
	}
	
}
