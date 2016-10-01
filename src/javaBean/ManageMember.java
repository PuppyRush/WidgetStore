
package javaBean;


import java.sql.*;
import java.util.*;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

import mail.PostMan;
import property.enums.member.enumMemberState;
import property.enums.enumPage;
import property.enums.enumSystem;
import property.enums.member.enumMemberAbnormalState;
import property.enums.member.enumMemberStandard;
import property.enums.member.enumMemberType;


public class ManageMember {
	
	private static Connection conn = ConnectMysql.getConnector();

	private ManageMember() {
	}

	public static EnumMap<enumMemberAbnormalState, Boolean> getMemberStates(Member member) throws SQLException, MemberException{

		Statement _st = null;
		PreparedStatement _ps = null;
		ResultSet _rs = null;
		EnumMap<enumMemberAbnormalState, Boolean> _stateMap = new EnumMap<>(enumMemberAbnormalState.class);
		try{
			
			int _id = sthToId(member);
			
			
			_ps = conn.prepareStatement("select * from userState where u_id = ?");
			_ps.setInt(1, _id);			
			_rs = _ps.executeQuery();
			
			if(_rs.next()){
				
				if(_rs.getInt("isAbnormal")==1){
				
					for(enumMemberAbnormalState e : enumMemberAbnormalState.values()){												
						String _attributeName = e.getString();
						if(_rs.getInt(_attributeName)==1)
							_stateMap.put(e, true);
						else
							_stateMap.put(e, false);
					}
				}
				
			}
			else
				throw new SQLException();
		}
		 finally {
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
			if (_st != null)
				try {
					_st.close();
				} catch (SQLException ex) {
				}
		}
		
		return _stateMap;
		
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
		finally {
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
	

	
	public static int sthToId(Member member) throws SQLException, MemberException {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		int id=-1;
		
		try{
			
			if(member.getEmail() != null && member.getEmail().equals("") == false ){
				pstmt = conn.prepareStatement("select u_id from user where email = ? ");
				pstmt.setString(1, member.getEmail());
			}
			else if(member.getNickname() != null && member.getNickname().equals("") == false ){
				pstmt = conn.prepareStatement("select u_id from user where nickname = ? ");
				pstmt.setString(1, member.getNickname());
			}
			else if( member.getSessionId() != null && member.getNickname().equals("") == false ){
				if(Member.isContainsMember(member.getSessionId()))
					return Member.getMember(member.getSessionId()).getId();
				else
					throw new MemberException(enumMemberState.NOT_EXIST_MEMBER_FROM_MAP, enumPage.ERROR404);
			}
			else{
				throw new SQLException("닉네임과 메일이 입력되지 않았습니다.");
			}
			
		

			rs = pstmt.executeQuery();
			if(rs.next())
				id = rs.getInt(1);
			else
				throw new SQLException("해당하는 아이디는 가입되지 않은 아이디 입니다.");
			
			
			
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

	public static int sthToId(String email) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		

		pstmt = conn.prepareStatement("select u_id from user where email = ? ");
		pstmt.setString(1, email);
		
		rs = pstmt.executeQuery();
		rs.next();
		
		return rs.getInt("u_id");		
		
	}
	
	/**
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 가입이 무사히 성사됐는지 여부를 반환
	 * @throws Throwable 
	 */
	@SuppressWarnings("resource")
	public static boolean joinMember(Member member) throws Throwable {
		
		conn.setAutoCommit(false);
		ResultSet _rs = null;
		PreparedStatement _ps = null;
		int _key=-1;
		
		try {
			
			
			
			boolean isDuplicated_email = 
					isExist("user", "email" ,member.getEmail() );
			
			boolean isDuplicated_nickname = 
					isExist("user", "nickname" ,member.getNickname() );
			
			if(isDuplicated_email || isDuplicated_nickname){
				
				_ps = conn.prepareStatement("select joinCertification from userState where u_id = ?");
				
				int id = sthToId(member);
				
				_ps.setInt(1, id);
				_rs = _ps.executeQuery();
						
				if(_rs.next()){
					if(_rs.getInt("joinCertification")==1){
						throw new MemberException( "가입후  인증하지 않은 상태입니다.메일 인증 후 로그인 하세요" ,enumMemberState.NOT_JOIN_CERTIFICATION, enumPage.MAIN);
					}
				}else
					throw new SQLException("id"+id+"가 userState에 존재하지 않습니다.");
					
				throw new SQLException(member.getEmail() +"이나" + member.getNickname()+"이 중복됩니다." );
			}
		
			////////user table
			
			_ps = conn.prepareStatement(
					"insert into user ( email, nickname, password, registrationDate, registrationKind) values (?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			_ps.setString(1, member.getEmail());
			_ps.setString(2, member.getNickname());
			_ps.setTimestamp(4, member.getRegDate());
			_ps.setString(5, member.getIdType().getString());
			if(member.getIdType().getString().equals( enumMemberType.NOTHING.getString() )){

				String pw = BCrypt.hashpw( member.getPlanePassword(), BCrypt.gensalt(12));
				_ps.setString(3, pw);
			}
			else
				_ps.setString(3, "");
			
			_ps.executeUpdate();

			_rs = _ps.getGeneratedKeys();

			if (_rs.next()) {
			    _key = _rs.getInt(1);
			}	
			else{
				member.setJoin(false);
				throw new SQLException("-");
			}
			

			member.setJoin(true);
			
			//////////userState table
			
		/*	_ps = conn.prepareStatement("insert into userState (u_id ) values(?)" );
			_ps.setInt(1,_key);	
			_ps.executeUpdate();*/
					
			//insert certification table and send mail 
				
			String _uuid =  UUID.randomUUID().toString();
			String hashedUUID =  BCrypt.hashpw(_uuid, BCrypt.gensalt(12));
			_ps = conn.prepareStatement("insert into joinCertification (u_id, certificationNumber ) values(?,?)" );
			_ps.setInt(1,_key);
			_ps.setString(2, _uuid);	
			_ps.executeUpdate();
			
			String _fullUrl = new StringBuilder(enumPage.ROOT.toString()).append(enumPage.CERTIFICATE.toString()).append("?email=").append(member.getEmail()).
					append("&number=").append(hashedUUID).toString();
			if(PostMan.sendCeriticationJoin(member.getEmail(), _fullUrl ))
				conn.commit();
			else{
				conn.rollback();
			}
		}
		finally {
			if(conn!=null) 
				try{conn.rollback();}// Exception 발생시 rollback 한다.
					catch(SQLException ex1){
						System.out.println(ex1.getMessage());
						ex1.printStackTrace();
					}
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}
		
			
	
		return true;
	}

	/**
	 * @throws Exception 
	 * @param member  가입할 유저의 정보의 객체 
	 * @param request jsp페이지로부터  넘어온 attribute값을 이용하기 위함 
	 * @return 로그인 무사히 성사됐는지 여부를 반환
	 * @throws  
	 */
	public static boolean loginMember(Member member) throws Exception {
		
		PreparedStatement __ps =null;
		ResultSet __rs = null;
		Statement _st = conn.createStatement();
		PreparedStatement _ps = null;
		ResultSet _rs=null;
		boolean _res=false;
		int _id = member.getId();
		try {				
			
			_ps = conn.prepareStatement("select * from user where u_id = ? ");
			_ps.setInt(1, member.getId());
			_rs = _ps.executeQuery();	
			_rs.next();
			String hashedpw =  _rs.getString("password");

			//String hashed = BCrypt.hashpw(member.getPassword(), BCrypt.gensalt(12));	
			//비밀번호 일치.
			if( BCrypt.checkpw( member.getPlanePassword(), hashedpw ) ){
				
				_res =  true;
				

				////// 마지막 로그인 날짜 갱신 
				Timestamp t = new Timestamp(System.currentTimeMillis());
				setSthJustOne("userDetail", "u_id", _id, "lastLoginDate" , t);
		
				//실패횟수 초기화			
				setSthJustOne("userDetail", "u_id", _id, "failedLoginCount", 0);
				
				//잠금상태 해제 
				//sleep인경우?
				
		
				member.setLogin(true);
				
				//////user

			
				member.setEmail( _rs.getString("email"));
				member.setNickname(_rs.getString("nickname"));
				member.setRegDate(_rs.getTimestamp("registrationDate"));
				
				
//				
				//developer
				
				__ps = conn.prepareStatement("select * from developer where u_id = ?");
				__ps.setInt(1, member.getId());
				__rs = __ps.executeQuery();
				if(__rs.next()){
					member.setDeveloper(true);
					member.setDeveloperId(__rs.getInt("d_id"));					
				}else{
					member.setDeveloper(false);					
				}
				__ps.close();
				__rs.close();
				
		
				
				//downloadedWidget
				__ps = conn.prepareStatement("select * from widgetInfo join widget using(widget_id) where u_id = ? ");
		  		__ps.setInt(1,member.getId());
				__rs = __ps.executeQuery();
				while(__rs.next()){
					
					DownloadedWidget _widget = 
							new DownloadedWidget.Builder(__rs.getInt("widget_id"), __rs.getString("title"),__rs.getString("kind")).developerId(__rs.getInt("d_id"))
							.setSize(__rs.getInt("x"), __rs.getInt("y"), __rs.getInt("width"), __rs.getInt("height"))
							.sourceRoot(__rs.getString("HTML")).build();
					
					member.addDownloadedWidget(_widget);							
				
				}
				
			}
			//불일치
			else{
				_res = false;
				
				int failedLoginCount = (int)getSthJustOne("userDetail", "u_id", _id, "failedLoginCount");				
				
				
				failedLoginCount +=1;	
				setSthJustOne("userDetail","u_id", _id, "failedLoginCount", failedLoginCount);
				//st.executeUpdate("update userDetail set failedCountLogin = " + failedCountLogin + " where id = " + id);
				
				if(failedLoginCount >= Integer.valueOf(enumMemberStandard.POSSIBILLTY_FAILD_LOGIN_NUM.getString()))				
					setSthJustOne("userDetail", "u_id", _id, "isAbnormal", 1);

				member.setLogin(false);
				
			}
		
		}
		finally {
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (_st != null)
				try {
					_st.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
		}

		return _res;
	}

	public static boolean loginManager(Member member) throws Throwable{
		
		
		PreparedStatement _ps = null;
		ResultSet _rs=null;
		
		boolean _res=false;
		try {	
			int id = sthToId(member);
			member.setId(id);
						
			
			if(!member.getEmail().equals(enumSystem.ADMIN.toString()))
				throw new MemberException(enumMemberState.NOT_ADMIN, enumPage.LOGIN_MANAGER);
			
			_ps = conn.prepareStatement("select password from user where u_id=? ");
			_ps.setInt(1, member.getId());
			_rs = _ps.executeQuery();	
			
			_rs = _ps.executeQuery();
			_rs.next();
			String hashedpw =  _rs.getString("password");

			//String hashed = BCrypt.hashpw(member.getPassword(), BCrypt.gensalt(12));	
			//비밀번호 일치.
			if( BCrypt.checkpw( member.getPlanePassword(), hashedpw ) ){
				
				_res =  true;
				

				////// 마지막 로그인 날짜 갱신 
				Timestamp t = new Timestamp(System.currentTimeMillis());
				setSthJustOne("userDetail", "u_id", id, "lastLoginDate" , t);
		
				//실패횟수 초기화			
				setSthJustOne("userDetail", "u_id", id, "failedLoginCount", 0);
				
				//잠금상태 해제 
				//sleep인경우?
		
				member.setLogin(true);
			}
			//불일치
			else{
				_res = false;
				
				int failedLoginCount = (int)getSthJustOne("userDetail", "u_id", id, "failedLoginCount");				
				
				
				failedLoginCount +=1;	
				setSthJustOne("userDetail","u_id", id, "failedLoginCount", failedLoginCount);
				//st.executeUpdate("update userDetail set failedCountLogin = " + failedCountLogin + " where id = " + id);
				
				if(failedLoginCount >= Integer.valueOf(enumMemberStandard.POSSIBILLTY_FAILD_LOGIN_NUM.getString()))				
					setSthJustOne("userDetail", "u_id", id, "isAbnormal", 1);

				member.setLogin(false);
				
			}
		
		}
		finally {
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}

		}
		
		
		return true;
	}
	
	/**
	 * 	로그아웃을 위한 처리를 합니다.
	 * @param member
	 * @throws Throwable 
	 */
	public static void logoutMember(Member member) throws Throwable {
		
		member.setId(sthToId(member));
		setSthJustOne("userDetail","u_id",member.getId(),"lastLogoutDate", new Timestamp(System.currentTimeMillis()) );
		Member.getMemberMap().remove(member);
		member = null;
		
	}
	
	public static boolean isMember(Member member) throws SQLException{
	
		ResultSet _rs = null;
		PreparedStatement _ps = null;

		boolean _result = false;
	
		if(member.getNickname().equals("")==false){
			
			_ps = conn.prepareStatement("select nickname from user where nickname = ?");
			_ps.setString(1, member.getNickname());
			_rs = _ps.executeQuery();
			
			if(_rs.next())
				_result = true;
			else
				_result = false;
							
		}
		else if(member.getEmail().equals("")==false){
			_ps = conn.prepareStatement("select u_id from user where email = ?");
			_ps.setString(1, member.getEmail());
			_rs = _ps.executeQuery();
			
			if(_rs.next())			
				_result = true;
				
			else
				_result = false;
		}
		else if(member.getId()>Member.DEFAULT_VALUE){
			_ps = conn.prepareStatement("select u_id from user where u_id = ?");
			_ps.setString(1, member.getNickname());
			_rs = _ps.executeQuery();
			
			if(_rs.next())
				_result = true;
			else
				_result = false;
		}
			

		
		
		return _result;
	}
	
	/**
	 * 
	 * 
	 * @param member
	 * @return	3개월 이상 지났다면 true 아니면 false
	 * @throws SQLException 
	 */
	public static boolean isPassingDate(Member member) throws SQLException{
		
		Timestamp time = (Timestamp)ManageMember.getSthJustOne("losingPasswd", "u_id", member.getId(), "sendedMailDate");
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
		
		int stdDate = Integer.valueOf( enumMemberStandard.PASSWD_CHANGE_STADNDATE_DATE.getString());
		//인증메일을 보낸지 24시간이 아직 경과 하지 않았는가?
		//경과하지않음.
		if(stdDate > count)
			return false;
		
		
		
		return true;
					
	}
	
	/**
	 * 	비밀번호 분실 경우 메일을 보낼것인가 인증번호 입력페이지로 갈 것인가를 결정하는 함수.	  
	 * 
	 * @param member
	 * @return	true if redirect to inputmailPage, false if to inputAuthnumPage
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public static boolean isSendmail(Member member) throws NumberFormatException, SQLException{
		
		//이미 전송하였는가?
		if( (boolean)ManageMember.getSthJustOne("losingPassword", "u_id", member.getId(), "isSendedMail")){
			Timestamp time = (Timestamp)ManageMember.getSthJustOne("losingPasswd", "u_id", member.getId(), "sendedMailDate");
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
			
			int stdDate = Integer.valueOf( enumMemberStandard.RESEND_STANDRATE_DATE.getString());
			//인증메일을 보낸지 24시간이 아직 경과 하지 않았는가?
			//경과하지않음.
			if(stdDate > count)
				return false;
		}					
		
		return true;

	}
	
	/**
	 * 	가입 후 인증메일로 받은 email과 인증번호를 비교하여 결과를 반환한다.
	 * @param email 가입당시 사용한 메
	 * @param hashedUUID  가입인증을 위해 발급된 인증번호(해시된 비번)
	 * @return
	 * @throws Exception 
	 * @throws Throwable 
	 */
	public static boolean certificateJoin(String sId, String email, String hashedUUID) throws Exception {
		
		PreparedStatement _ps = null;
		ResultSet _rs = null;
		
		try{
			
			conn.setAutoCommit(false);
			
			 
			int _id = sthToId(email);
			Member member = Member.getMember(sId);
			if(_id<=0)
				throw new Exception("아이디의 값이 불일치 합니다.");
			
			 _ps= conn.prepareStatement("select certificationNumber from joinCertification where u_id = ? ");
			_ps.setInt(1, _id);
			_rs = _ps.executeQuery();
			
			if(!_rs.next())
				throw new SQLException(email+","+_id+"joinCertification테이블에 id가 둘 이상 존재하거나 한개도 존재하지 않습니다.");
			 			
			String planeUUID = _rs.getString(1);
			
			if(BCrypt.checkpw( planeUUID, hashedUUID )){
				
				_ps = conn.prepareStatement("delete from joinCertification where u_id = ?");
				_ps.setInt(1, _id);
				_ps.executeUpdate();
				
				_ps = conn.prepareStatement("update userState set joinCertification = 0 where u_id = ?");
				_ps.setInt(1, _id);
				_ps.executeUpdate();
				/*
				_ps = conn.prepareStatement("update userState set isAbnormal = 0 where u_id = ?");
				_ps.setInt(1, _id);
				_ps.executeUpdate();*/
				
				////////userDetail table
				
				Timestamp date = new Timestamp(System.currentTimeMillis());
				_ps = conn.prepareStatement("update userDetail set lastModifiedPasswordDate =? , lastLogoutDate = ? where u_id = ?");				
				_ps.setInt(3,_id);
				_ps.setTimestamp(1, date);
				_ps.setTimestamp(2, date);
				
				_ps.executeUpdate();
					
				PostMan.sendWelcomeMail(member.getNickname(), member.getEmail());
				
				conn.commit();
			}else
				throw new SQLException();
		
			
		}finally{
			
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
	 * @throws Throwable 
	 */
	
	public static boolean isConcordTempValue(Member member, String value ) throws Throwable{
		
		member.setId( sthToId(member));
		String tempPw = (String)getSthJustOne("losingPasswd","u_id",member.getId(), "tempPassword");
		
		if(BCrypt.checkpw(value, tempPw)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static boolean changePassword(Member member, String newPasswd) throws Throwable{
		
		member.setId( sthToId(member) );
		
		String pw = BCrypt.hashpw( newPasswd, BCrypt.gensalt());
		
		setSthJustOne("user", "u_id", member.getId(),"password", pw);		
		return true;
		
	}
	
	public static Object getSthJustOne(String tableName, String where_attr, Object where_val, String _needSelection) throws SQLException{

		
		Object resultValue = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
	
		try {
			//테이블명 - > 조건 -> select 순서 
			switch(tableName){
				
				case "losingPasswd":
					
					switch(where_attr){
					
						case "u_id":
							
							switch(_needSelection){
								
								case "tempRegistrationDate":
									pstmt = conn.prepareStatement("select tempRegistrationdate from losingPasswd where u_id = ? ");
									break;
							
								case "sendedMailDate":
									pstmt = conn.prepareStatement("select sendedMailDate from losingPasswd where u_id = ? ");
									break;
									
								case "isSendedMail":
									pstmt = conn.prepareStatement("select isSendedMail from losingPasswd where u_id = ? ");
									break;
								case "tempPassword":
									pstmt = conn.prepareStatement("select tempPassword from losingPasswd where u_id = ? ");
									break;
									
								default:
									throw new SQLException("해당하는 " +  _needSelection +"이 존재하지 않습니다");
							}		
							pstmt.setInt(1, (int)where_val);
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + _needSelection +"이 존재하지 않습니다");				
						
					}
					
					break;
				
				case "userDetail":
					
					switch(where_attr){
							
						case "u_id":
							
							switch(_needSelection){
							
								case "loginCount": 
									pstmt = conn.prepareStatement("select loginCount from userDetail where u_id = ? ");
									break;
							
								case "lastLoginDate": 
									pstmt = conn.prepareStatement("select lastLoginDate from userDetail where u_id = ? ");
									break;
									
								case "lastModifiedDate":
									pstmt = conn.prepareStatement("select lastModifiedDate from userDetail where u_id = ? ");
									break;
									
								case "denyChangePwDate":
									pstmt = conn.prepareStatement("select denyChangePwDate from userDetail where u_id = ? ");
									break;
									
								case "isAbnormal":
									pstmt = conn.prepareStatement("select isAbnormal from userDetail where u_id = ? ");
									break;
								
								case "isDeveloper":
									pstmt = conn.prepareStatement("select isDeveloper from userDetail where u_id = ? ");
									break;
									
								
								case "failedLoginCount":
									pstmt = conn.prepareStatement("select failedLoginCount from userDetail where u_id = ? ");
									break;
			
								default:
									throw new SQLException("해당하는 " +  _needSelection +"이 존재하지 않습니다");
							}
							pstmt.setInt(1, (int)where_val);
							break;
							
						default:
							throw new SQLException(where_attr +"해당하는  " + where_val +"나 " + _needSelection +"이 존재하지 않습니다");		
						
					}
					break;
					
				case "userState":
					
					switch(where_attr){
						
						case "u_id":
							
							switch(_needSelection){
								
								case "lostPassword":
									pstmt = conn.prepareStatement("select lostPassword from userState where u_id = ? ");
									break;
								
								case "failedLogin":
									pstmt = conn.prepareStatement("select failedLogin from userState where u_id = ? ");
									break;
									
								case "sleep":
									pstmt = conn.prepareStatement("select sleep from userState where u_id = ? ");
									break;
									
								case "oldPassword":
									pstmt = conn.prepareStatement("select oldPassword from userState where u_id = ? ");
									break;
									
								case "joinCertification":
									pstmt = conn.prepareStatement("select joinCertification from userState where u_id = ? ");
									break;
									
								case "isAbnormal":
									pstmt = conn.prepareStatement("select isAbnormal from userState where u_id = ? ");
									break;
								
							}
							break;
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + _needSelection +"이 존재하지 않습니다");		
						
					}
					
				case "user":
					
					switch(where_attr){
					
						case "u_id":
							
							switch(_needSelection){
							
								case "email":
									pstmt = conn.prepareStatement("select email from user where u_id = ? ");
									break;
						
							
								case "registrationDate":
									pstmt = conn.prepareStatement("select registrationDate from user where u_id = ? ");
									pstmt.setInt(1, (int)where_val);
									break;
									
								case "registrationKind":
									pstmt = conn.prepareStatement("select registrationKind from user where u_id = ? ");
									pstmt.setInt(1, (int)where_val);
									break;
									
								case "nickname":
									pstmt = conn.prepareStatement("select nickname from user where u_id = ? ");
									pstmt.setInt(1, (int)where_val);
									break;
								
								default:
									throw new SQLException("해당하는 " +  _needSelection +"이 존재하지 않습니다");
							
							}
							break;
					
					
							
						default:
							throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + _needSelection +"이 존재하지 않습니다");		
					}
					break;
					
	
			}
		
			rs = pstmt.executeQuery();
			if(rs.next())
				resultValue = rs.getObject(1);
			else
				throw new SQLException("해당하는 " +  where_attr +"나 " + where_val +"," + _needSelection +"이 존재하지 않습니다");
			
			
			
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
	 * @param where_attr 찾을 테이블에서의 조건
	 * @param where_val  찾을 테이블에서 조건에 해당할 값
	 * @param columnName 변경할 값의 속성   
	 * @param setValue   변경할 값
	 */
	public static void setSthJustOne(String tableName, String where_attr, Object where_val, 
			String columnName, Object setValue){

		ResultSet rs = null;
		PreparedStatement pstmt = null;
	
		try {
			//테이블명 - > 조건 -> select 순서 
			switch(tableName){
				
				case "losingPasswd":
					
					switch(where_attr){
					
						case "u_id":
							
							switch(columnName){
								
								case "tempRegistrationDate":
									pstmt = conn.prepareStatement("update losingPasswd set tempRegistrationdate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
							
								case "sendedMailDate":
									pstmt = conn.prepareStatement("update losingPasswd set sendedMailDate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
									
								case "isSendedMail":
									pstmt = conn.prepareStatement("update losingPasswd set isSendedMail = ? where u_id = ? ");
									pstmt.setBoolean(1, (boolean)setValue );
									
									break;
									
								case "tempPassword":
									pstmt = conn.prepareStatement("update losingPasswd set tempPassword = ? where u_id = ? ");
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
						
						case "u_id":
							
							switch(columnName){
								
								case "lostPassword":
									pstmt = conn.prepareStatement("update userDetail set lostPassword = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
									break;
								
								case "failedLogin":
									pstmt = conn.prepareStatement("update userDetail set failedLogin = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
									break;
									
								case "sleep":
									pstmt = conn.prepareStatement("update userDetail set sleep = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
									break;
									
								case "oldPassword":
									pstmt = conn.prepareStatement("update userDetail set oldPassword = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
									break;
									
								case "joinCertification":
									pstmt = conn.prepareStatement("update userDetail set joinCertification = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
									break;
									
								case "isAbnormal":
									pstmt = conn.prepareStatement("update userDetail set isAbnormal = ? where u_id = ? ");
									pstmt.setInt(1, (int)setValue);
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
									
				case "userDetail":
					
					switch(where_attr){
							
						case "u_id":
							
							switch(columnName){
							
								case "lastLoginDate": 
									pstmt = conn.prepareStatement("update userDetail set lastLoginDate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
									
									break;
									
								case "lastModifiedDate":
									pstmt = conn.prepareStatement("update userDetail set lastModifiedDate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
								
									break;
									
								case "lastLogoutDate":
									pstmt = conn.prepareStatement("update userDetail set lastLogoutDate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
								
									break;
									
			
								case "failedLoginCount":
									pstmt = conn.prepareStatement("update userDetail set failedLoginCount = ? where 10 = ? ");
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
					
						case "u_id":
							
							switch(columnName){
							
								case "email":
									pstmt = conn.prepareStatement("update user set email = ? where u_id = ? ");
									pstmt.setString(1, (String)setValue );
								
									break;					
							
								case "registrationDate":
									pstmt = conn.prepareStatement("update user set registrationDate = ? where u_id = ? ");
									pstmt.setTimestamp(1, (Timestamp)setValue );
							
									break;
									
								case "password":
									pstmt = conn.prepareStatement("update user set password = ? where u_id = ? ");
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
		
			pstmt.executeUpdate();				
			
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

	private static void setAbnormalState(int uId) throws SQLException{
		
		
		PreparedStatement _ps = null;
		ResultSet _rs = null;
		
		try{
			_ps = conn.prepareStatement("select * from usetState where u_id = ?");
			_ps.setInt(1, uId);
			_rs = _ps.executeQuery();
			if(!_rs.next())
					throw new SQLException("userState 테이블에 "+uId+"가 없습니다.");
			
			boolean isAbnormal=false;
			for(enumMemberAbnormalState e: enumMemberAbnormalState.values()){
				if(e.getString().equals("isAbnormal") || e.getString().equals("u_id"))
					continue;
				
				if(_rs.getInt(e.getString())==1)
					isAbnormal=true;
			}
			_ps.close();
			
			_ps = conn.prepareStatement("update userState set isAbnormal = ? where u_id = ?");
			if(isAbnormal)
				_ps.setInt(1, 1);
			else
				_ps.setInt(1, 0);
			
			_ps.setInt(2, uId);
			_ps.executeQuery();		
			
			
		}finally{
			try {
				_ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				_rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void withdrawMember(int uId, String nickname, String email, String reason) throws SQLException{
		
		PreparedStatement _ps= null;
		try{
			
			conn.setAutoCommit(false);
		
		
			_ps= conn.prepareStatement("delete from user where u_id = ?");
			_ps.setInt(1, uId);
			_ps.executeQuery();			
			
			PostMan.sendWithdraw(nickname, email, reason);
			
			conn.commit();
		}finally{
			
			_ps.close();
		}
		
	}
	
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
				
				__ps.close();
				__rs.close();
				
				__ps = conn.prepareStatement("select * from userState where u_id = ?");
				__ps.setInt(1, m.getId());
				__rs = __ps.executeQuery();
				__rs.next();
				
				EnumMap<enumMemberAbnormalState, Boolean> state = new EnumMap<>(enumMemberAbnormalState.class);
				
				
				if(__rs.getInt("isAbnormal")==1){
		
					
					if(__rs.getInt(enumMemberAbnormalState.LOST_PASSWORD.getString())==1)
						state.put(enumMemberAbnormalState.LOST_PASSWORD, true);
					else
						state.put(enumMemberAbnormalState.LOST_PASSWORD, false);
					
					if(__rs.getInt(enumMemberAbnormalState.FAILD_LOGIN.getString())==1)
						state.put(enumMemberAbnormalState.FAILD_LOGIN, true);
					else
						state.put(enumMemberAbnormalState.FAILD_LOGIN, false);
					
					if(__rs.getInt(enumMemberAbnormalState.SLEEP.getString())==1)
						state.put(enumMemberAbnormalState.SLEEP, true);
					else
						state.put(enumMemberAbnormalState.SLEEP, false);
					
					if(__rs.getInt(enumMemberAbnormalState.OLD_PASSWORD.getString())==1)
						state.put(enumMemberAbnormalState.OLD_PASSWORD, true);
					else
						state.put(enumMemberAbnormalState.OLD_PASSWORD, false);
					
					if(__rs.getInt(enumMemberAbnormalState.JOIN_CERTIFICATION.getString())==1)
						state.put(enumMemberAbnormalState.JOIN_CERTIFICATION, true);
					else
						state.put(enumMemberAbnormalState.JOIN_CERTIFICATION, false);
				}
		
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


