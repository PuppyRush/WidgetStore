package page;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import property.commandAction;

public class uploadFormPage implements commandAction{

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
			
		HashMap<String , Object> r = new HashMap<String , Object>();
		r.put("view", "upload-form.jsp");
		//r.put("view", "/StoreEditor/pages/trex/file.jsp");
		return r;
	}
}