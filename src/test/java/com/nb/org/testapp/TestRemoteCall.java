package com.nb.org.testapp;


import org.junit.Test;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;

import com.nb.org.application.client.OAuthMgtClient;
import com.nb.org.remote.app.AppAllConfigInfo;
import com.nb.org.remote.app.AppConfigBuilder;
import com.nb.org.remote.app.RemoteAppMgt;
import com.nb.org.util.GlobalConfig;

public class TestRemoteCall {
	
	
	
//	private OAuthConsumerAppDTO registerOAuthApplicationData() throws Exception{
//		
//		OAuthMgtClient client = new OAuthMgtClient(GlobalConfig.server);
//		String callback = new String("http://test");
//		String oauthVersion = new String("OAuth-2.0");
//		String grantCode = new String("authorization_code");
//		String grantImplicit = new String("implicit");
//		String grants = null;
//		StringBuffer buff = new StringBuffer();
//		buff.append(grantCode + " ");
//		buff.append(grantImplicit + " ");
//		
//		grants = buff.toString();
//		OAuthConsumerAppDTO consumerApp = null , app = new OAuthConsumerAppDTO();
//		
//		app.setApplicationName("abcbb");
//		
//		app.setCallbackUrl(callback);
//		app.setOAuthVersion(oauthVersion);
//        app.setGrantTypes(grants);
//		//client.registerOAuthApplicationData(app);
//		return client.getOAuthApplicationDataByAppName("abcbb");	
//	}

	
	
	
	@Test
	public void testCompleteRemoteRegister(){
		AppConfigBuilder build = new AppConfigBuilder();
		AppAllConfigInfo info = build.setAppName("completeRegisterTest3").setDescription("���԰�")
		.setOauthUrl("http://oauth").setSamlConsumerUrl("http://saml")
		.setSamlIssuer("completeRegisterTest3").setSamlLogoutUrl("http://logout").build();		
		RemoteAppMgt mgt = new RemoteAppMgt(info);
		try {
			mgt.registerServiceProvider();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
