package com.nb.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppSAML {
	private Integer id;
	private String appName;
	@JsonIgnore
	private String issuer;
	private String url;
	private String logoutUrl;
	
	
	public AppSAML(Integer id, String appName,String issuer, String url, String logoutUrl) {
		super();
		this.id = id;
		this.appName = appName;
		this.issuer = issuer;
		this.url = url;
		this.logoutUrl = logoutUrl;
	}


	public AppSAML() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AppSAML(String appName,String issuer, String url, String logoutUrl) {
		super();
		this.appName = appName;
		this.issuer = issuer;
		this.url = url;
		this.logoutUrl = logoutUrl;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getIssuer() {
		return issuer;
	}


	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getLogoutUrl() {
		return logoutUrl;
	}


	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}


	@Override
	public String toString() {
		return "AppSAML [id=" + id + ", issuer=" + issuer + ", url=" + url
				+ ", logoutUrl=" + logoutUrl + "]";
	}
	
	
}
