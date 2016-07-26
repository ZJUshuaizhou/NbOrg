package com.nb.org.vo;


/*
 * @author upshi
 * @date 20160223
 * @description AppInfo相关rest使用
 * 
 */

public class AppInfoVO {
	
	private Integer appId;
	private String name;
	private String description;
	private String oauthUrl;
	private String samlUrl;
	private String samlLogoutUrl;
	private String stsEndpoint;
	private String stsCertAlias;
	private String appDepartment;
	
	public AppInfoVO() {}
	
	public AppInfoVO(Integer appId,String name, String description, String oauthUrl, String samlUrl, String samlLogoutUrl, String stsEndpoint, String stsCertAlias, String appDepartment) {
		super();
		this.appId = appId;
		this.name = name;
		this.description = description;
		this.oauthUrl = oauthUrl;
		this.samlUrl = samlUrl;
		this.samlLogoutUrl = samlLogoutUrl;
		this.stsEndpoint = stsEndpoint;
		this.stsCertAlias = stsCertAlias;
		this.appDepartment = appDepartment;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
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

	public String getOauthUrl() {
		return oauthUrl;
	}

	public void setOauthUrl(String oauthUrl) {
		this.oauthUrl = oauthUrl;
	}

	public String getSamlUrl() {
		return samlUrl;
	}

	public void setSamlUrl(String samlUrl) {
		this.samlUrl = samlUrl;
	}

	public String getSamlLogoutUrl() {
		return samlLogoutUrl;
	}

	public void setSamlLogoutUrl(String samlLogoutUrl) {
		this.samlLogoutUrl = samlLogoutUrl;
	}

	public String getStsEndpoint() {
		return stsEndpoint;
	}

	public void setStsEndpoint(String stsEndpoint) {
		this.stsEndpoint = stsEndpoint;
	}

	public String getStsCertAlias() {
		return stsCertAlias;
	}

	public void setStsCertAlias(String stsCertAlias) {
		this.stsCertAlias = stsCertAlias;
	}

	public String getAppDepartment() {
		return appDepartment;
	}

	public void setAppDepartment(String appDepartment) {
		this.appDepartment = appDepartment;
	}

	@Override
	public String toString() {
		return "AppInfoVO [appId=" + appId + ", name=" + name + ", description=" + description + ", oauthUrl=" + oauthUrl + ", samlUrl=" + samlUrl + ", samlLogoutUrl=" + samlLogoutUrl + ", stsEndpoint=" + stsEndpoint + ", stsCertAlias=" + stsCertAlias + ", appDepartment=" + appDepartment + "]";
	}
	
}
