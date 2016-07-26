package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nb.org.dao.IAppInfoDao;
import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppOAuth;
import com.nb.org.domain.AppSAML;
import com.nb.org.domain.AppSTS;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppOAuthService;
import com.nb.org.service.IAppRoleService;
import com.nb.org.service.IAppSAMLService;
import com.nb.org.service.IAppSTSService;
import com.nb.org.vo.ApplicationVO;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
@Service("appInfoService")
public class AppInfoServiceImpl implements IAppInfoService {

	@Autowired
	private IAppInfoDao dao;

	@Autowired
	private IAppRoleService roleService;
	
	
	@Autowired
	private IAppOAuthService oauthService;

	@Autowired
	private IAppSAMLService samlService;
	@Autowired
	private IAppSTSService stsService;
	private AppInfo appInfo = null;

	public AppInfo getAppInfo() {
		return appInfo;
	}

	@Override
	public IAppOAuthService getOauthService() {
		return oauthService;
	}

	@Override
	public IAppSAMLService getSamlService() {
		return samlService;
	}

	@Override
	public IAppSTSService getStsService() {
		return stsService;
	}

	@Override
	@Transactional
	public AppSAML addSaml(AppSAML saml,String appName){
		AppInfo exist = dao.getAppByName(appName);
		if (exist != null) {
			return null;
		}
		int samlId=0;
		if (saml != null) {
			samlId = samlService.insertSAML(saml);
			saml.setAppName(appName);
			String issuer = "issuer" + String.valueOf(samlId);
			while(samlService.getSAMLByAppIssuer(issuer)!=null){
				samlId++;
				issuer = "issuer" + String.valueOf(samlId);
			}
			saml.setId(samlId);
			saml.setIssuer(issuer);
			samlService.setIssuer(saml);
			return saml;
		}else
			return null;
	}
	@Transactional
	@Override
	public int insertApp(AppInfo app) {
		// TODO Auto-generated method stub
		AppInfo exist = dao.getAppByName(app.getName());
		if (exist != null) {
			return -1;
		}
		AppOAuth oauth = app.getOauth();
		//AppSAML saml = app.getSaml();
		AppSTS sts = app.getSts();
		
		Integer oauthId = null, samlId = null , stsId;
		if (oauth != null) {
			oauthId = oauthService.insertOAuth(oauth);
			oauth.setAppName(app.getName());
			oauth.setId(oauthId);
			app.setOauth(oauth);
		}
//		if (saml != null) {
//			samlId = samlService.insertSAML(saml);
//			saml.setAppName(app.getName());
//			saml.setId(samlId);
//			app.setSaml(saml);
//		}
		if(sts != null){
			stsId = stsService.insertSTS(sts);
			sts.setAppName(app.getName());
			sts.setId(stsId);
			app.setSts(sts);
		}

		appInfo = app;
		dao.insertApp(app);
		return dao.getAppByName(app.getName()).getId();

	}

	@Transactional
	@Override
	public int updateApp(AppInfo app) throws AppUpdateException{
		// TODO Auto-generated method stub
		AppInfo appl = dao.getAppByName(app.getName());
		if(appl != null&&!(appl.getId().equals(app.getId()))){
			AppUpdateException e = new AppUpdateException("应用名称已存在，不能重名。");
			throw e;
		}
		AppOAuth oauth = app.getOauth();
		AppSAML saml = app.getSaml();
		AppSTS sts = app.getSts();
		if(appl == null)
		{
			oauth.setAppName(app.getName());
			saml.setAppName(app.getName());
			sts.setAppName(app.getName());
		}
		try{
		oauthService.updateOAuth(oauth);
		samlService.updateSAML(saml);
		stsService.updateSTS(sts);
		return dao.updateApp(app);
		}catch(Exception e){
			throw new AppUpdateException("操作失败，请重试。");
		}
	}

	@Transactional
	@Override
	public int deleteApp(int id) {
		// TODO Auto-generated method stub
		
		AppInfo app = dao.getAppById(id);
		if(app == null)
			return -1;
		roleService.removeRolesForApp(id);
		oauthService.deleteOAuth(app.getOauth().getId());
		samlService.deleteSAML(app.getSaml().getId());
		stsService.deleteSTS(app.getSts().getId());
		return dao.deleteApp(id);
	}
	@Override
	public AppInfo getAppByName(String name) {
		// TODO Auto-generated method stub
		return dao.getAppByName(name);
	}

	@Override
	public List<AppInfo> getApps() {
		// TODO Auto-generated method stub
		return dao.getApps();
	}

	@Override
	public AppInfo getAppById(int id) {
		// TODO Auto-generated method stub
		return dao.getAppById(id);
	}

	@Override
	public List<AppInfo> getAppsByPersonId(int id) {
		// TODO Auto-generated method stub
		return dao.getAppsByPersonId(id);
	}

	@Override
	public List<AppInfo> getAppsByDepartmentId(int id) {
		return dao.getAppsByDepartmentId(id);
	}

	@Override
	public ApplicationVO listAppsPageByPersonID(ApplicationVO vo) {
		List<AppInfo> apps = dao.listAppsPageByPId(vo);
		int total = dao.countAppsPageByPId(vo.getVisitorId());
		vo.setTotal(total);
		vo.setRows(apps);
		return vo;
	}

	@Override
	public ApplicationVO listAppsPageByDepartmentID(ApplicationVO vo) {
		List<AppInfo> apps = dao.listAppsPageByDId(vo);
		int total = dao.countAppsPageByDId(vo);
		vo.setTotal(total);
		vo.setRows(apps);
		return vo;
	}

	@Override
	public ApplicationVO searchAppByName(ApplicationVO vo) {
		List<AppInfo> apps = dao.searchAppByName(vo);
		int total = dao.countSearchAppByName(vo);
		vo.setTotal(total);
		vo.setRows(apps);
		return vo;
	}

	@Override
	public ApplicationVO searchOtherApp(ApplicationVO vo) {
		List<AppInfo> apps = dao.searchOtherApp(vo);
		int total = dao.countSearchOtherApp(vo);
		vo.setTotal(total);
		vo.setRows(apps);
		return vo;
	}

	@Override
	@Transactional
	public List<AppInfo> getAppsByCreator(String username) {
		// TODO Auto-generated method stub
		try{
		int creatorId = dao.getPersonIdByUsername(username);
		return dao.getAppsByPersonId(creatorId);
		}catch(Exception e){
			return new ArrayList<AppInfo>();
		}
	}
}
