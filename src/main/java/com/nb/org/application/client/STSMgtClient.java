package com.nb.org.application.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.sts.mgt.stub.generic.STSAdminServiceStub;
import org.wso2.carbon.identity.sts.mgt.stub.generic.SecurityConfigExceptionException;
import org.wso2.carbon.identity.sts.mgt.stub.service.util.xsd.TrustedServiceData;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.AuthenticationException;
import com.nb.org.util.ServiceAuthenticator;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class STSMgtClient {
	private STSAdminServiceStub stub;

	public STSMgtClient(String url) throws AppUpdateException {
		try {
			stub = new STSAdminServiceStub(url + "/services/STSAdminService");
			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}

	}

	public STSMgtClient() throws AppUpdateException {
		try {
			stub = new STSAdminServiceStub();
			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public void registerSTS(String address, String alais) throws AppUpdateException {
		try {
			stub.addTrustedService(address, alais);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作STS配置时，连接认证服务器失败。");
		} catch (SecurityConfigExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，认证服务器处理失败。");
		}
	}

	public TrustedServiceData getSTSByAddress(String address) throws AppUpdateException {
		try {
			TrustedServiceData[] data = stub.getTrustedServices();
			for (TrustedServiceData d : data) {
				if (d.getServiceAddress().equals(address)) {
					return d;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作STS配置时，连接认证服务器失败。");
		} catch (SecurityConfigExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，认证服务器处理失败。");
		}
		return null;
	}

	public TrustedServiceData[] getSTSApps() throws AppUpdateException {
		try {
			return stub.getTrustedServices();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作STS配置时，连接认证服务器失败。");
		} catch (SecurityConfigExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，认证服务器处理失败。");
		}
	}

	public void removeSTSApp(String address) throws AppUpdateException {
		try {
			stub.removeTrustedService(address);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作STS配置时，连接认证服务器失败。");
		} catch (SecurityConfigExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作SAML配置时，认证服务器处理失败。");
		}
	}
	public void updateApplication(String oldEndpoint,TrustedServiceData dto) throws AppUpdateException {
		removeSTSApp(oldEndpoint);
		registerSTS(dto.getServiceAddress(), dto.getCertAlias());
	}
	public String[] getCertAlias() throws AppUpdateException{
		try {
			return stub.getCertAliasOfPrimaryKeyStore();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程操作STS配置时，连接认证服务器失败。");
		} catch (SecurityConfigExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程获取SAML配置时，认证服务器处理失败。");
		}
	}
}
