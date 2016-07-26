package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppOAuth;
import com.nb.org.domain.AppSAML;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.remote.app.AppAllConfigInfo;
import com.nb.org.remote.app.AppConfigBuilder;
import com.nb.org.remote.app.RemoteAppMgt;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppMgtService;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
@Service("appMgtService")
public class AppMgtService implements IAppMgtService {

	@Autowired
	private IAppInfoService appInfoService;
	private RemoteAppMgt wso2Mgt;
	private AppAllConfigInfo allInfo;

	public IAppInfoService getAppInfoService() {
		return appInfoService;
	}

	public RemoteAppMgt getWso2Mgt() {
		return new RemoteAppMgt();
	}

	private void initWso2Mgt(AppInfo app) {
		boolean oAuthAvailable = false, samlAvailable = false;
		AppOAuth oauth = app.getOauth();
		AppSAML saml = app.getSaml();
		if (oauth != null) {
			oAuthAvailable = true;
		}
		if (saml != null) {
			samlAvailable = true;
		}
		AppConfigBuilder builder = new AppConfigBuilder(oAuthAvailable, samlAvailable);
		if (oAuthAvailable) {
			builder.setOauthUrl(oauth.getUrl());
		}
		if (samlAvailable) {
			builder.setSamlConsumerUrl(saml.getUrl()).setSamlIssuer(saml.getIssuer())
					.setSamlLogoutUrl(saml.getLogoutUrl());
		}
		allInfo = builder.setAppName(app.getName()).setDescription(app.getDescription())
				.setSTSCertAlias(app.getSts().getCertAlias()).setSTSEndpoint(app.getSts().getEndpoint()).build();

		wso2Mgt = new RemoteAppMgt(allInfo);
	}

	@Override
	public void addApplication(AppInfo app) throws AppUpdateException {
		// TODO Auto-generated method stub
		AppInfo temp = appInfoService.getAppByName(app.getName());
		if(temp!=null){
			throw new AppUpdateException("注册失败,应用名称已存在，请重新填写！");
		}
		app.setSaml(appInfoService.addSaml(app.getSaml(),app.getName()));
		initWso2Mgt(app);
		OAuthConsumerAppDTO oauthDTO = null;
		oauthDTO = wso2Mgt.registerServiceProvider();
		app.getOauth().setOauthKey(oauthDTO.getOauthConsumerKey());
		app.getOauth().setOauthSecret(oauthDTO.getOauthConsumerSecret());
		try {
			appInfoService.insertApp(app);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wso2Mgt.deleteServiceProvider();
			throw new AppUpdateException("注册失败,名称或标识已存在，请重新填写！");
		}
	}
	@Override
	public void updateApplication(AppInfo app) throws AppUpdateException {
		AppInfo temp = appInfoService.getAppByName(app.getName());
		if(temp!=null&&!temp.getId().equals(app.getId())){
			throw new AppUpdateException("更新失败,应用名称已存在，请重新填写！");
		}
		initWso2Mgt(app);
		AppInfo a = appInfoService.getAppById(app.getId());
		String oldName = a.getName();
		String oldEnd = a.getSts().getEndpoint();
		wso2Mgt.updateServiceProvider(oldName, oldEnd);
		appInfoService.updateApp(app);
	}

	@Override
	public AppInfo getApplication(String appName) {
		return appInfoService.getAppByName(appName);
	}

	@Override
	public void removeApplication(AppInfo app) throws AppUpdateException {
		initWso2Mgt(app);
		wso2Mgt.deleteServiceProvider();
		try{
			appInfoService.deleteApp(app.getId());
		}catch(Exception e){
			e.printStackTrace();
			wso2Mgt.registerServiceProvider();
			throw new AppUpdateException("删除失败,未知错误，请重试！");
		}
	}
}
