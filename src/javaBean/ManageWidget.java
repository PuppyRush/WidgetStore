package javaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
public class ManageWidget {

	private static Connection conn = ConnectMysql.getConnector();
	
	public static void addWidgetToStore(int uId, int wId){
		
	
	}
	
	public static void removeWidget(int uId, int wId){
		
		
		
	}
	
	public static boolean updateWidget(int uId, int wId,float version, String root ,String wName){
		
		PreparedStatement st  = null;
		ResultSet rs = null;
		
	  	try {
	  		conn.setAutoCommit(false);
	  		
	  		st = conn.prepareStatement("select widget_num from widget where developer = ? and title = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	  		st.setInt(1,uId);
	  		st.setString(2, wName);
	  		rs = st.executeQuery();
	  		
	  		rs.last();
	  		if(rs.getRow()!=1)
	  			throw new SQLException();
	  			
	  	
	  		
			st = conn.prepareStatement("update widget set currentVersion = ? , HTML = ? where title = ? and developer = ?  ");
		  	st.setFloat(1,  version);
		  	st.setString(2, root);
		  	st.setString(3, wName);
		  	st.setInt(4, uId);
			 
			st.executeUpdate();		
			
			
			
			//manifest에 업로드.
		  	
		  	conn.commit();
		  
		  	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static ArrayList<HashMap<String,String>> getUserWidgets(String uId){
		ArrayList<HashMap<String,String>> _list = new ArrayList<HashMap<String,String>>(); 
		
		
		
		
		return _list;
	}
	
}
