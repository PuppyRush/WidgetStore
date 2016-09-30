package manageStore;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import com.mysql.fabric.xmlrpc.base.Member;

import property.commandAction;

public class storeLoad implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String, Object> returns = new HashMap<String, Object>();
		// Member mdb = new Member();
		
		String id = (String)request.getParameter("widgetId");
		
		returns.put("id", id);
		returns.put("view", "Store.jsp");

		return returns;

	}
}