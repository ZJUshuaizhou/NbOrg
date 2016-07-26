package com.nb.org.service;

import com.nb.org.domain.AppSAML;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppSAMLService {
	public int insertSAML(AppSAML app);
	public int updateSAML(AppSAML app);
	public int deleteSAML(int id);
	public AppSAML getSAMLById(int id);
	public AppSAML getSAMLByAppName(String name);
	public AppSAML getSAMLByAppIssuer(String issuer);
	public int setIssuer(AppSAML app);
}
