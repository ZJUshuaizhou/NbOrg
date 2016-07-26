package com.nb.org.remote.app;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppConfigBuilder {
	AppAllConfigInfo appAllConfigInfo;
	
	public AppConfigBuilder(AppAllConfigInfo app){
		super();
		appAllConfigInfo = app;
	}
	
	public AppConfigBuilder(){
		super();
		appAllConfigInfo = new AppAllConfigInfo(true, true);
	}
	
	public AppConfigBuilder(boolean oAuthAvailable,boolean samlAvailable){
		super();
		appAllConfigInfo = new AppAllConfigInfo(oAuthAvailable, samlAvailable);
	}
	
	public AppAllConfigInfo getAppAllConfigInfo() {
		return appAllConfigInfo;
	}

	public AppAllConfigInfo build(){
		return appAllConfigInfo;
	}


	public AppConfigBuilder setAppName(String appName) {
		appAllConfigInfo.setAppName(appName);
		return this;
	}






	public AppConfigBuilder setDescription(String description) {
		appAllConfigInfo.setDescription(description);
		return this;
	}





	public AppConfigBuilder setOauthUrl(String oauthUrl) {
		appAllConfigInfo.setOauthUrl(oauthUrl);
		return this;
	}






	public AppConfigBuilder setSamlConsumerUrl(String samlConsumerUrl) {
		appAllConfigInfo.setSamlConsumerUrl(samlConsumerUrl);
		return this;
	}




	public AppConfigBuilder setSamlLogoutUrl(String samlLogoutUrl) {
		appAllConfigInfo.setSamlLogoutUrl(samlLogoutUrl);
		return this;
	}



	public AppConfigBuilder setSamlIssuer(String samlIssuer) {
		appAllConfigInfo.setSamlIssuer(samlIssuer);
		return this;
	}
	
	public AppConfigBuilder setSTSEndpoint(String endpoint) {
		appAllConfigInfo.setStsEndpoint(endpoint);
		return this;
	}
	public AppConfigBuilder setSTSCertAlias(String certAlias) {
		appAllConfigInfo.setStsCertAlias(certAlias);
		return this;
	}
}
