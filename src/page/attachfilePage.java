package page;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import property.commandAction;

	public class attachfilePage implements commandAction{

		@Override
		public HashMap<String, String> requestPro(HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
		
			
			HashMap<String, String> r = new HashMap<String, String>();
			r.put("view", "StoreEditor/pages/trex/file.html");
			return r;
		}
	}

