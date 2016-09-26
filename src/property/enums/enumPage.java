package property.enums;

public enum enumPage {

	ROOT("114.129.211.123:8282"),
	
	ERROR404("/error/404code.jsp"),
	ERROR303("/error/303code.jsp"),
	
	MAIN("Main.jsp"),
	STORE("/Store.jsp"),
	LOGIN("Login.jsp"),	//  /Member 폴더에서 시작.
	JOIN("Join.jsp"),
	CERTIFICATE("verifyRegistration.do"),
		RESET_PASSWORD("Member/ResetPassword.jsp"),
		CERTIFICATE_FAILED_PASSWORD("Member/CertificatePassword.jsp"),
		CHECK_PWD_AUTH_NUM("Member/CheckAuthNum.jsp"),
		INPUT_MAIL_FOR_CERIFICATION("Member/InputMail.jsp"),
		REGSTRY_DEVELOPER("Member/RegistryDeveloper.jsp"),
	
	DEVELOPER("Developer/ManageDeveloper.jsp"),
		UPLOAD_WIDGET("UploadForm.jsp"),		//  /Developer 폴더에서 시작.
		UPDATE_WIDGET("UpdateForm.jsp"),
	
	SETTINGS("Settings.jsp"),
	CUSTOM("Custom.jsp"),
	
	WIDGET_MANAGER("/AdminPage/adminPage_widget.jsp"),
	MEMBER_MANAGER("/AdminPage/adminPage_user.jsp"),
	LOGIN_MANAGER("/AdminPage/AdminLogin.jsp");
	
	private String pageDirection;
	
	enumPage(String str){
		pageDirection = str;
	}
	
	public String getString(){
		return pageDirection;
	}
	
	@Override
	public String toString(){
		return pageDirection;
	}
}
