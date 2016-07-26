package com.nb.org.domain;

import java.util.List;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppInfo {
	private Integer id;
	private String name;
	private String description;
	private Person creator;
	private Department manageDep;
	private AppOAuth oauth;
	private AppSAML saml;
	private AppSTS sts;
	private List<AppRole> appRoles;


	public AppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public AppInfo(Integer id, String name, String description, Person creator, Department manageDep, AppOAuth oauth,
			AppSAML saml, AppSTS sts) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.manageDep = manageDep;
		this.oauth = oauth;
		this.saml = saml;
		this.sts = sts;
	}



	public AppInfo(String name, String description, Person creator, Department manageDep, AppOAuth oauth, AppSAML saml,
			AppSTS sts) {
		super();
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.manageDep = manageDep;
		this.oauth = oauth;
		this.saml = saml;
		this.sts = sts;
	}



	public AppInfo(Integer id, String name, String description, Person creator, Department manageDep, AppOAuth oauth,
			AppSAML saml, AppSTS sts, List<AppRole> appRoles) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.manageDep = manageDep;
		this.oauth = oauth;
		this.saml = saml;
		this.sts = sts;
		this.appRoles = appRoles;
	}



	public AppSTS getSts() {
		return sts;
	}




	public void setSts(AppSTS sts) {
		this.sts = sts;
	}



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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Person getCreator() {
		return creator;
	}



	public void setCreator(Person creator) {
		this.creator = creator;
	}



	public Department getManageDep() {
		return manageDep;
	}



	public void setManageDep(Department  manageDepId) {
		this.manageDep = manageDepId;
	}



	public AppOAuth getOauth() {
		return oauth;
	}



	public void setOauth(AppOAuth oauth) {
		this.oauth = oauth;
	}



	public AppSAML getSaml() {
		return saml;
	}
	public void setSaml(AppSAML saml) {
		this.saml = saml;
	}


	public List<AppRole> getAppRoles() {
		return appRoles;
	}



	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}



	@Override
	public String toString() {
		return "AppInfo [id=" + id + ", name=" + name + ", description=" + description + ", creator=" + creator
				+ ", manageDep=" + manageDep + ", oauth=" + oauth + ", saml=" + saml + ", sts=" + sts + ", appRoles="
				+ appRoles + "]";
	}



	


}
