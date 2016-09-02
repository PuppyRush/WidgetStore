
package javaBean;


import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;
import property.*;


public class MemberProcessBean {
	
	private static Connection conn = ConnectMysql.getConnector();
	private static MemberDataBean instance = new MemberDataBean();

	public static MemberDataBean getInstance() {
		return instance;
	}

	public MemberProcessBean() {
	}

	public static int isLockingMember(MemberDataBean mdb){

		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			
			int u_num = -1;
			
			if(mdb.getEmail() != null)
				u_num = somethingToId("email",mdb.getEmail());
			else if(mdb.getNickname() != null)
				u_num = somethingToId("nickname",mdb.getNickname());
			else
				throw new Exception("메일, 닉네임 값 모두 존재하지 않습니다.");
			
			ps = conn.prepareStatement("select isAbnormal, abnormalCode from userState where u_num = ?");
			ps.setInt(1, u_num);			
			rs = ps.executeQuery();
			
			if(rs.next()){
				if(rs.getInt("isAbnormal")!=0){
					int code = rs.getInt("abnormalCode");			
					return code;
				}
				else
					return 0;		
							
			}

			
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
			if (st != null)
				try {
					st.close();
				} catch (SQLException ex) {
				}
		}
		
		return 0;
		
	}

	
	/**
	   *   해당 테이블의 속성에 원하는 값이 존재하는지 여부만 조사 
    * select count(*) as size from user where name="hk*" 같은용도의 사용 
	 * @param tableName : 검색할 테이블
	 *	@param  attr : 속성 이름(들)
	 * @param  val : 값 이름(들)
	 * @return 각 속성에 대하여 중복이 발생시 중복결과에 맞게 배열로 그 결과들을 반환함.
	 */	
	public static boolean isExist(String tableName, String attr ,Object val)
			throws Exception {

		boolean isDuplicated=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		String sql;

		try {
			
			switch(tableName){
				case "user":
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
					break;
					
				case "widget":
					
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
	

	
	public static int somethingToId(String attr, String val){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		int id;
		try {

			switch(attr){
			
				case "email":
					pstmt = conn.prepareStatement("select user_num from user where email = ? ");
					pstmt.setString(1, val);
					
					break;
					
				case "nickname":
					pstmt = conn.prepareStatement("select user_num from user where nickname = ? ");
					pstmt.setString(1, val);
					break;
			
			
			}
			
			
			rs = pstmt.executeQuery();
			rs.next();
			id = rs.getInt(1);
			if(id == 0)
				throw new SQLException("해당하는 " +  attr +" " + val +"이 존재하지 않습니다");
			
			
			
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
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
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
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
	public static boolean joinMember(MemberDataBean member) throws SQLException {

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
					"insert into user ( email, nickname, password, registrationDate) values (?,?,?,?)",
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
/*			pstmt.setInt(2, 0);
			pstmt.setInt(3,0);
			pstmt.setInt(4,0);*/
			pstmt.setTimestamp(5, member.getReg_date());
			/*pstmt.setTimestamp(6, member.getReg_date());*/
			
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
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}
		
			

		return true;
	}

	/**
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 로그인 무사히 성사됐는지 여부를 반환
	 * @throws  
	 */
	public static boolean loginMember(MemberDataBean member) throws SQLException {
		
		Statement st = conn.createStatement();
		PreparedStatement ps = null;
		ResultSet rs=null;
		boolean res=false;
		int u_num=-1;
		try {				
			
			if(member.getEmail() != null){
				res = isExist("user", "email", member.getEmail() );	
				ps = conn.prepareStatement("select password from user where email = ? ");
				ps.setString(1, member.getEmail());
						
				u_num = somethingToId("email", member.getEmail());	
			}
			else if(member.getNickname() != null ){
				res = isExist("user", "nickname", member.getNickname() );	
				ps = conn.prepareStatement("select password from user where nickname = ? ");
				ps.setString(1, member.getNickname());
				
				u_num = somethingToId("nickname", member.getNickname());		
			}
			else{
				throw new SQLException("닉네임과 메일이 입력되지 않았습니다.");
			}
			
			member.setId(u_num);
			
			if(res == false){
				throw new SQLException("메일 혹은 닉네임이 존재하지 않습니다.");
			}
			
			rs = ps.executeQuery();
			rs.next();
			String hasedpw =  rs.getString(1);
			
			//비밀번호 일치.
			if( BCrypt.checkpw( member.getPassword(), hasedpw ) ){
				
				res =  true;
				
				/// 로그인횟수 증가
				
				int loginCount=+1;				
				rs = st.executeQuery("select loginCount from userState where u_num = " + u_num);	

				if(rs.next())
					loginCount = rs.getInt("loginCount");
				else
					throw new SQLException("해당하는 " + u_num + " 이 존재하지 않습니다");
				
				loginCount+=1;				
				setSomethingJustOne("userState", "u_num", u_num, "loginCount" , loginCount);
				st.executeUpdate("update userState set loginCount = " + loginCount 
						+ "where u_num = " + u_num);
	
				////// 마지막 로그인 날짜 갱신 
				
				Timestamp t = new Timestamp(System.currentTimeMillis());
				st.executeUpdate("update userState set lastLoginDate = " + t 
						+ "where u_num = " + u_num);
				
				//실패횟수 초기화			
				setSomethingJustOne("userState", "u_num", member.getId(), "faildLoginCount", 0);
				
				//잠금상태 해제 
				setSomethingJustOne("userState", "u_num", member.getId(), "faildLoginCount", 0);
				
				
			}
			//불일치
			else{
				res = false;
				
				int failedLoginCount=-1;				
				//rs = st.executeQuery("select failedLoginCount from userState where u_num = " + u_num);	
				failedLoginCount = (int)getSomethingJustOne("userState", "u_num", u_num, "failedLoginCount");
				
				failedLoginCount +=1;	
				setSomethingJustOne("userState","u_num", u_num, "failedLoginCount", failedLoginCount);
				//st.executeUpdate("update userState set failedCountLogin = " + failedCountLogin + " where u_num = " + u_num);
				
				if(failedLoginCount >= 5){
					
					setSomethingJustOne("userState", "u_num", member.getId(), "isAbnormal", "true");
					int code = (int)getSomethingJustOne("userState", "u_num", member.getId(), "abnormalCode");
					code = code & Integer.valueOf(constUserstate.FAILD_LOGIN.getString());
					setSomethingJustOne("userState", "u_num", member.getId(), "abnoramlCode", code);
					
				}
				
			}
		
		}catch(SQLException ex){
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
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (st != null)
				try {
					st.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}

		return res;
	}


	/**
	 * 
	 * 
	 * @param mdb
	 * @return	3개월 이상 지났다면 true 아니면 false
	 */
	public static boolean isPassingDate(MemberDataBean mdb){
		
		Timestamp time = (Timestamp)MemberProcessBean.getSomethingJustOne("losingPasswd", "u_num", mdb.getId(), "sendedMailDate");
		//int dateGap = Calendar.getInstance().get
																 
		Date today = new Date ( );
		Calendar cal = Calendar.getInstance ( );
		cal.setTime ( today );// 오늘로 설정. 
		 
		 
		Calendar cal2 = Calendar.getInstance ( );
		cal2.setTime(time);
	 
		 
		int count = 0;
		while ( !cal2.after ( cal ) )
		{
			count++;
			cal2.add ( Calendar.DATE, 1 ); // 다음날로 바뀜					
		}
		
		int stdDate = Integer.valueOf( constStd.PASSWD_CHANGE_STADNDATE_DATE.getString());
		//인증메일을 보낸지 24시간이 아직 경과 하지 않았는가?
		//경과하지않음.
		if(stdDate > count)
			return false;
		
		
		
		return true;
					
	}
	
	/**
	 * 	비밀번호 분실 경우 메일을 보낼것인가 인증번호 입력페이지로 갈 것인가? 	  
	 * 
	 * @param mdb
	 * @return	true if redirect to inputmailPage, false if to inputAuthnumPage
	 */
	public static boolean isSendmail(MemberDataBean mdb, int userState){
		
		//이미 전송하였는가?
		if( (boolean)MemberProcessBean.getSomethingJustOne("losingPassword", "u_num", mdb.getId(), "isSendedMail")){
			Timestamp time = (Timestamp)MemberProcessBean.getSomethingJustOne("losingPasswd", "u_num", mdb.getId(), "sendedMailDate");
			//int dateGap = Calendar.getInstance().get
																	 
			Date today = new Date ( );
			Calendar cal = Calendar.getInstance ( );
			cal.setTime ( today );// 오늘로 설정. 
			 
			 
			Calendar cal2 = Calendar.getInstance ( );
			cal2.setTime(time);
		 
			 
			int count = 0;
			while ( !cal2.after ( cal ) )
			{
				count++;
				cal2.add ( Calendar.HOUR, 1 ); // 다음날로 바뀜					
			}
			
			int stdDate = Integer.valueOf( constStd.RESEND_STANDRATE_DATE.getString());
			//인증메일을 보낸지 24시간이 아직 경과 하지 않았는가?
			//경과하지않음.
			if(stdDate > count)
				return false;
		}					
		
		return true;

	}
	
	/** 원하는 테이블에서 조건에 맞는 값을 하나만 리턴해주는 함수.
	 *     "select 'needSelection' from 'tableName' where 'attr' = 'val' "
	   *     의 형태로 쿼리를 수행함
	 * @param tableName			찾고자 하는 테이블 
	 * @param attr					찾을 테이블에서의 속성 
	 * @param val					찾을 테이블에서 조건인 속성에 해당할 값  
	 * @param needSelection		찾을 테이블에서 찾고자하는 값
	 * @return						쿼리결과에 따라 하나의 날짜가 리턴됨 
	 */
	
	public static boolean isConcordTempValue(MemberDataBean mdb, String value ){
		
		mdb.setId( somethingToId(mdb.getEmail(), "email" ));
		String tempPw = (String)getSomethingJustOne("losingPasswd","u_num",mdb.getId(), "tempPassword");
		
		if(BCrypt.checkpw(value, tempPw)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static boolean changePassword(MemberDataBean mdb, String newPasswd){
		
		mdb.setId( somethingToId(mdb.getEmail(), "email") );
		
		String pw = BCrypt.hashpw( newPasswd, BCrypt.gensalt());
		
		setSomethingJustOne("user", "userNum", mdb.getId(),"password", pw);		
		return true;
		
	}
	
	public static Object getSomethingJustOne(String tableName, String where_attr, Object where_val, String needSelection){

		
		Object resultValue = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
	
		try {
			//테이블명 - > 조건 -> select 순서 
			switch(tableName){
				
				case "losingPasswd":
					
					switch(where_attr){
					
						case "u_num":
							
							switch(needSelection){
								
								case "tempRegistrationDate":
									pstmt = conn.prepareStatement("select tempRegistrationdate from losingPasswd where u_num = ? ");
									break;
							
								case "sendedMailDate":
									pstmt = conn.prepareStatement("select sendedMailDate from losingPasswd where u_num = ? ");
									break;
									
								case "isSendedMail":
									pstmt = conn.prepareStatement("select isSendedMail from losingPasswd where u_num = ? ");
									break;
								case "tempPassword":
									pstmt = conn.prepareStatement("select tempPassword from losingPasswd where u_num = ? ");
									break;
									
								default:
									throw new SQLException("해당하는 " +  needSelection +"이 존재하지 않습니다");
							}		
							pstmt.setInt(1, (int)where_val);
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + needSelection +"이 존재하지 않습니다");				
						
					}
					
					break;
				
				case "userState":
					
					switch(where_attr){
							
						case "u_num":
							
							switch(needSelection){
							
								case "lastLoginDate": 
									pstmt = conn.prepareStatement("select lastLoginDate from userState where u_num = ? ");
									break;
									
								case "lastModifiedDate":
									pstmt = conn.prepareStatement("select lastModifiedDate from userState where u_num = ? ");
									break;
									
								case "denyChangePwDate":
									pstmt = conn.prepareStatement("select denyChangePwDate from userState where u_num = ? ");
									break;
									
								case "isAbnormal":
									pstmt = conn.prepareStatement("select isAbnormal from userState where u_num = ? ");
									break;
								
								case "isDeveloper":
									pstmt = conn.prepareStatement("select isDeveloper from userState where u_num = ? ");
									break;
									
								case "failedLoginCount":
									pstmt = conn.prepareStatement("select failedLoginCount from userState where u_num = ? ");
									break;
			
								default:
									throw new SQLException("해당하는 " +  needSelection +"이 존재하지 않습니다");
							}
							pstmt.setInt(1, (int)where_val);
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + needSelection +"이 존재하지 않습니다");		
						
					}
					break;
					
				case "user":
					
					switch(where_attr){
					
						case "userNum":
							
							switch(needSelection){
							
								case "email":
									pstmt = conn.prepareStatement("select email from user where userNum = ? ");
									pstmt.setInt(1, (int)where_val);
									break;
						
							
								case "registrationDate":
									pstmt = conn.prepareStatement("select registrationDate from user where userNum = ? ");
									pstmt.setInt(1, (int)where_val);
									break;
								
								default:
									throw new SQLException("해당하는 " +  needSelection +"이 존재하지 않습니다");
							
							}
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + needSelection +"이 존재하지 않습니다");		
					}
					break;
					
	
			}
		
			rs = pstmt.executeQuery();
			if(rs.next())
				resultValue = rs.getObject(1);
			else
				throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + needSelection +"이 존재하지 않습니다");
			
			
			
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			resultValue = null;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			resultValue = null;
		}
		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}
		
		return resultValue;
	
		
		
		
	}

	/** 원하는 테이블에서 조건에 맞는 값을 하나만 update하는 함수.
	 *  
	 * @param tableName	찾고자 하는 테이블 
	 * @param where_attr 찾을 테이블에서의 속
	 * @param where_val  찾을 테이블에서 조건이 속성에 해당할 
	 * @param columnName 변경할 값의 속성   
	 * @param setValue   변경할 값
	 */
	public static void setSomethingJustOne(String tableName, String where_attr, Object where_val, 
			String columnName, Object setValue){

		ResultSet rs = null;
		PreparedStatement pstmt = null;
	
		try {
			//테이블명 - > 조건 -> select 순서 
			switch(tableName){
				
				case "losingPasswd":
					
					switch(where_attr){
					
						case "u_num":
							
							switch(columnName){
								
								case "tempRegistrationDate":
									pstmt = conn.prepareStatement("update losingPasswd set tempRegistrationdate = ? where u_num = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
							
								case "sendedMailDate":
									pstmt = conn.prepareStatement("update losingPasswd set sendedMailDate = ? where u_num = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
									
								case "isSendedMail":
									pstmt = conn.prepareStatement("update losingPasswd set isSendedMail = ? where u_num = ? ");
									pstmt.setBoolean(1, (boolean)setValue );
									
									break;
									
								case "tempPassword":
									pstmt = conn.prepareStatement("update losingPasswd set tempPassword = ? where u_num = ? ");
									pstmt.setString(1, (String)setValue );
									
									break;
									
								default:
									throw new SQLException("해당하는 " +  columnName +"이 존재하지 않습니다");
							}	
							pstmt.setInt(2, (int)where_val );
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + columnName +"이 존재하지 않습니다");				
						
					}
					
					break;
				
				case "userState":
					
					switch(where_attr){
							
						case "u_num":
							
							switch(columnName){
							
								case "lastLoginDate": 
									pstmt = conn.prepareStatement("update userState set lastLoginDate = ? where u_num = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
									
								case "lastModifiedDate":
									pstmt = conn.prepareStatement("update userState set lastModifiedDate = ? where u_num = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
								
									break;
									
								case "denyChangePwDate":
									pstmt = conn.prepareStatement("update userState set denyChangePwDate = ? where u_num = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
								
									break;
									
								case "isAbnormal":
									pstmt = conn.prepareStatement("update userState set isAbnormal = ? where u_num = ? ");
									pstmt.setBoolean(1, (boolean)setValue );
									
									break;
								
								case "isDeveloper":
									pstmt = conn.prepareStatement("update userState set isDeveloper = ? where u_num = ? ");
									pstmt.setBoolean(1, (boolean)setValue );
									
									break;
			
								case "failedLoginCount":
									pstmt = conn.prepareStatement("update userState set failedLoginCount = ? where u_num = ? ");
									pstmt.setInt(1, (int)setValue );
									
									break;
									
								default:
									throw new SQLException("해당하는 " +  columnName +"이 존재하지 않습니다");
							}
							pstmt.setInt(2, (int)where_val );
							break;
					
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + columnName +"이 존재하지 않습니다");		
						
					}
					break;
					
				case "user":
					
					switch(where_attr){
					
						case "userNum":
							
							switch(columnName){
							
								case "email":
									pstmt = conn.prepareStatement("update user set email = ? where userNum = ? ");
									pstmt.setString(1, (String)setValue );
								
									break;					
							
								case "registrationDate":
									pstmt = conn.prepareStatement("update user set registrationDate = ? where userNum = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
							
									break;
									
								case "password":
									pstmt = conn.prepareStatement("update user set password = ? where userNum = ? ");
									pstmt.setString(1, (String)setValue );
							
							default:
								throw new SQLException("해당하는 " +  columnName +"이 존재하지 않습니다");
							
							}
							pstmt.setInt(2, (int)where_val );
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + columnName +"이 존재하지 않습니다");		
					}
					break;
					
	
			}
		
			rs = pstmt.executeQuery();
					
			
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}
		
			
	}

}


