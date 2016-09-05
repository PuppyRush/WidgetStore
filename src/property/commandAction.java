package property;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;

public interface commandAction {
	public HashMap<String, String> requestPro(
			HttpServletRequest request, HttpServletResponse response)
					throws Throwable;
	
	

}
