package com.nb.org.testapp;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.xml.sax.SAXException;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.remote.app.AppDefaultData;

public class TestAppDefaultData {

	@Test
	public void testGetApp() throws AppUpdateException{
		new AppDefaultData().getDefaultServiceProvider();
	}
	
	
	
	@Test
	public void testonfig() throws Exception {
		AppDefaultData data = new AppDefaultData();
		ServiceProvider sp = data.getDefaultServiceProvider();
		OAuthConsumerAppDTO oauthDTO = data.getDefaultOAuth();
		SAMLSSOServiceProviderDTO samlDTO = data.getDefaultSAML();
		
		System.out.println(sp.getSaasApp());
		System.out.println(samlDTO.getNameIDFormat());
		
		
		System.out.println(oauthDTO.getCallbackUrl());
		System.out.println(oauthDTO.getGrantTypes());
		
		
	}
}
