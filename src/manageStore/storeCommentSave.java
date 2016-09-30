package manageStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Member;

import javaBean.ConnectMysql;
import property.commandAction;

public class storeCommentSave implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String, Object> returns = new HashMap<String, Object>();
		Member mdb = new Member();
		
		Connection conn = ConnectMysql.getConnector();
		PreparedStatement ps = null;
		
		String widgetId = request.getParameter("widgetId").toString();
		String comment = request.getParameter("comment").toString();
		String userId = request.getParameter("userId").toString();
		String grade = request.getParameter("grade").toString();
		
		// System.out.println(widgetId + " / " + comment + " / " + userId + " / " + grade);
		// insert into post (widget_num, comment, review, u_id) values ('1', 'testing', 0, 3);
		String sql = "insert into post (widget_id, comment, review, u_id) values ('"+ widgetId +"', '"+comment+"', "+ grade +", "+userId+");";
		
		ps = conn.prepareCall(sql);
		ps.executeUpdate();
		
		sql = "update widgetDetail set count = count +1, totalReview = totalReview +"+grade+" where widget_id = "+widgetId+";";
		ps = conn.prepareCall(sql);
		ps.executeUpdate();
		
		returns.put("id", widgetId);
		returns.put("view", "Store.jsp");
		return returns;

	}
}