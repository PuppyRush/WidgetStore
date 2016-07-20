
package javaBean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import property.constMysql;

public class memberProcessBean {
	
	private static Connection conn = connectMysql.getConnector();
	private static memberDataBean instance = new memberDataBean();

	public static memberDataBean getInstance() {
		return instance;
	}

	public memberProcessBean() {
	}

	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/basicjsp");
		return ds.getConnection();
	}
	

	/**
	   *   해당 테이블의 속성에 원하는 값이 존재하는지 여부만 조사 
    * select count(*) as size from user where name="hk*" 같은용도의 사용 
	 * @param tableName : 검색할 테이블
	 *	@param  attr : 속성 이름(들)
	 * @param  val : 값 이름(들)
	 * @return 각 속성에 대하여 중복이 발생시 중복결과에 맞게 배열로 그 결과들을 반환함.
	 */	
	public boolean isExist(String tableName, String attr ,Object val)
			throws Exception {

		boolean isDuplicated=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		String sql;

		try {
			switch(attr){
				
			
				case "id" :
	
					sql = " SELECT count(*) FROM user WHERE id = ? ";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, (int)val);
					break;
				
				case "nickname" :
					sql = " SELECT count(*) FROM user where nickname = ? ";
					ps = conn.prepareStatement(sql);
					
					ps.setString(1, (String)val);
					break;
				case "email" :
					sql = " SELECT count(*) FROM user WHERE email = ? ";
					ps = conn.prepareStatement(sql);
					
					ps.setString(1, (String)val);

					break;
					
				default :
					
					ps.setString(3, (String)val);
					
					break;
			
			}
								
				rs = ps.executeQuery();
				
				// 중복아이디가 있는지 결과를 저장
				rs.next();
				
				if ( rs.getInt(1) >=1)
					isDuplicated = true;
				else
					isDuplicated = false;

				
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}

		return isDuplicated;
	}
	

	
	private int somethingToId(String attr, String val){
		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int id;
		try {

			switch(attr){
			
				case "email":
					pstmt = conn.prepareStatement("select user_num from user where email = ? ");
					pstmt.setString(1, attr);
					
					break;
					
				case "nickname":
					pstmt = conn.prepareStatement("select user_num from user where nickname = ? ");
					pstmt.setString(1, attr);
					break;
			
			
			}
			
			
			rs = pstmt.executeQuery();
			rs.next();
			id = rs.getInt(1);
			if(id == 0)
				throw new SQLException("해당하는 " +  attr +" " + val +"이 존재하지 않습니다");
			
			
			
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		
		return id;
		
		
	}

	/**
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 가입이 무사히 성사됐는지 여부를 반환
	 * @throws SQLException 중복, 테이블이 꽉찼는지에 대한 예외가 발생됨 
	 * 홈페이지 내부가입자가 아닌 외부인증을 통하여 가입한 유저는 비밀번호가 공백이다.
	 */
	@SuppressWarnings("resource")
	public boolean joinMember(memberDataBean member) throws SQLException {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		long key=-1;
		
		try {
			conn.setAutoCommit(false);
			
			
			boolean isDuplicated_email = 
					isExist("user", "email" ,member.getEmail() );
			
			boolean isDuplicated_nickname = 
					isExist("user", "nickname" ,member.getNickname() );
			
			if(isDuplicated_email){
				throw new SQLException(member.getEmail() + " 중복됩니다" );
			}
			if(isDuplicated_nickname){
				throw new SQLException(member.getNickname() + " 중복됩니다" );
			}
			
			////////user table
			
			pstmt = conn.prepareStatement(
					"insert into user ( email, nickname, password, regstryDate) values (?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getNickname());
		
			pstmt.setTimestamp(4, member.getReg_date());

			if(member.getIdType().equals("inner")){

				String pw = BCrypt.hashpw( member.getPassword(), BCrypt.gensalt());
				pstmt.setString(3, pw);
			}
			else
				pstmt.setString(3, "");

			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
			    key = rs.getLong(1);
			}	
			else
				throw new SQLException("-");
	
			
			////////userState table
			
			pstmt = conn.prepareStatement("insert into userState (u_num , isBlocked, isDeveloper,"
					+ "blockedReasonCode, lastLoginDate, lastModifiedDate) values(?,?,?,?,?,?)" );
			
			pstmt.setLong(1,key);
			pstmt.setInt(2, 0);
			pstmt.setInt(3,0);
			pstmt.setInt(4,0);
			pstmt.setTimestamp(5, member.getReg_date());
			pstmt.setTimestamp(6, member.getReg_date());
			
			pstmt.executeUpdate();
				
			conn.commit();
				
		}
		catch(SQLException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			if(conn!=null) 
				try{conn.rollback();}// Exception 발생시 rollback 한다.
					catch(SQLException ex1){
						System.out.println(ex1.getMessage());
						ex1.printStackTrace();
					}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		
		
	  conn.commit();
		

		return true;
	}

	/**
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 로그인 무사히 성사됐는지 여부를 반환
	 * @throws  
	 */
	public boolean loginMember(memberDataBean member) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs;
		boolean res=false;
		String uniqID;
		try {
			if(member.getEmail() != null){
				res = isExist("user", "email", member.getEmail() );	
				ps = conn.prepareStatement("select password from user where email = ? ");
				ps.setString(1, member.getEmail());
			}
			else if(member.getNickname() != null ){
				res = isExist("user", "nickname", member.getNickname() );	
				ps = conn.prepareStatement("select password from user where nickname = ? ");
				ps.setString(1, member.getNickname());
			}
			else{
				throw new SQLException("닉네임과 메일이 입력되지 않았습니다.");
			}
			
			if(res == false){
				throw new SQLException("메일 혹은 닉네임이 존재하지 않습니다.");
			}
			
			rs = ps.executeQuery();
			rs.next();
			String hasedpw =  rs.getString(1);
			
			if( BCrypt.checkpw( member.getPassword(), hasedpw ) )
				res =  true;
			else
				res = false;
		
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	
		return res;
	}
	
	
	public boolean createTempPasswd(memberDataBean member){
		
		
		
	
		return true;
	}
	
}
