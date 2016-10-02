package manageStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.DBhandler;
import javaBean.ConnectMysql;
import javaBean.Member;
import javaBean.MemberException;
import page.PageException;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberState;

public class storeDownload implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		HashMap<String, Object> returns = new HashMap<String, Object>();
		
		String widgetId = request.getParameter("widgetId").toString();
		Connection conn = ConnectMysql.getConnector();
		DBhandler dbhandler = new DBhandler();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sId = request.getRequestedSessionId();
		
		try{
			if(!Member.isContainsMember(sId))
				throw new MemberException(enumMemberState.NOT_EXIST_MEMBER_FROM_MAP, enumPage.MAIN);
			
			Member member = Member.getMember(sId);
			if(!member.isJoin())
				throw new MemberException(enumMemberState.NOT_JOIN, enumPage.JOIN);
			if(!member.isLogin())
				throw new MemberException(enumMemberState.NOT_LOGIN, enumPage.LOGIN);
			
		

			/* 유저 불러오는 부분 필요 */
			int u_id = member.getId();
			
			// 다운된 위젯인지 확인한다.
			String sql = "SELECT widget_id FROM widgetInfo WHERE u_id = "+u_id+" && widget_id = "+widgetId+";";
			rs = dbhandler.executeSQL(sql);
			
			if(!rs.next()){
				sql = "insert into widgetInfo(u_id, widget_id, width, height, x, y) values ('"+u_id+"', '"+widgetId+"', 350, 250, -1, -1);";
				ps = conn.prepareCall(sql);
				ps.executeUpdate();
				System.out.println(widgetId + " download done");
			}
			
			returns.put("view", enumPage.STORE.toString());
				
		}catch(MemberException e){
			returns.put("isSuccessVerify", false);
			returns.put("view", e.getToPage().toString());
			returns.put("from", enumPage.STORE.toString());
			returns.put("message", e.getErrCode().getString());
			returns.put("messageKind", enumCautionKind.ERROR);
		}
			catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			returns.put("view", enumPage.MAIN.toString());
		}
		
		returns.put("id", widgetId);
		
		return returns;

	}
}
