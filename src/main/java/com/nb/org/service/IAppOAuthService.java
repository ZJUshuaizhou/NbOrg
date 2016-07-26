package com.nb.org.service;

import com.nb.org.domain.AppOAuth;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppOAuthService {
	public int insertOAuth(AppOAuth app);
	public int updateOAuth(AppOAuth app);
	public int deleteOAuth(int id);
	public AppOAuth getOAuthById(int id);
}
