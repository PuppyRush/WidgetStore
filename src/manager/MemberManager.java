package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.EnumMap;

import javaBean.ConnectMysql;
import javaBean.EvaluatingWidget;
import javaBean.Member;
import property.enums.member.enumMemberAbnormalState;;

public class MemberManager {

	private static Connection conn = ConnectMysql.getConnector();
	
	public static ArrayList<Member> getAllMember(){
		
		ArrayList<Member> _members = new ArrayList<Member>();
		PreparedStatement _ps=null, __ps=null;
		ResultSet _rs=null, __rs=null;
		
		
		try{
			
			 _ps = conn.prepareStatement("select * from user");
			 _rs = _ps.executeQuery();
			
			while(_rs.next()){
											
				Member m = new Member();
				m.setEmail( _rs.getString("email"));
				m.setId( _rs.getInt("u_id"));
				m.setNickname(_rs.getString("nickname"));
				m.setRegDate(_rs.getTimestamp("registrationDate"));
								
				__ps = conn.prepareStatement("select * from developer where u_id = ?");
				__ps.setInt(1, m.getId());
				__rs = __ps.executeQuery();
				if(__rs.next()){
					m.setDeveloper(true);
					m.setDeveloperId(__rs.getInt("d_id"));					
				}else{
					m.setDeveloper(false);					
				}
				
				__ps = conn.prepareStatement("select * from userState where u_id = ?");
				__ps.setInt(1, m.getId());
				__rs = __ps.executeQuery();
				__rs.next();
				
				EnumMap<enumMemberAbnormalState, Boolean> state = new EnumMap<>(enumMemberAbnormalState.class);
				
				
				if(__rs.getInt("isAbnormal")==1){
					state.put(enumMemberAbnormalState.ABNORMAL, true);
					
					if(__rs.getInt("lostPassword")==1)
						state.put(enumMemberAbnormalState.LOST_PASSWORD, true);
					else
						state.put(enumMemberAbnormalState.LOST_PASSWORD, false);
					
					if(__rs.getInt("failedLogin")==1)
						state.put(enumMemberAbnormalState.FAILD_LOGIN, true);
					else
						state.put(enumMemberAbnormalState.FAILD_LOGIN, false);
					
					if(__rs.getInt("sleep")==1)
						state.put(enumMemberAbnormalState.SLEEP, true);
					else
						state.put(enumMemberAbnormalState.SLEEP, false);
					
					if(__rs.getInt("oldPassword")==1)
						state.put(enumMemberAbnormalState.OLD_PASSWORD, true);
					else
						state.put(enumMemberAbnormalState.OLD_PASSWORD, false);
					
					if(__rs.getInt("joinCertification")==1)
						state.put(enumMemberAbnormalState.JOIN_CERTIFICATION, true);
					else
						state.put(enumMemberAbnormalState.JOIN_CERTIFICATION, false);
				}
				else
					state.put(enumMemberAbnormalState.ABNORMAL, false);
				
			
				m.setAbnormalState(state);
				_members.add(m);
				
			}
			
		
		}catch(Throwable e){
			
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
		return _members;
	}
	
}
