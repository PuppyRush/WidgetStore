package manageCustom;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.DBhandler;
import javaBean.DownloadedWidget;
import javaBean.Member;
import property.commandAction;
import property.enums.enumPage;

public class widgetSave implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String, Object> returns = new HashMap<String, Object>();

		Member member = Member.getMember(request.getRequestedSessionId());

		// form에서 정보를 꺼낸다.
		String[] widget_id = ((String) request.getParameter("widgetNum")).split(",");
		String[] x = ((String) request.getParameter("widgetPx")).split(",");
		String[] y = ((String) request.getParameter("widgetPy")).split(",");

		// DB를 연결한다.
		DBhandler dbhandler = new DBhandler();
		ResultSet rs = null;

		// 자주 사용하는 변수를 미리 선언한다.
		String sql = null;
		String px = null;
		String py = null;
		String num = null;
		int u_id = member.getId();
		ArrayList<Integer> listUserWidget = new ArrayList<>();

		
		// 유저가 가지고 있는 위젯의 리스트를 가져온다.
		if (member.getDownloadedWidgetList() == null) {
			try {
				sql = "select widget_id from widgetInfo natural join widget where u_id = " + Integer.toString(u_id)
						+ ";";
				rs = dbhandler.executeSQL(sql);

				while (rs.next()) {
					listUserWidget.add(rs.getInt("widget_id"));
					// System.out.print(rs.getString("widget_id"));
				}

			} catch (Exception e) {
				System.out.println("fail to load user widget id");
				e.printStackTrace();
			}
		} else {

			for (DownloadedWidget w : member.getDownloadedWidgetList()) 
				listUserWidget.add(w.getWidgetId());
			
		}

		// 이제 저장을 시작한다.
		if (u_id != -1)
			try {
				// 보드에 있는 위젯을 처리한다.
				for (int i = 0; i < widget_id.length; i++) {
					num = widget_id[i];

					if (num.equals(""))
						continue;
					//listUserWidget.remove(Integer.valueOf(num));
					// 문자열 내부의 px를 제거
					px = x[i].replace("px", "");
					py = y[i].replace("px", "");
					

					sql = "update widgetInfo set x = " + px + ", y = " + py + " where u_id = " + Integer.toString(u_id)
							+ "&& widget_id =" + num + ";";
					dbhandler.updateSQL(sql);
					
			
					for(DownloadedWidget widget : member.getDownloadedWidgetList()){
						if(String.valueOf(widget.getWidgetId()).equals(num)){
							widget.setPoint(Integer.valueOf(px), Integer.valueOf(py));
							break;
						}
					}
				
							
					
					
				}

				// 리모콘에 있는 위젯을 처리한다.
			/*	for (int w : listUserWidget) {
					// System.out.println(w + " / -1 / -1");
					sql = "update widgetInfo set x = -1, y = -1 where u_id = " + Integer.toString(u_id)
							+ "&& widget_id =" + w + ";";
					dbhandler.updateSQL(sql);
				}*/
				
				
				
			} catch (Exception e) {
				System.out.println("fail to save widget");
				e.printStackTrace();
			}
		returns.put("view", enumPage.CUSTOM.toString());
		return returns;
	}
}