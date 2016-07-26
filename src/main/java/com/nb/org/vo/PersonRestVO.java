package com.nb.org.vo;

public class PersonRestVO {

	private Integer id;

	private String sn;

	private String name;

	private String username;

	private Integer gender;

	private String telephone;

	private String mobilephone;

	private String email;
	
	private String position;
	
	private Integer adminFlag;
	
	private String password;

	public PersonRestVO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "PersonRestVO [id=" + id + ", sn=" + sn + ", name=" + name + ", username=" + username + ", gender=" + gender + ", telephone=" + telephone + ", mobilephone=" + mobilephone + ", email=" + email + ", position=" + position + ", adminFlag=" + adminFlag + ", password=" + password + "]";
	}

}
