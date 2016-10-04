package property.enums.widget;

public enum enumResolutionKind {

	FIXED("fixed"),
	AUTO("auto"),
	FREE("free"),
	RATIO("ratio");
	
	public String str;
	
	private enumResolutionKind(String str){
		this.str = str;
	}
	
	@Override
	public String toString(){
		return str;
	}
	
	
}
