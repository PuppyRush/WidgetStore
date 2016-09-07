package property;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Member;
import property.enums.enumAttributeKey;

public interface commandAction {
	public HashMap<String, Object> requestPro(
			HttpServletRequest request, HttpServletResponse response)
					throws Throwable;
	

}
