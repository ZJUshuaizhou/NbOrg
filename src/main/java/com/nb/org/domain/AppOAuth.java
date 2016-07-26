package com.nb.org.domain;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppOAuth {
	private Integer id;
	private String appName;
	private String url;
	private String oauthKey;
	private String oauthSecret;



	public AppOAuth(Integer id, String appName,String url, String oauthKey, String oauthSecret) {
		super();
		this.id = id;
		this.appName = appName;
		this.url = url;
		this.oauthKey = oauthKey;
		this.oauthSecret = oauthSecret;
	}


	public AppOAuth() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AppOAuth(String appName,String url, String oauthKey, String oauthSecret) {
		super();
		this.appName = appName;
		this.url = url;
		this.oauthKey = oauthKey;
		this.oauthSecret = oauthSecret;
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getOauthKey() {
		return oauthKey;
	}


	public void setOauthKey(String oauthKey) {
		this.oauthKey = oauthKey;
	}


	public String getOauthSecret() {
		return oauthSecret;
	}


	public void setOauthSecret(String oauthSecret) {
		this.oauthSecret = oauthSecret;
	}


	@Override
	public String toString() {
		return "AppOAuth [id=" + id + ", url=" + url + ", oauthKey=" + oauthKey
				+ ", oauthSecret=" + oauthSecret + "]";
	}
	
	
	
}
