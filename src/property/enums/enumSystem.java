package property.enums;

public enum enumSystem {

	MANIFEST_FULLPATH("/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml"),
	URL_ROOT("http://114.129.211.123:8181"),
	PROJECT_PATH("/home/cmk/workspace/WidgetStore/WebContent/"),
	UPLOAD_PATH("/home/cmk/workspace/WidgetStore/WebContent/upload/"),
	IMAGE_FOLDER_NAME("representiveImages"),
	SOURCE_FOLDER_NAME("source"),
	UPLOAD_FOLDER_NAME("upload"),
	ADMIN("gooddaumi@naver.com");
	
	private String str;
	
	enumSystem(String str){
		this.str = str;
	}
	
	@Override
	public String toString(){
		return str;
	}
	
	
}
