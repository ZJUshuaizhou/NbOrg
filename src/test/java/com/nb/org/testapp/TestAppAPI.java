package com.nb.org.testapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig;
import org.wso2.carbon.identity.application.common.model.xsd.Property;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO;

import com.nb.org.application.client.ApplicationMgtClient;
import com.nb.org.application.client.OAuthMgtClient;
import com.nb.org.application.client.SAMLMgtClient;
import com.nb.org.dao.IAppRoleDao;
import com.nb.org.dao.IAppRoleDeparmentDao;
import com.nb.org.dao.IAppRolePersonDao;
import com.nb.org.domain.AppRole;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.service.IAppOAuthService;
import com.nb.org.service.IAppSAMLService;
import com.nb.org.util.GlobalConfig;
import com.thoughtworks.xstream.XStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestAppAPI {
	private static Logger logger = Logger.getLogger(TestAppInfo.class);

//	@Autowired
//	private IAppOAuthService oauthService;
	@Autowired
	private IAppSAMLService samlService;
//	@Autowired
//	private IAppRolePersonDao personDao;
	
	@Autowired
	private IAppRoleDeparmentDao depDao;
	
	@Autowired
	private IAppRoleDao appRole;
	
	@Test
	public void createApp() {
		ApplicationMgtClient client = null;
		try {
			client = new ApplicationMgtClient(GlobalConfig.server);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceProvider serviceProvider = new ServiceProvider();
		serviceProvider.setApplicationName("test1abc");
		serviceProvider.setDescription("HaHa! I made it!");
		int r = 0;
		try {
			r = client.createApplication(serviceProvider);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(r);
	}
	
	@Test
	public void testAuthDao(){
		
		samlService.getSAMLByAppName("trriee");
//		oauthService.insertOAuth(new AppOAuth("111","aa","bb","secret"));
//		oauthService.getOAuthById(3);
	}
	

	@Test
	public void testGetSp() {
		try {
			ApplicationMgtClient client = new ApplicationMgtClient(
					GlobalConfig.server);
			ServiceProvider sp = client.getApplication("samlssosamplecom");
			OutputStream out = new FileOutputStream("E:\\zzz.xml");
			XStream xstream = new XStream();
			xstream.alias("ServiceProvider", ServiceProvider.class);
			xstream.alias(
					"ClaimMapping",
					org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping.class);
			xstream.alias("InboundAuthenticationRequestConfig",
					InboundAuthenticationRequestConfig.class);
			xstream.alias("Property", Property.class);
			xstream.toXML(sp, out);
			System.out.println(sp.getDescription());
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getOAuth() throws FileNotFoundException {
		OAuthMgtClient client = null;
		try {
			client = new OAuthMgtClient(GlobalConfig.server);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OAuthConsumerAppDTO dto = null;
		try {
			dto = client
					.getOAuthApplicationDataByAppName("trriee");
			System.out.println(dto.getOauthConsumerKey());
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		OutputStream out = new FileOutputStream("E:\\OAUTH.xml");
//		XStream xstream = new XStream();
//		xstream.alias("OAuth", OAuthConsumerAppDTO.class);
//
//		xstream.toXML(dto, out);

		// client.registerOAuthApplicationData(dto);
	}



	@Test
	public void getSAML() throws AppUpdateException {
		SAMLMgtClient client = null;
		try {
			client = new SAMLMgtClient(GlobalConfig.server);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAMLSSOServiceProviderInfoDTO dto = client
				.getSAMLApps();
		SAMLSSOServiceProviderDTO dto1 = dto.getServiceProviders()[9];

		OutputStream out;
		try {
			out = new FileOutputStream("E:\\SAML.xml");
			XStream xstream = new XStream();
			xstream.alias("SAML", SAMLSSOServiceProviderDTO.class);
			xstream.toXML(dto1, out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
