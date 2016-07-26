package com.nb.org.domain;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppSTS {
	private Integer id;
	private String appName;
	private String endpoint;
	private String certAlias;
	
	
	
	
	public AppSTS() {
		super();
		// TODO Auto-generated constructor stub
	}




	public AppSTS(Integer id, String appName, String endpoint, String certAlias) {
		super();
		this.id = id;
		this.appName = appName;
		this.endpoint = endpoint;
		this.certAlias = certAlias;
	}




	public AppSTS(String appName, String endpoint, String certAlias) {
		super();
		this.appName = appName;
		this.endpoint = endpoint;
		this.certAlias = certAlias;
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getAppName() {
		return appName;
	}




	public void setAppName(String appName) {
		this.appName = appName;
	}




	public String getEndpoint() {
		return endpoint;
	}




	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}




	public String getCertAlias() {
		return certAlias;
	}




	public void setCertAlias(String certAlias) {
		this.certAlias = certAlias;
	}




	@Override
	public String toString() {
		return "AppSTS [id=" + id + ", appName=" + appName + ", endpoint=" + endpoint + ", certAlias=" + certAlias
				+ "]";
	}
	
	
	
}
