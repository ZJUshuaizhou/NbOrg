package com.nb.org.application.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.AuthenticationException;
import com.nb.org.util.ServiceAuthenticator;


/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class OAuthMgtClient {
	private OAuthAdminServiceStub stub;

	public OAuthMgtClient(String url) throws AppUpdateException {
		try {
			stub = new OAuthAdminServiceStub(null, url + "/services/OAuthAdminService");

			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public OAuthMgtClient() throws AppUpdateException {
		try {
			stub = new OAuthAdminServiceStub();

			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public void registerOAuthApplicationData(OAuthConsumerAppDTO app) {
		try {
			stub.registerOAuthApplicationData(app);
		} catch (RemoteException | OAuthAdminServiceException e) {
			e.printStackTrace();
		}
	}

	public OAuthConsumerAppDTO getOAuthApplicationDataByAppName(String applicationName) throws AppUpdateException {
		try {
			return stub.getOAuthApplicationDataByAppName(applicationName);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程获取OAuth配置时，连接认证服务器失败。");
		} catch (OAuthAdminServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程获取OAuth配置时，认证服务器处理失败，请确保信息正确。");
		}
	}

	public void updateConsumerApplication(OAuthConsumerAppDTO dto) throws AppUpdateException {
		try {
			stub.updateConsumerApplication(dto);

		} catch (RemoteException e) {
			e.printStackTrace();
			throw new AppUpdateException("远程更新OAuth配置时，连接认证服务器失败。");
		} catch (OAuthAdminServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("OAuth配置更新时，认证服务器处理失败，请确保信息正确。");
		}
	}

	public void removeOAuthApplicationData(String key) throws AppUpdateException {
		try {
			stub.removeOAuthApplicationData(key);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程删除OAuth配置的时候失败，连接认证服务器失败。");
		} catch (OAuthAdminServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("OAuth配置删除时，认证服务器处理失败，请确保信息正确。");
		}
	}

	public OAuthConsumerAppDTO getOAuthApplicationData(String key) throws AppUpdateException {
		try {
			return stub.getOAuthApplicationData(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程获取OAuth配置时，连接认证服务器失败。");
		} catch (OAuthAdminServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程获取OAuth配置时，认证服务器处理失败，请确保信息正确。");
		}
	}

}
