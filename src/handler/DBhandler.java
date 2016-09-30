package handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaBean.ConnectMysql;

public class DBhandler {
	private Connection conn = ConnectMysql.getConnector();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public ResultSet executeSQL(String sql){
		try {
			ps = conn.prepareCall(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB execute error!");
			System.out.println("SQL : " + sql);
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void updateSQL(String sql){
		try {
			ps = conn.prepareCall(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB update error!");
			System.out.println("SQL : " + sql);
			e.printStackTrace();
		}
	}
}
