package com.nb.org.remote.app;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig;
import org.wso2.carbon.identity.application.common.model.xsd.Property;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sts.mgt.stub.generic.STSAdminServiceStub;
import org.wso2.carbon.identity.sts.mgt.stub.service.util.xsd.TrustedServiceData;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.util.GlobalConfig;
import com.thoughtworks.xstream.XStream;

/**
 * @author huangxin xin 2016年1月22日
 */
public class AppDefaultData {
	private InputStream inputStream;
	private DocumentBuilder builder;

	public ServiceProvider getDefaultServiceProvider() throws AppUpdateException {
		try {
			inputStream = AppDefaultData.class.getClassLoader().getResourceAsStream("application-default.xml");
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			ServiceProvider sp = null;
			Document document = null;
			Document spdom = builder.newDocument();
			document = builder.parse(inputStream);
			NodeList l = document.getElementsByTagName("ServiceProvider");
			Node item = l.item(0);
			Node spNode = spdom.importNode(item, true);
			spdom.appendChild(spNode);
			org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
			org.dom4j.Document spdom4j = xmlReader.read(spdom);
			XStream xstream = new XStream();
			xstream.alias("ServiceProvider", ServiceProvider.class);
			xstream.alias("ClaimMapping", org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping.class);
			xstream.alias("InboundAuthenticationRequestConfig", InboundAuthenticationRequestConfig.class);
			xstream.alias("Property", Property.class);
			sp = (ServiceProvider) xstream.fromXML(spdom4j.asXML());
			inputStream.close();
			return sp;
		} catch (Exception e) {
			throw new AppUpdateException("默认配置加载失败。");
		}
	}

	public OAuthConsumerAppDTO getDefaultOAuth() throws AppUpdateException {
		try {
			inputStream = AppDefaultData.class.getClassLoader().getResourceAsStream("application-default.xml");
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			OAuthConsumerAppDTO oauth = null;
			Document document = null;
			Document oauthDom = builder.newDocument();
			document = builder.parse(inputStream);
			NodeList l = document.getElementsByTagName("OAuth");
			Node item = l.item(0);
			Node oauthNode = oauthDom.importNode(item, true);
			oauthDom.appendChild(oauthNode);
			org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
			org.dom4j.Document spdom4j = xmlReader.read(oauthDom);
			XStream xstream = new XStream();
			xstream.alias("OAuth", OAuthConsumerAppDTO.class);
			oauth = (OAuthConsumerAppDTO) xstream.fromXML(spdom4j.asXML());
			inputStream.close();
			return oauth;
		} catch (Exception e) {
			throw new AppUpdateException("默认配置加载失败。");
		}
	}

	public String getDefaultSTSCertAlias() throws AppUpdateException {
		inputStream = AppDefaultData.class.getClassLoader().getResourceAsStream("application-default.xml");
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(inputStream);
			Node item = document.getElementsByTagName("STS").item(0);
			NodeList certs = item.getChildNodes();
			int len = certs.getLength();
			for (int i = 0; i < len; i++) {
				Node node = certs.item(i);
				if (node.getNodeName().equals("DefaultCertAlias")) {
					return node.getFirstChild().getNodeValue();
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AppUpdateException("默认配置加载失败。");
		}
	}

	public TrustedServiceData getSTSData(String address, String alias) {
		TrustedServiceData data = new TrustedServiceData();
		data.setCertAlias(alias);
		data.setServiceAddress(address);
		return data;
	}

	public SAMLSSOServiceProviderDTO getDefaultSAML() throws AppUpdateException {
		try {
			inputStream = AppDefaultData.class.getClassLoader().getResourceAsStream("application-default.xml");
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SAMLSSOServiceProviderDTO saml = null;
			Document document = null;
			Document samlDom = builder.newDocument();
			document = builder.parse(inputStream);
			NodeList l = document.getElementsByTagName("SAML");
			Node item = l.item(0);
			Node samlNode = samlDom.importNode(item, true);
			samlDom.appendChild(samlNode);
			org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
			org.dom4j.Document spdom4j = xmlReader.read(samlDom);
			XStream xstream = new XStream();
			xstream.alias("SAML", SAMLSSOServiceProviderDTO.class);
			saml = (SAMLSSOServiceProviderDTO) xstream.fromXML(spdom4j.asXML());
			String[] tokenUrl = saml.getRequestedAudiences();
			if (tokenUrl == null || tokenUrl.length == 0)
				tokenUrl = new String[] { new String(GlobalConfig.server + "/oauth2/token") };
			saml.setRequestedAudiences(tokenUrl);
			tokenUrl = saml.getRequestedRecipients();
			if (tokenUrl == null || tokenUrl.length == 0)
				tokenUrl = new String[] { new String(GlobalConfig.server + "/oauth2/token") };
			saml.setRequestedRecipients(tokenUrl);
			inputStream.close();
			return saml;
		} catch (Exception e) {
			throw new AppUpdateException("默认配置加载失败。");
		}
	}
}
