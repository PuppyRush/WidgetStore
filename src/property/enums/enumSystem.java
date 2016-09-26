package property.enums;

public enum enumSystem {

	IMAGE_FOLDER_NAME("representiveImages"),
	SOURCE_FOLDER_NAME("source"),
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
