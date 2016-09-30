package manageStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Member;

import handler.DBhandler;
import javaBean.ConnectMysql;
import property.commandAction;

public class storeDownload implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String, Object> returns = new HashMap<String, Object>();
		Member mdb = new Member();
		
		String widgetId = request.getParameter("widgetId").toString();
		Connection conn = ConnectMysql.getConnector();
		DBhandler dbhandler = new DBhandler();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			/* 유저 불러오는 부분 필요 */
			int u_id = 11;
			
			// 다운된 위젯인지 확인한다.
			String sql = "SELECT widget_id FROM widgetInfo WHERE u_id = "+u_id+" && widget_id = "+widgetId+";";
			rs = dbhandler.executeSQL(sql);
			
			if(!rs.next()){
				sql = "insert into widgetInfo(u_id, widget_id, width, height, x, y) values ('"+u_id+"', '"+widgetId+"', 350, 250, -1, -1);";
				ps = conn.prepareCall(sql);
				ps.executeUpdate();
				System.out.println(widgetId + " download done");
			}
			
		}catch(Exception e){
			System.out.println("안됨");
			e.printStackTrace();
		}
		
		returns.put("id", widgetId);
		returns.put("view", "Store.jsp");
		return returns;

	}
}
