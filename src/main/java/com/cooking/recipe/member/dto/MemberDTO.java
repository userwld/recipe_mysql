package com.cooking.recipe.member.dto;

public class MemberDTO {
	private String id;
	private String pw;
	private String phone;
	private String addr1;
	private String addr2;
	private String social;
	private String name;
	
	public MemberDTO() {}
	
	// 주소와 이름은 주문시에 입력받아서 저장됨(가입시에는 4가지 항목만)
	public MemberDTO(String id, String pw, String phone, String social) {
		this.id = id; 
		this.pw = pw;
		this.phone = phone;
		this.social = social;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getSocial() {
		return social;
	}
	public void setSocial(String social) {
		this.social = social;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
