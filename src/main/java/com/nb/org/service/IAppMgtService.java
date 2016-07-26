package com.nb.org.service;

import com.nb.org.domain.AppInfo;
import com.nb.org.exception.AppUpdateException;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppMgtService {
	public void addApplication(AppInfo app) throws AppUpdateException;

	public void updateApplication(AppInfo app) throws Exception;

	public AppInfo getApplication(String appName);

	public void removeApplication(AppInfo app) throws AppUpdateException;

}
