
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
import property.constString;

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
	

	
	private String MailToId(String email){
		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int id;
		try {


			pstmt = conn.prepareStatement("select user_num from user where email = ? ");
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			id = rs.getInt(1);
			if(id == 0)
				throw new SQLException("해당하는 메일이 존재하지 않습니다");
			
			
			
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
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
		
		return String.valueOf(id);
		
		
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

		try {
			conn.setAutoCommit(false);
			
			
			boolean isDuplicated_email = 
					isExist("user", "email" ,member.getEmail() );
			
			boolean isDuplicated_nickname = 
					isExist("user", "nickname" ,member.getNickname() );
			
			if(isDuplicated_email){
				throw new Exception(member.getEmail() + " 중복됩니다" );
			}
			if(isDuplicated_nickname){
				throw new Exception(member.getNickname() + " 중복됩니다" );
			}
			
			
		
			pstmt = conn.prepareStatement(
					"insert into user ( email, nickname, password, regstryDate, isDeveloper) values (?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getNickname());
		
			pstmt.setTimestamp(4, member.getReg_date());
			pstmt.setInt(5, 0);
			
			if(member.getIdType().equals("inner")){
				constString s = constString.salt;
				int salt = Integer.valueOf(s.getString());
				String pw = BCrypt.hashpw( member.getPassword(), BCrypt.gensalt(salt));
				pstmt.setString(3, pw);
			}
			else
				pstmt.setString(3, "");

				pstmt.executeUpdate();
				
				conn.commit();
				
		}
		catch(SQLException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			/*if(conn!=null) 
				try{conn.rollback();}// Exception 발생시 rollback 한다.
					catch(SQLException ex1){
						System.out.println(ex1.getMessage());
						ex1.printStackTrace();
					}*/
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
		
		
	//	conn.commit();
		

		return true;
	}

	/**
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 로그인 무사히 성사됐는지 여부를 반환
	 * @throws  
	 */
	public boolean logonMember(memberDataBean member) throws SQLException {
		
		Statement st;
		ResultSet rs;
		boolean res;
		try {
			res = isExist("UserMapping", "email", member.getEmail());
			
			if(res == false){
				throw new SQLException("값이 존재하지 않습니다");
				
			}
			
			String uniqID = MailToId(member.getEmail());
			
			st = conn.createStatement();
			rs = st.executeQuery(" select Passwd from User where Id = " + uniqID);
			rs.next();
			String hasedpw =  rs.getString(1);
			if( !BCrypt.checkpw(member.getPassword(), hasedpw ) )
				res =  false;
			else
				res = true;
		
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
	
}
