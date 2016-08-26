package javaBean;

import java.sql.Timestamp;

//User Table 참조
public class MemberDataBean {

	private int id;
	private String nickname;
	private String password;
	private String idType;
	private String email;
	private Timestamp reg_date;
	
	public MemberDataBean(){
	}
	
	public MemberDataBean(MemberDataBean mdb){
		id = mdb.id;
		nickname = mdb.nickname;
		password = mdb.password;
		idType = mdb.idType;
		reg_date = mdb.reg_date;
	}
	
	public MemberDataBean(int id, String email, String name, String pw, String idType, Timestamp reg_date){
		this.id = id;
		this.email = email;
		this.nickname = name;
		this.password = pw;
		this.idType = idType;
		this.reg_date = reg_date;
	}
	
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

	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override 
	public String toString(){
		
		return new String("해당 객체는 사이트에서... ");
		
	}
	
	@Override
	public boolean equals(Object mdb){
		
		if( mdb == null)
			throw new NullPointerException();
		else if( !(mdb instanceof MemberDataBean))
			throw new IllegalArgumentException("비교대상으로 적합하지 않은 객체가 비교시도 되었습니다.");
		
		MemberDataBean m = (MemberDataBean )mdb;
		
		return ( id==m.getId() && password.equals(m.getPassword()) ) ? true : false;
	}
	
}
