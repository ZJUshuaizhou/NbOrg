package com.nb.org.application.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceIdentityException;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceStub;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOServiceStub;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.AuthenticationException;
import com.nb.org.util.ServiceAuthenticator;


/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class SAMLMgtClient {
	private IdentitySAMLSSOServiceStub stub;
	private IdentitySAMLSSOConfigServiceStub stub2;

	public SAMLMgtClient(String url) throws AppUpdateException{

		try {
			stub = new IdentitySAMLSSOServiceStub(url
					+ "/services/IdentitySAMLSSOService");
		

		stub2 = new IdentitySAMLSSOConfigServiceStub(url
				+ "/services/IdentitySAMLSSOConfigService");
		ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
		authenticator.authenticate(stub._getServiceClient());
		authenticator.authenticate(stub2._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public SAMLMgtClient() throws AppUpdateException {
		try{
		stub = new IdentitySAMLSSOServiceStub();
		stub2 = new IdentitySAMLSSOConfigServiceStub();
		ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
		authenticator.authenticate(stub._getServiceClient());
		authenticator.authenticate(stub2._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public boolean registerSAMLApplicationData(SAMLSSOServiceProviderDTO app) throws AppUpdateException{
		boolean result = false;
		try {
			result = stub2.addRPServiceProvider(app);
		}catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，连接认证服务器失败。");
		} catch (IdentitySAMLSSOConfigServiceIdentityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，连接认证服务器处理失败。");
		}
		return result;
	}

	public SAMLSSOServiceProviderDTO getSAMLApplicationDataByIssuer(String issuer) throws AppUpdateException{
			
			SAMLSSOServiceProviderInfoDTO dtos;
			try {
				dtos = stub2.getServiceProviders();
			}catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器失败。");
			} catch (IdentitySAMLSSOConfigServiceIdentityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器处理失败。");
			}
			SAMLSSOServiceProviderDTO[] dto = dtos.getServiceProviders();
			for(SAMLSSOServiceProviderDTO d : dto){
				if(issuer.equals(d.getIssuer())){
					return d;
				}
			}
			return null;
	}
	public SAMLSSOServiceProviderInfoDTO getSAMLApps() throws AppUpdateException{
			try {
				return stub2.getServiceProviders();
			}catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器失败。");
			} catch (IdentitySAMLSSOConfigServiceIdentityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器处理失败。");
			}
	}
	
	public boolean removeApplication(String issuer) throws AppUpdateException {
			try {
				return stub2.removeServiceProvider(issuer);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器失败。");
			} catch (IdentitySAMLSSOConfigServiceIdentityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppUpdateException("远程操作SAML配置时，连接认证服务器处理失败。");
			}
	}
	
	public boolean updateApplication(SAMLSSOServiceProviderDTO dto) throws AppUpdateException{
		return removeApplication(dto.getIssuer())&&registerSAMLApplicationData(dto);
	}
}
