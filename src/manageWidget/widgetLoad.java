package manageWidget;

import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//user package
import java.util.*;
import handler.DBhandler;
import javaBean.*;
import property.commandAction;

public class widgetLoad implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		HashMap<String, Object> returns = new HashMap<String, Object>();

		// declare DB object
		DBhandler dbhandler = new DBhandler();
		ResultSet rs = null;

		try {
			/* 유저 불러오는 부분 필요 */
			Member member = Member.getMember(request.getRequestedSessionId());

			if (member.getDevelopedWidget() == null) {

				String sql = "select * from widgetInfo natural join widget where u_id = "
						+ Integer.toString(member.getId()) + ";";
				rs = dbhandler.executeSQL(sql);

				String title = null;
				String html = null;
				int num = 0;
				int x = 0;
				int y = 0;
				int w = 0;
				int h = 0;
				boolean remote = false;

				while (rs.next()) {
					num = rs.getInt("widget_id");
					title = rs.getString("title");
					html = rs.getString("HTML");
					x = rs.getInt("x");
					y = rs.getInt("y");
					w = rs.getInt("width");
					h = rs.getInt("height");

					System.out.println(title + "widget call");

					if (x > -1 && y > -1)
						remote = true;
					else
						remote = false;

					// System.out.println(code);

					// class widget
					// int num, String name, String tag, int pX, int pY, int
					// width, int height, boolean remote
					returns.put(Integer.toString(num), new widget(num, title, html, x, y, w, h, remote));
				} // end of while
			}else		 
				for(DownloadedWidget w :  member.getDownloadedWidgetList())						
					returns.put( String.valueOf(w.getWidgetId()), 
							new widget(w.getWidgetId(), w.getWidgetName(), w.getHtmlRoot(), 
							w.getX(), w.getY(), w.getWidth(), w.getHeight(), 
							w.getX()>-1 && w.getY()>-1)) ;
	
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		returns.put("view", "Custom.jsp");
		return returns;

	}
}