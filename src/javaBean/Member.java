package javaBean;

import java.awt.Point;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * member에 대한 객체 정보는 static 변수를 통해 참조하며 유저와 관련된 위젯들은 widget관련 변수를 통해 관리한다.
 * 	멤버 상태나 정보는 manageMember패캐지의 클래스들이나 MemberProess에서만 유일하게 조작되며 
 *     개개의 유저가 가진 위젯들의 상태는 WidgetProcess를 통해서만 조작된다.
 * @author cmk
 *
 */
public class Member {

	
	public static class MyAddedWidgets{
		
		private final int wId;
		private final String wName;
		private final String originPath;
		private final Point point;
				
		public MyAddedWidgets(int id, String name, String originPath, Timestamp updatedDate, Point point){
			
			wId = id;
			wName = name;
			this.originPath = originPath;
			this.point = point;
		}
		
	}
	
	private static Map<String, Member> MemberMap = new HashMap<String, Member>();
	private DevelopedWidget developedWidget;
	private DownloadedWidget downloadedWidget;
	
	public static final int DEFAULT_VALUE = -1;
	private int id;
	private String nickname;
	private String password;
	private String idType;
	private String email;
	private Timestamp regDate;
	private String sessionId;
	private boolean isLogin;
	private boolean isLogout;
	private boolean isJoin;
	private boolean isDeveloper;
	
	
	
	public Member(){
		id = DEFAULT_VALUE;
		nickname="";
		password="";
		idType="";
		email="";
		regDate =  new Timestamp(System.currentTimeMillis());
		sessionId= "";
		downloadedWidget = null;
		developedWidget = null;
	}
	
	public Member(Member mdb){
		id = mdb.id;
		nickname = new String(mdb.nickname);
		password = new String(mdb.password.toString());
		idType = new String(mdb.idType);
		regDate = (Timestamp)mdb.regDate.clone();
		developedWidget = mdb.developedWidget;
		downloadedWidget = mdb.downloadedWidget;
		developedWidget = mdb.developedWidget;
	}
	
	public Member(int id, String email, String name, String pw, String idType, Timestamp reg_date){
		this.id = id;
		this.email = email;
		this.nickname = name;
		this.password = pw;
		this.idType = idType;
		this.regDate = reg_date;
		this.developedWidget = null;
		this.downloadedWidget = null;
	}
	
	////getter setter////
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String name) {
		this.nickname = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionId(){
		return sessionId;
	}
	public void setSessionId(String arg){
		sessionId = arg;
	}
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean isLogout() {
		return isLogout;
	}

	public boolean isDeveloper() {
		return isDeveloper;
	}

	public void setDeveloper(boolean isDeveloper) {
		this.isDeveloper = isDeveloper;
	}

	public void setLogout(boolean isLogout) {
		this.isLogout = isLogout;
	}

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}
		

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	public static Map<String,Member> getMemberMap(){
		return MemberMap;
	}

	public DevelopedWidget getDevelopedWidget() {
		return developedWidget;
	}

	public void setDevelopedWidget(DevelopedWidget developedWidget) {
		this.developedWidget = developedWidget;
	}

	public DownloadedWidget getDownloadedWidget() {
		return downloadedWidget;
	}

	public void setDownloadedWidget(DownloadedWidget downloadedWidget) {
		this.downloadedWidget = downloadedWidget;
	}

	/////method/////
	
	
	public static boolean isContainsMember(String sId){
		
		return MemberMap.containsKey(sId);
						
	}
	

	
	/**
	 * 	로그인한 유저를 대상으로 HashMap으로 객체를 보유하고 없으면 새로 생성한다. 
	 * @param sId	브라우져 sessionId를 통해 유저의 객체를 찾는다. 
	 * @return	sId key에 맞는 객체 value를 반환. 
	 * @throws Throwable	sId가 null이거나 sId를 통해 객체를 찾지 못핳는 경우 예외 발생. 
	 */
	public static Member getMember(String sId) throws Throwable{
		
		if(sId==null)
			throw new NullPointerException();
		
		Member member = null;
		
		
		if(Member.MemberMap.containsKey(sId)){
			return Member.MemberMap.get(sId);
		}
		else{
			
			member = new Member();
			member.setSessionId(sId);
			MemberMap.put(sId, member);
		}
		
		return member;
	}
	
	public static void addMember(Member m){
		Member.getMemberMap().put(m.getSessionId(), m);
	}
		
	@Override
	public int hashCode(){
		
		return 17;
	}
	
	@Override 
	public String toString(){
		
		return new String("해당 객체는 사이트에서... ");
		
	}
	
	@Override
	public boolean equals(Object mdb){
		
		if( mdb == null)
			throw new NullPointerException();
		else if( !(mdb instanceof Member))
			throw new IllegalArgumentException("비교대상으로 적합하지 않은 객체가 비교시도 되었습니다.");
		
		Member m = (Member )mdb;
		
		return ( id==m.getId() && password.equals(m.getPassword()) ) ? true : false;
	}
	
	public static Member makePerfectMember(String id){
		Member m = new Member();
		m.setEmail("gooddaumi@naver.com");
		
		m.setId(32);
		m.setSessionId(id);
		m.setJoin(true);
		m.setLogin(true);
		m.setLogout(false);
		m.setNickname("cks1023");
		m.setPassword("1234");
		
		return m;
	}



	
}
