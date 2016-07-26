package com.nb.org.remote.app;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppAllConfigInfo {
	
	private boolean oauthAvailable;
	private boolean samlAvailable;
	private String appName;
	private String description;
	private String oauthUrl;
	private String samlConsumerUrl;
	private String samlLogoutUrl;
	private String samlIssuer;
	private String stsEndpoint;
	private String stsCertAlias;
	
	 
	
	public AppAllConfigInfo(boolean oauthAvailable, boolean samlAvailable) {
		super();
		this.oauthAvailable = oauthAvailable;
		this.samlAvailable = samlAvailable;
	}



	public boolean isOAuthAvailable() {
		return oauthAvailable;
	}



	public void setOAuthAvailable(boolean oauthAvailable) {
		this.oauthAvailable = oauthAvailable;
	}



	public boolean isSamlAvailable() {
		return samlAvailable;
	}



	public void setSamlAvailable(boolean samlAvailable) {
		this.samlAvailable = samlAvailable;
	}



	public boolean isOauthAvailable() {
		return oauthAvailable;
	}



	public void setOauthAvailable(boolean oauthAvailable) {
		this.oauthAvailable = oauthAvailable;
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



	public String getAppName() {
		return appName;
	}



	public void setAppName(String appName) {
		this.appName = appName;
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



	public String getSamlConsumerUrl() {
		return samlConsumerUrl;
	}



	public void setSamlConsumerUrl(String samlConsumerUrl) {
		this.samlConsumerUrl = samlConsumerUrl;
	}



	public String getSamlLogoutUrl() {
		return samlLogoutUrl;
	}



	public void setSamlLogoutUrl(String samlLogoutUrl) {
		this.samlLogoutUrl = samlLogoutUrl;
	}



	public String getSamlIssuer() {
		return samlIssuer;
	}



	public void setSamlIssuer(String samlIssuer) {
		this.samlIssuer = samlIssuer;
	}



	@Override
	public String toString() {
		return "AppAllConfigInfo [oauthAvailable=" + oauthAvailable + ", samlAvailable=" + samlAvailable + ", appName="
				+ appName + ", description=" + description + ", oauthUrl=" + oauthUrl + ", samlConsumerUrl="
				+ samlConsumerUrl + ", samlLogoutUrl=" + samlLogoutUrl + ", samlIssuer=" + samlIssuer + ", stsEndpoint="
				+ stsEndpoint + ", stsCertAlias=" + stsCertAlias + "]";
	}


}
