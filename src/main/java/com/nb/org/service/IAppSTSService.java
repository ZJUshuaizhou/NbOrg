package com.nb.org.service;

import com.nb.org.domain.AppSTS;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppSTSService {
	public int insertSTS(AppSTS app);
	public int updateSTS(AppSTS app);
	public int deleteSTS(int id);
	public AppSTS getSTSById(int id);
	public AppSTS getSTSByAppName(String name);
}
