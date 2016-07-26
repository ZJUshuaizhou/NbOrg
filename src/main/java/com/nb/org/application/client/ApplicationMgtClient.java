package com.nb.org.application.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceIdentityApplicationManagementException;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.AuthenticationException;
import com.nb.org.util.ServiceAuthenticator;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class ApplicationMgtClient {
	IdentityApplicationManagementServiceStub stub;

	public ApplicationMgtClient(String url) throws AppUpdateException {
		try {
			stub = new IdentityApplicationManagementServiceStub(null,
					url + "/services/IdentityApplicationManagementService");

			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}

	public ApplicationMgtClient() throws AppUpdateException {
		try {
			stub = new IdentityApplicationManagementServiceStub();

			ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
			authenticator.authenticate(stub._getServiceClient());
		} catch (AxisFault | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("请确保认证服务器配置正确。");
		}
	}
	public int createApplication(ServiceProvider serviceProvider) throws AppUpdateException {
		int returncode = -1;
		try {
			returncode = stub.createApplication(serviceProvider);
		} catch (RemoteException  e) {
			e.printStackTrace();
			throw new AppUpdateException("创建应用失败，连接认证服务器失败。");
		} catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("远程创建应用失败，与已有应用信息冲突，请重新填写信息并重试。");
		}
		return returncode;
	}

	public void updateApplication(ServiceProvider serviceProvider) throws AppUpdateException {
		try {
			stub.updateApplication(serviceProvider);

		}catch (RemoteException  e) {
			e.printStackTrace();
			throw new AppUpdateException("更新应用失败，连接认证服务器失败。");
		} catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("更新创建应用失败，与已有应用信息冲突，请重新填写信息并重试。");
		}
	}

	public ServiceProvider getApplication(String appName) throws AppUpdateException {
		ServiceProvider serviceProvider = new ServiceProvider();

		try {
			serviceProvider = stub.getApplication(appName);
		} catch (RemoteException  e) {
			e.printStackTrace();
			throw new AppUpdateException("获取应用失败，连接认证服务器失败。");
		} catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("从认证服务器获取应用失败。");
		}
		return serviceProvider;
	}

	public void deleteApplication(String appName) throws AppUpdateException {
		try {
			stub.deleteApplication(appName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("删除应用时，连接认证服务器失败。");
		} catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppUpdateException("认证服务器删除应用失败，请重试");
		}
	}

}
