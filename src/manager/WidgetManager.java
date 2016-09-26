package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javaBean.ConnectMysql;
import javaBean.EvaluatingWidget;
import javaBean.Member;

public class WidgetManager {


	private static Connection conn = ConnectMysql.getConnector();
	
	public static ArrayList<EvaluatingWidget> getAllWidgets(){
		
		ArrayList<EvaluatingWidget> _widgets = new ArrayList<EvaluatingWidget>();
		PreparedStatement _ps=null, __ps=null;
		ResultSet _rs=null, __rs=null;
		
		try{
			
			
		//_ps = conn.prepareStatement("select developer, widget_num, evalNum, evalState, evaluationBeginDate,developer,isUpdate, title, kind, registartionPosition from widgetEvaluation join widget");
			 _ps = conn.prepareStatement("select * from widgetEvaluation join widget");
			 _rs = _ps.executeQuery();
			
			if(_rs.next()){
				
				int wNum= _rs.getInt("widget_num");
				String name = _rs.getString("title");
				int evalNum = _rs.getInt("evalNum");
				String kind = _rs.getString("kind");
				Timestamp date = _rs.getTimestamp("evaluationBeginDate");
				String pos = _rs.getString("registrationPosition");
				int developerId = _rs.getInt("developer");
				boolean isUpdate = _rs.getInt("isUpdate")==1 ? true : false; 
				
				 __ps = conn.prepareStatement("widget_root from WidgetDetail where widget_num = ?");
				__ps.setInt(1, wNum);
				__rs = __ps.executeQuery();
				__rs.next();
				String widgetRoot = __rs.getString("widget_root");
				__ps.close();
				__rs.close();

				__ps = conn.prepareStatement("nickname, d_id from user join developer where d_id = ?");
				__ps.setInt(1, developerId);
				__rs = __ps.executeQuery();
				__rs.next();
				String nickname = __rs.getString("nickname");
				int devId = __rs.getInt("d_id");		

				
				EvaluatingWidget w = new EvaluatingWidget(wNum, name, kind, date, widgetRoot, pos, evalNum, isUpdate,devId, nickname);				
				_widgets.add(w);
				
			}
			
		
		}catch(SQLException e){
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
				}
			if (__ps != null)
				try {
					__ps.close();
				} catch (SQLException ex) {
				}
			if (__rs != null)
				try {
					__rs.close();
				} catch (SQLException ex) {
				}
		}
		return _widgets;
	}
	
}
