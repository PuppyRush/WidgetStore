package manageCustom;

import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//user package
import java.util.*;
import handler.DBhandler;
import javaBean.*;
import property.commandAction;
import property.enums.enumPage;

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

			if (member.getDevelopedWidget() == null && member.getDevelopedWidget().size() <= 0) {

				String sql = "select * from widgetInfo natural join widget where u_id = "
						+ Integer.toString(member.getId()) + ";";
				rs = dbhandler.executeSQL(sql);

				String title = null;
				String html = null;
				String kind = null;
				int num = 0;
				int x = 0;
				int y = 0;
				int w = 0;
				int h = 0;
				int developerId=0;
				boolean remote = false;

				while (rs.next()) {
					num = rs.getInt("widget_id");
					title = rs.getString("title");
					html = rs.getString("HTML");
					kind = rs.getString("kind");
					x = rs.getInt("x");
					y = rs.getInt("y");
					w = rs.getInt("width");
					h = rs.getInt("height");
					developerId = rs.getInt("d_id");

					System.out.println(title + "widget call");

					if (x > -1 && y > -1)
						remote = true;
					else
						remote = false;
					
					returns.put(Integer.toString(num), new widget(num, title, html, x, y, w, h, remote));
					
					member.addDownloadedWidget(new DownloadedWidget.Builder(num,title, kind).setSize(x, y, w, h).developerId(developerId).htmlRoot(html).build());
					
					
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

		returns.put("view", enumPage.CUSTOM.toString());
		return returns;

	}
}