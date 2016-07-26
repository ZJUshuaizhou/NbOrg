package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.AppInfo;
import com.nb.org.vo.ApplicationVO;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public interface IAppInfoDao {
	public int insertApp(AppInfo app);
	public int updateApp(AppInfo app);
	public int deleteApp(int id);
	public AppInfo getAppByName(String name);
	public AppInfo getAppById(int id);
	public List<AppInfo> getAppsByPersonId(int id);
	public List<AppInfo> getAppsByDepartmentId(int id);
	public List<AppInfo> getApps();
	public List<AppInfo> listAppsPageByPId(ApplicationVO vo);
	public int countAppsPageByPId(int pid);
	public List<AppInfo> listAppsPageByDId(ApplicationVO vo);
	public int countAppsPageByDId(ApplicationVO vo);
	public List<AppInfo> searchAppByName(ApplicationVO vo);
	public int countSearchAppByName(ApplicationVO vo);
	public List<AppInfo> searchOtherApp(ApplicationVO vo);
	public int countSearchOtherApp(ApplicationVO vo);
	public int getPersonIdByUsername(String username);
	
}
