package com.nb.org.remote.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationConfig;
import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig;
import org.wso2.carbon.identity.application.common.model.xsd.Property;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;

import com.nb.org.application.client.ApplicationMgtClient;
import com.nb.org.application.client.OAuthMgtClient;
import com.nb.org.application.client.SAMLMgtClient;
import com.nb.org.application.client.STSMgtClient;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.util.GlobalConfig;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class RemoteAppMgt {
	private AppDefaultData data;
	private AppAllConfigInfo info;

	public RemoteAppMgt(AppAllConfigInfo info) {
		super();
		data = new AppDefaultData();
		this.info = info;
	}

	public RemoteAppMgt() {
		super();
		data = new AppDefaultData();
	}

	public AppDefaultData getData() {
		return data;
	}

	public void setData(AppDefaultData data) {
		this.data = data;
	}

	public AppAllConfigInfo getInfo() {
		return info;
	}

	public void setInfo(AppAllConfigInfo info) {
		this.info = info;
	}

	private ServiceProvider registerApp(String appName, String description) throws AppUpdateException {
		ApplicationMgtClient client = new ApplicationMgtClient(GlobalConfig.server);
		ServiceProvider sp;

		sp = new ServiceProvider();
		sp.setApplicationName(appName);
		sp.setDescription(description);
		client.createApplication(sp);
		ServiceProvider sss = client.getApplication(appName);
		sp = data.getDefaultServiceProvider();
		sss.setInboundAuthenticationConfig(sp.getInboundAuthenticationConfig());
		sss.setSaasApp(sp.getSaasApp());
		sss.setClaimConfig(sp.getClaimConfig());
		sss.setInboundProvisioningConfig(sp.getInboundProvisioningConfig());
		sss.setLocalAndOutBoundAuthenticationConfig(sp.getLocalAndOutBoundAuthenticationConfig());
		sss.setOutboundProvisioningConfig(sp.getOutboundProvisioningConfig());
		sss.setOwner(sp.getOwner());
		sss.setPermissionAndRoleConfig(sp.getPermissionAndRoleConfig());
		sss.setRequestPathAuthenticatorConfigs(sp.getRequestPathAuthenticatorConfigs());
		client.updateApplication(sss);
		return sss;
	}

	private OAuthConsumerAppDTO registerOAuth(String appName, String url) throws AppUpdateException {
		OAuthMgtClient client = new OAuthMgtClient(GlobalConfig.server);
		OAuthConsumerAppDTO dto = data.getDefaultOAuth();
		String callback = url;
		String grantCode = new String("authorization_code");
		String grantImplicit = new String("implicit");
		StringBuffer buff = new StringBuffer();
		buff.append(grantCode + " ");
		buff.append(grantImplicit + " ");
		OAuthConsumerAppDTO app = new OAuthConsumerAppDTO();
		app.setApplicationName(appName);
		app.setCallbackUrl(callback);
		app.setGrantTypes(dto.getGrantTypes());
		app.setOAuthVersion(dto.getOAuthVersion());
		client.registerOAuthApplicationData(app);
		return client.getOAuthApplicationDataByAppName(appName);
	}

	private SAMLSSOServiceProviderDTO registerSAML(String issuer, String consumerUrl, String logoutUrl)
			throws AppUpdateException {
		SAMLMgtClient client = new SAMLMgtClient(GlobalConfig.server);
		SAMLSSOServiceProviderDTO dto = data.getDefaultSAML();
		dto.setIssuer(issuer);
		dto.setAssertionConsumerUrl(consumerUrl);
		dto.setLogoutURL(logoutUrl);
		dto.setRequestedAudiences(new String[] { GlobalConfig.server + "/oauth2/token" });
		dto.setRequestedRecipients(new String[] { GlobalConfig.server + "/oauth2/token" });
		client.registerSAMLApplicationData(dto);
		return client.getSAMLApplicationDataByIssuer(dto.getIssuer());
	}

	private void registerSTS(String endpoint, String alias) throws AppUpdateException {
		STSMgtClient client = new STSMgtClient(GlobalConfig.server);
		client.registerSTS(endpoint, alias);
	}

	private void updateApp(String oldName, String appName, String description) throws AppUpdateException {
		ApplicationMgtClient client = new ApplicationMgtClient(GlobalConfig.server);
		ServiceProvider sp = client.getApplication(oldName);
		sp.setApplicationName(appName);
		sp.setDescription(description);
		client.updateApplication(sp);
	}

	private void updateOAuth(String oldName, String appName, String url) throws AppUpdateException {
		OAuthMgtClient client = new OAuthMgtClient(GlobalConfig.server);
		OAuthConsumerAppDTO dto = client.getOAuthApplicationDataByAppName(oldName);
		dto.setApplicationName(appName);
		dto.setCallbackUrl(url);
		client.updateConsumerApplication(dto);
	}

	private boolean updateSAML(String issuer, String consumerUrl, String logoutUrl) throws AppUpdateException {
		SAMLMgtClient client = new SAMLMgtClient(GlobalConfig.server);
		SAMLSSOServiceProviderDTO dto = client.getSAMLApplicationDataByIssuer(issuer);
		dto.setAssertionConsumerUrl(consumerUrl);
		dto.setLogoutURL(logoutUrl);
		return client.updateApplication(dto);
		// throw new AppUpdateException("SAML配置更新失败，请重试");
	}

	private void updateSTS(String oldEndpoint) throws AppUpdateException {
		STSMgtClient client = new STSMgtClient(GlobalConfig.server);
		client.updateApplication(oldEndpoint,data.getSTSData(info.getStsEndpoint(), info.getStsCertAlias()));
	}

	private void deleteOAuth(String key) throws AppUpdateException {
		OAuthMgtClient client = new OAuthMgtClient(GlobalConfig.server);
		client.removeOAuthApplicationData(key);
	}

	private void deleteSAML(String issuer) throws AppUpdateException {
		SAMLMgtClient client = new SAMLMgtClient(GlobalConfig.server);
		SAMLSSOServiceProviderDTO dto = client.getSAMLApplicationDataByIssuer(issuer);
		client.removeApplication(issuer);
	}

	private void deleteSTS(String endpoint) throws AppUpdateException {
		STSMgtClient client = new STSMgtClient(GlobalConfig.server);
		client.removeSTSApp(endpoint);
	}

	private void deleteApp(String name) throws AppUpdateException {
		ApplicationMgtClient client = new ApplicationMgtClient(GlobalConfig.server);
		client.deleteApplication(name);
	}

	public void updateServiceProvider(String oldName,String oldEndpoint) throws AppUpdateException {
		if (info.isSamlAvailable()) {
			boolean f = updateSAML(info.getSamlIssuer(), info.getSamlConsumerUrl(), info.getSamlLogoutUrl());
			if (!f) {
				throw new AppUpdateException("SAML配置更新失败，请重试");
			}
		}
		if (info.isOAuthAvailable()) {
			updateOAuth(oldName, info.getAppName(), info.getOauthUrl());
		}
		updateSTS(oldEndpoint);
		updateApp(oldName, info.getAppName(), info.getDescription());
	}

	public void deleteServiceProvider() throws AppUpdateException {
		if (info.isOAuthAvailable()) {
			try {
				deleteOAuth(info.getAppName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("OAuth配置删除失败，请重试");
			}
		}
		if (info.isSamlAvailable()) {
			try {
				deleteSAML(info.getSamlIssuer());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				registerOAuth(info.getAppName(), info.getOauthUrl());
				throw new AppUpdateException("SAML配置删除失败，请重试");
			}
		}
		try {
			deleteSTS(info.getStsEndpoint());
		} catch (Exception e) {
			registerOAuth(info.getAppName(), info.getOauthUrl());
			registerSAML(info.getSamlIssuer(), info.getSamlConsumerUrl(), info.getSamlLogoutUrl());
			throw new AppUpdateException("应用删除失败，请重试");
		}
		try {
			deleteApp(info.getAppName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			registerOAuth(info.getAppName(), info.getOauthUrl());
			registerSAML(info.getSamlIssuer(), info.getSamlConsumerUrl(), info.getSamlLogoutUrl());
			registerSTS(info.getStsEndpoint(), info.getStsCertAlias());
			throw new AppUpdateException("应用删除失败，请重试");
		}
	}

	public OAuthConsumerAppDTO registerServiceProvider() throws AppUpdateException {
		ApplicationMgtClient client = new ApplicationMgtClient(GlobalConfig.server);

		ServiceProvider sp = registerApp(info.getAppName(), info.getDescription());
		OAuthConsumerAppDTO returnOAuthDTO = null;

		InboundAuthenticationConfig inboundAuthenticationConfig = sp.getInboundAuthenticationConfig();
		if (inboundAuthenticationConfig == null)
			inboundAuthenticationConfig = new InboundAuthenticationConfig();
		if (info.isOAuthAvailable()) {
			OAuthConsumerAppDTO oauthDTO = registerOAuth(info.getAppName(), info.getOauthUrl());

			// InboundAuthenticationRequestConfig[] configs =
			// inboundAuthenticationConfig.getInboundAuthenticationRequestConfigs();
			InboundAuthenticationRequestConfig[] oauthirc = inboundAuthenticationConfig
					.getInboundAuthenticationRequestConfigs();
			InboundAuthenticationRequestConfig[] authconfigs;
			int length = 0;
			if (oauthirc == null)
				authconfigs = new InboundAuthenticationRequestConfig[1];
			else {
				length = oauthirc.length;
				authconfigs = new InboundAuthenticationRequestConfig[length + 1];
				System.arraycopy(oauthirc, 0, authconfigs, 0, length);
			}
			InboundAuthenticationRequestConfig oauthConfig = new InboundAuthenticationRequestConfig();
			oauthConfig.setInboundAuthKey(oauthDTO.getOauthConsumerKey());
			oauthConfig.setInboundAuthType("oauth2");
			Property property = new Property();
			property.setName("oauthConsumerSecret");
			property.setValue(oauthDTO.getOauthConsumerSecret());
			oauthConfig.setProperties(new Property[] { property });
			authconfigs[length] = oauthConfig;
			inboundAuthenticationConfig.setInboundAuthenticationRequestConfigs(authconfigs);
			sp.setInboundAuthenticationConfig(inboundAuthenticationConfig);

			returnOAuthDTO = oauthDTO;
		}
		if (info.isSamlAvailable()) {
			try {
				SAMLSSOServiceProviderDTO samlDTO = registerSAML(info.getSamlIssuer(), info.getSamlConsumerUrl(),
						info.getSamlLogoutUrl());
				InboundAuthenticationRequestConfig[] samlirc = inboundAuthenticationConfig
						.getInboundAuthenticationRequestConfigs();
				InboundAuthenticationRequestConfig[] samlconfigs;
				int length = 0;
				if (samlirc == null)
					samlconfigs = new InboundAuthenticationRequestConfig[1];
				else {
					length = samlirc.length;
					samlconfigs = new InboundAuthenticationRequestConfig[length + 1];
					System.arraycopy(samlirc, 0, samlconfigs, 0, length);
				}
				InboundAuthenticationRequestConfig samlConfig = new InboundAuthenticationRequestConfig();
				samlConfig.setInboundAuthKey(samlDTO.getIssuer());
				samlConfig.setInboundAuthType("samlsso");
				Property property = new Property();
				property.setName("attrConsumServiceIndex");
				property.setValue(samlDTO.getAttributeConsumingServiceIndex());
				samlConfig.setProperties(new Property[] { property });
				samlconfigs[length] = samlConfig;
				inboundAuthenticationConfig.setInboundAuthenticationRequestConfigs(samlconfigs);
				sp.setInboundAuthenticationConfig(inboundAuthenticationConfig);
			} catch (AppUpdateException e) {
				if (info.isOauthAvailable())
					deleteOAuth(returnOAuthDTO.getOauthConsumerKey());
				throw e;
			}
		}
		if (info.getStsEndpoint() != null) {
			try {
				registerSTS(info.getStsEndpoint(), info.getStsCertAlias());
				InboundAuthenticationRequestConfig[] stsirc = inboundAuthenticationConfig
						.getInboundAuthenticationRequestConfigs();
				InboundAuthenticationRequestConfig[] stsconfigs;
				int length = 0;
				if (stsirc == null)
					stsconfigs = new InboundAuthenticationRequestConfig[1];
				else {
					length = stsirc.length;
					stsconfigs = new InboundAuthenticationRequestConfig[length + 1];
					System.arraycopy(stsirc, 0, stsconfigs, 0, length);
				}
				InboundAuthenticationRequestConfig stsConfig = new InboundAuthenticationRequestConfig();
				stsConfig.setInboundAuthKey(info.getStsEndpoint());
				stsConfig.setInboundAuthType("wstrust");
				stsconfigs[length] = stsConfig;
				inboundAuthenticationConfig.setInboundAuthenticationRequestConfigs(stsconfigs);
				sp.setInboundAuthenticationConfig(inboundAuthenticationConfig);
			} catch (AppUpdateException e) {
				deleteOAuth(returnOAuthDTO.getOauthConsumerKey());
				deleteSAML(info.getSamlIssuer());
				throw e;
			}
		}
		try {
			client.updateApplication(sp);
		} catch (AppUpdateException e) {
			deleteOAuth(returnOAuthDTO.getOauthConsumerKey());
			deleteSAML(info.getSamlIssuer());
			deleteSTS(info.getStsEndpoint());
			throw e;
		}
		return returnOAuthDTO;
	}
	public String[] getCertAlias() throws AppUpdateException{
		STSMgtClient client = new STSMgtClient(GlobalConfig.server);
		String[] alias = client.getCertAlias();
		return alias;
	}
}
