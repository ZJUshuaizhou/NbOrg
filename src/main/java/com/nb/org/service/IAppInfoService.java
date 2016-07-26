package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppSAML;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.vo.ApplicationVO;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppInfoService {
	public int insertApp(AppInfo app);

	public int updateApp(AppInfo app) throws AppUpdateException;

	public int deleteApp(int id);

	public AppInfo getAppByName(String name);

	public AppInfo getAppById(int id);
	public List<AppInfo> getApps();

	public List<AppInfo> getAppsByPersonId(int id);
	public List<AppInfo> getAppsByDepartmentId(int id);
	public ApplicationVO listAppsPageByPersonID(ApplicationVO vo);
	public ApplicationVO listAppsPageByDepartmentID(ApplicationVO vo);
	
	public ApplicationVO searchAppByName(ApplicationVO vo);
	public ApplicationVO searchOtherApp(ApplicationVO vo);
	public List<AppInfo> getAppsByCreator(String username);
	IAppOAuthService getOauthService();

	IAppSAMLService getSamlService();

	IAppSTSService getStsService();

	public AppSAML addSaml(AppSAML saml, String name);
	}
