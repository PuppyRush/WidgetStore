package property.enums.widget;

import java.util.HashMap;

public enum enumWidgetEvaluation {

	
	
	
	PASS("1"),
	EVALUATING("2"),
	UNALLOWANCE("3");
	
	private enumEvalFailCase failcase;
	private String state;
	private String errMsg;
	
	enumWidgetEvaluation(String str){
		state = str;
		failcase = enumEvalFailCase.NOTHING;
	}

	public String getErrMsg(){
		return errMsg;
	}
	
	public void setErrMsg(String str){
		errMsg = str;
	}
	
	public String getString(){
		return state;
	}
	
	public void setFailCase(enumEvalFailCase f){
		failcase = f;
	}

	public enumEvalFailCase getFailCase(){
		return failcase;
	}
	
	public static enum enumEvalFailCase {
		
		NOTHING("1"),
		NO_ZIPFILE("2"),
		NO_IMAGE("2"),
		NO_ROOTFILE("2"),
		NO_MANIFEST("2"),
		MANIFEST_ERROR("3"),
		IMAGE_ERROR("3"),
		UNKWON_ERROR("4");
		
		
		
		private String UnallowanceReason;
		
		enumEvalFailCase(String str){
			UnallowanceReason = str;
		}
		
		public String getString(){
			return UnallowanceReason;
		}
	}

	
}
