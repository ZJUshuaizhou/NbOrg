package com.nb.org.domain;

import java.util.List;

public class PersonDto {
	private Integer id;

    private String name;
    
    private String username;
   
	private String  gender;
    
    private String telephone;
    
    private String mobilephone;
    
    private String email;
    
	private String dep;
	
	private String pos;
	
	private int isEdit;
	
	private int samePersonId;
	
	private String depsn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}
	
	

	public int getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}

	
	

	public int getSamePersonId() {
		return samePersonId;
	}

	public void setSamePersonId(int samePersonId) {
		this.samePersonId = samePersonId;
	}
	
	
	

	public String getDepsn() {
		return depsn;
	}

	public void setDepsn(String depsn) {
		this.depsn = depsn;
	}

	@Override
    public String toString() {
        return "PersonDto [id=" + id + ", name=" + name + ", username=" + username + ", gender=" + gender
            + ", telephone=" + telephone + ", mobilephone=" + mobilephone + ", email=" + email + ", dep="
            + dep + ", pos=" + pos + ", isEdit=" + isEdit + ", samePersonId=" + samePersonId + "]";
    }
	
    public PersonDto(Integer id, String name, String username,String gender, String telephone, String mobilephone, String email,
			String dep, String pos, int isEdit) {
		super();
		this.id = id;
		this.name = name;
		this.username=username;
		this.gender = gender;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.dep = dep;
		this.pos = pos;
		this.isEdit = isEdit;
	}

	public PersonDto(Integer id, String name,String username, String gender, String telephone, String mobilephone, String email,
			String dep, String pos) {
		super();
		this.id = id;
		this.name = name;
		this.username=username;
		this.gender = gender;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.dep = dep;
		this.pos = pos;
	}

	

	public PersonDto(Integer id, String name, String username,String gender, String telephone, String mobilephone, String email,
			String dep, String pos, int isEdit, int samePersonId) {
		super();
		this.id = id;
		this.name = name;
		this.username=username;
		this.gender = gender;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.dep = dep;
		this.pos = pos;
		this.isEdit = isEdit;
		this.samePersonId = samePersonId;
	}

	public PersonDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonDto(Integer id, String name, String username, String gender, String telephone, String mobilephone,
			String email, String dep, String pos, int isEdit,  String depsn) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.gender = gender;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.dep = dep;
		this.pos = pos;
		this.isEdit = isEdit;
		this.depsn = depsn;
	}

	
	
    

}