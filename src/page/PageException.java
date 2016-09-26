package page;

import property.enums.enumPageError;

public class PageException extends Exception {

	enumPageError err;
	

	public PageException(enumPageError err){
		super(err.getString());
		this.err = err;
	}
	
	public PageException(String errMsg, enumPageError err){
		super(errMsg);
		this.err = err;
	}
	
	public String getErrorMessage(){
		return err.getString();
	}
}
