package com.nb.org.dao;

import com.nb.org.domain.AppOAuth;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppOAuthDao {
	public int insertOAuth(AppOAuth app);
	public int updateOAuth(AppOAuth app);
	public int deleteOAuth(int id);
	public AppOAuth getOAuthById(int id);
	public AppOAuth getOAuthByAppName(String name);
}
