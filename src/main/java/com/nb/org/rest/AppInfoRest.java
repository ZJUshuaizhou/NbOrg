package com.nb.org.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppOAuth;
import com.nb.org.domain.AppSAML;
import com.nb.org.domain.AppSTS;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.DepartmentException;
import com.nb.org.remote.app.RemoteAppMgt;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppMgtService;
import com.nb.org.service.IPositionService;
import com.nb.org.util.StringUtil;
import com.nb.org.vo.AppInfoVO;

@Controller
@RequestMapping("/rest/appInfo")
public class AppInfoRest {
	
	@Autowired
	private IAppInfoService appInfoService;
	
	@Autowired
	private IAppMgtService appMgtService;
	
	@Autowired
	private IPositionService positionService;
	
	
	/**
	 * @author upshi
	 * @date 20160223
	 * @url /rest/appInfo/add
	 * 需要的请求体json格式：{"name":"","description":"","oauthUrl":"","samlUrl":"","samlLogoutUrl":"","stsEndpoint":"","stsCertAlias":"","appDepartment":""}
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map<String,Object> add(@RequestBody AppInfoVO appInfoVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		//应用管理部门
		Department manageDept = null;
		AppSAML saml = null;
		//从request中获取执行当操作的用户
		Person person = (Person) request.getAttribute("restUser");
		//判断用户输入的部门(即创建的应用的管理部门)是不是该用户所属的部门
		for(Department dept : person.getDeps()) {
			if(dept.getName().equals(appInfoVO.getAppDepartment())) {
				manageDept = dept;
				break;
			}
		}
		
		//配置三种协议的属性
		//saml协议
		if(!(StringUtil.isNoE(appInfoVO.getSamlUrl()) && StringUtil.isNoE(appInfoVO.getSamlLogoutUrl()))) {
			if(StringUtil.isNoE(appInfoVO.getSamlUrl())) {
				map.put("code", "5005");
				map.put("result", "failure");
				map.put("reason", "请输入SamlURL");
				return map;
			} else if(StringUtil.isNoE(appInfoVO.getSamlLogoutUrl())) {
				map.put("code", "5006");
				map.put("result", "failure");
				map.put("reason", "请输入SamlLogoutURL");
				return map;
			} else {
				saml = new AppSAML(appInfoVO.getName(),"",appInfoVO.getSamlUrl(), appInfoVO.getSamlLogoutUrl());
			}
		}
		//oauth协议
		AppOAuth oAuth = new AppOAuth(appInfoVO.getName(),"http://",null,null);
		if (!StringUtil.isNoE(appInfoVO.getOauthUrl())) {
			oAuth.setUrl(appInfoVO.getOauthUrl());
		}
		//STS协议
		AppSTS sts = new AppSTS(appInfoVO.getName(), "endpoint", "");
		if(!StringUtil.isNoE(appInfoVO.getStsEndpoint())) {
			sts.setEndpoint(appInfoVO.getStsEndpoint());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsCertAlias())) {
			try {
				//注：这里每次new对象是不好的
				String[] certAlias = new RemoteAppMgt().getCertAlias();
				String stsCertAlias = appInfoVO.getStsCertAlias();
				boolean aliasCorrect = false;
				for (String str : certAlias) {
					if(stsCertAlias.equals(str)) {
						aliasCorrect = true;
						break;
					}
				}
				if(!aliasCorrect) {
					map.put("code", "5009");
					map.put("result", "failure");
					map.put("reason", "stsCertAlias输入有误");
					return map;
				}
			} catch (AppUpdateException e) {
				map.put("code", "5008");
				map.put("result", "failure");
				map.put("reason", "连接认证服务器失败");
				return map;
			}
			sts.setCertAlias(appInfoVO.getStsCertAlias());
		}
		
		//如果manageDept为空,说明用户输入的部门不是用户所属的部门,不允许创建应用
		if(manageDept == null) {
			map.put("code", "5004");
			map.put("result", "failure");
			map.put("reason", "您无权在该部门下创建应用");
			return map;
		}
		
		AppInfo appInfo = new AppInfo();
		appInfo.setName(appInfoVO.getName());
		appInfo.setDescription(appInfoVO.getDescription());
		appInfo.setCreator(person);
		appInfo.setManageDep(manageDept);
		appInfo.setSaml(saml);
		appInfo.setOauth(oAuth);
		appInfo.setSts(sts);
		try {
			appMgtService.addApplication(appInfo);
		} catch (AppUpdateException e) {
			map.put("code", "5007");
			map.put("result", "failure");
			map.put("reason", "应用名称已存在");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}
	
	
	/**
	 * @author upshi
	 * @date 20160224
	 * @url /rest/appInfo/update
	 * 需要的请求体json格式：{"appId":"","name":"","description":"","oauthUrl":"","samlUrl":"","samlLogoutUrl":"","stsEndpoint":"","stsCertAlias":""}
	 */
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public Map<String,Object> update(@RequestBody AppInfoVO appInfoVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		//根据appID先获取app的原有信息
		AppInfo oldAppInfo = appInfoService.getAppById(appInfoVO.getAppId());
		//判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, appInfoVO.getAppId());
		if(preHandleMap != null) {
			return preHandleMap;
		}
		
		//修改信息
		if(!StringUtil.isNoE(appInfoVO.getName())) {
			oldAppInfo.setName(appInfoVO.getName());
		}
		if(!StringUtil.isNoE(appInfoVO.getDescription())) {
			oldAppInfo.setDescription(appInfoVO.getDescription());
		}
		if(!StringUtil.isNoE(appInfoVO.getOauthUrl())) {
			oldAppInfo.getOauth().setUrl(appInfoVO.getOauthUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getSamlUrl())) {
			if(oldAppInfo.getSaml() == null) {
				oldAppInfo.setSaml(new AppSAML());
			}
			oldAppInfo.getSaml().setUrl(appInfoVO.getSamlUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getSamlLogoutUrl())) {
			if(oldAppInfo.getSaml() == null) {
				oldAppInfo.setSaml(new AppSAML());
			}
			oldAppInfo.getSaml().setLogoutUrl(appInfoVO.getSamlLogoutUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsEndpoint())) {
			oldAppInfo.getSts().setEndpoint(appInfoVO.getStsEndpoint());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsCertAlias())) {
			oldAppInfo.getSts().setCertAlias(appInfoVO.getStsCertAlias());
		}
		
		try {
			appMgtService.updateApplication(oldAppInfo);
		} catch (Exception e) {
			map.put("code", "5007"); 
			map.put("result", "failure");
			map.put("reason", "应用名称已存在");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160224
	 * @url /rest/appInfo/delete/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppInfo appInfo = appInfoService.getAppById(id);
		//判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, id);
		if(preHandleMap != null) {
			return preHandleMap;
		}
		
		try {
			appMgtService.removeApplication(appInfo);
			map.put("code", "8000");
			map.put("result", "success");
		} catch (AppUpdateException e) {
			map.put("code", "5012");
			map.put("result", "failure");
			map.put("reason", "删除失败,未知错误，请重试");
		}
		return map;
	}
	
	
	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appInfo/searchByName/{name}
	 */
	@ResponseBody
	@RequestMapping(value="/searchByName/{name}" ,method = RequestMethod.GET)
	public Map<String, Object> searchByName(@PathVariable String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppInfo app = null;
		try {
			app = appInfoService.getAppByName(new String(name.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误！");
		}
		
		if (app != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("app", app);
		} else {
			map.put("code", "5023");
			map.put("result", "failure");
			map.put("reason", "您查询的应用不存在");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appInfo/search/departmentId/{departmentId}
	 */
	@ResponseBody
	@RequestMapping(value="/search/departmentId/{departmentId}" ,method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable Integer departmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppInfo> apps = null;
		apps = appInfoService.getAppsByDepartmentId(departmentId);
		
		if (apps != null && apps.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("apps", apps);
		} else {
			map.put("code", "5000");
			map.put("result", "failure");
			map.put("reason", "您查询的部门没有应用");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appInfo/search/creator/{creator}
	 */
	@ResponseBody
	@RequestMapping(value="/search/creator/{creator}" ,method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable("creator") String creator) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppInfo> apps = null;
		try {
			apps = appInfoService.getAppsByCreator(new String(creator.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误！");
		}
		System.out.println(creator);
		System.out.println(apps);
		
		if (apps != null && apps.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("apps", apps);
		} else {
			map.put("code", "5001");
			map.put("result", "failure");
			map.put("reason", "您查询的创建者没有应用");
		}
		return map;
	}
	
	/*
	 * 判断是否有权限执行修改删除等操作
	 * */
	private Map<String, Object> preHandle(HttpServletRequest request, Integer appId) {
		Map<String, Object> map = null;
		//从request中获取执行当操作的用户
		Person person = (Person) request.getAttribute("restUser");
		AppInfo appInfo = appInfoService.getAppById(appId);
		
		if(appInfo == null) {
			map = new HashMap<String, Object>();
			map.put("code", "5010");
			map.put("result", "failure");
			map.put("reason", "您输入的应用ID不存在");
			return map;
		}
		
		//判断人员是否有权删除该应用，应用的拥有者以及应用的管理部门的管理员有权删除
		if(person.getId() != appInfo.getCreator().getId() && !positionService.isAdmin(person.getUsername(), appInfo.getManageDep().getFullname())) {
			map = new HashMap<String, Object>();
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}
		
		return map;
	}
	
	
	/**
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用***********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 */
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版增加appinfo
	 * @url /rest/appInfo/add
	 * 需要的请求体json格式：{"name":"","description":"","oauthUrl":"","samlUrl":"","samlLogoutUrl":"","stsEndpoint":"","stsCertAlias":"","appDepartment":""}
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Map<String,Object> oldAdd(@RequestBody AppInfoVO appInfoVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		//应用管理部门
		Department manageDept = null;
		AppSAML saml = null;
		//从request中获取执行当操作的用户
		Person person = (Person) request.getAttribute("restUser");
		//判断用户输入的部门(即创建的应用的管理部门)是不是该用户所属的部门
		for(Department dept : person.getDeps()) {
			if(dept.getName().equals(appInfoVO.getAppDepartment())) {
				manageDept = dept;
				break;
			}
		}
		
		//配置三种协议的属性
		//saml协议
		if(!(StringUtil.isNoE(appInfoVO.getSamlUrl()) && StringUtil.isNoE(appInfoVO.getSamlLogoutUrl()))) {
			if(StringUtil.isNoE(appInfoVO.getSamlUrl())) {
				map.put("code", "5005");
				map.put("result", "failure");
				map.put("reason", "请输入SamlURL");
				return map;
			} else if(StringUtil.isNoE(appInfoVO.getSamlLogoutUrl())) {
				map.put("code", "5006");
				map.put("result", "failure");
				map.put("reason", "请输入SamlLogoutURL");
				return map;
			} else {
				saml = new AppSAML(appInfoVO.getName(),"",appInfoVO.getSamlUrl(), appInfoVO.getSamlLogoutUrl());
			}
		}
		//oauth协议
		AppOAuth oAuth = new AppOAuth(appInfoVO.getName(),"http://",null,null);
		if (!StringUtil.isNoE(appInfoVO.getOauthUrl())) {
			oAuth.setUrl(appInfoVO.getOauthUrl());
		}
		//STS协议
		AppSTS sts = new AppSTS(appInfoVO.getName(), "endpoint", "");
		if(!StringUtil.isNoE(appInfoVO.getStsEndpoint())) {
			sts.setEndpoint(appInfoVO.getStsEndpoint());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsCertAlias())) {
			try {
				//注：这里每次new对象是不好的
				String[] certAlias = new RemoteAppMgt().getCertAlias();
				String stsCertAlias = appInfoVO.getStsCertAlias();
				boolean aliasCorrect = false;
				for (String str : certAlias) {
					if(stsCertAlias.equals(str)) {
						aliasCorrect = true;
						break;
					}
				}
				if(!aliasCorrect) {
					map.put("code", "5009");
					map.put("result", "failure");
					map.put("reason", "stsCertAlias输入有误");
					return map;
				}
			} catch (AppUpdateException e) {
				map.put("code", "5008");
				map.put("result", "failure");
				map.put("reason", "连接认证服务器失败");
				return map;
			}
			sts.setCertAlias(appInfoVO.getStsCertAlias());
		}
		
		//如果manageDept为空,说明用户输入的部门不是用户所属的部门,不允许创建应用
		if(manageDept == null) {
			map.put("code", "5004");
			map.put("result", "failure");
			map.put("reason", "您无权在该部门下创建应用");
			return map;
		}
		
		AppInfo appInfo = new AppInfo();
		appInfo.setName(appInfoVO.getName());
		appInfo.setDescription(appInfoVO.getDescription());
		appInfo.setCreator(person);
		appInfo.setManageDep(manageDept);
		appInfo.setSaml(saml);
		appInfo.setOauth(oAuth);
		appInfo.setSts(sts);
		try {
			appMgtService.addApplication(appInfo);
		} catch (AppUpdateException e) {
			map.put("code", "5007");
			map.put("result", "failure");
			map.put("reason", "应用名称已存在");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}
	
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版更新
	 * @url /rest/appInfo/update
	 * 需要的请求体json格式：{"appId":"","name":"","description":"","oauthUrl":"","samlUrl":"","samlLogoutUrl":"","stsEndpoint":"","stsCertAlias":""}
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.PUT)
	public Map<String,Object> oldUpdate(@RequestBody AppInfoVO appInfoVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		//根据appID先获取app的原有信息
		AppInfo oldAppInfo = appInfoService.getAppById(appInfoVO.getAppId());
		//判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, appInfoVO.getAppId());
		if(preHandleMap != null) {
			return preHandleMap;
		}
		
		//修改信息
		if(!StringUtil.isNoE(appInfoVO.getName())) {
			oldAppInfo.setName(appInfoVO.getName());
		}
		if(!StringUtil.isNoE(appInfoVO.getDescription())) {
			oldAppInfo.setDescription(appInfoVO.getDescription());
		}
		if(!StringUtil.isNoE(appInfoVO.getOauthUrl())) {
			oldAppInfo.getOauth().setUrl(appInfoVO.getOauthUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getSamlUrl())) {
			if(oldAppInfo.getSaml() == null) {
				oldAppInfo.setSaml(new AppSAML());
			}
			oldAppInfo.getSaml().setUrl(appInfoVO.getSamlUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getSamlLogoutUrl())) {
			if(oldAppInfo.getSaml() == null) {
				oldAppInfo.setSaml(new AppSAML());
			}
			oldAppInfo.getSaml().setLogoutUrl(appInfoVO.getSamlLogoutUrl());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsEndpoint())) {
			oldAppInfo.getSts().setEndpoint(appInfoVO.getStsEndpoint());
		}
		if(!StringUtil.isNoE(appInfoVO.getStsCertAlias())) {
			oldAppInfo.getSts().setCertAlias(appInfoVO.getStsCertAlias());
		}
		
		try {
			appMgtService.updateApplication(oldAppInfo);
		} catch (Exception e) {
			map.put("code", "5007"); 
			map.put("result", "failure");
			map.put("reason", "应用名称已存在");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版删除
	 * @url /rest/appInfo/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> oldDelete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppInfo appInfo = appInfoService.getAppById(id);
		//判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, id);
		if(preHandleMap != null) {
			return preHandleMap;
		}
		
		try {
			appMgtService.removeApplication(appInfo);
			map.put("code", "8000");
			map.put("result", "success");
		} catch (AppUpdateException e) {
			map.put("code", "5012");
			map.put("result", "failure");
			map.put("reason", "删除失败,未知错误，请重试");
		}
		return map;
	}
	
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版查找name
	 * @url /rest/appInfo/{name}
	 */
	@ResponseBody
	@RequestMapping(value="/{name}" ,method = RequestMethod.GET)
	public Map<String, Object> oldSearchByName(@PathVariable String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppInfo app = null;
		try {
			app = appInfoService.getAppByName(new String(name.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误！");
		}
		
		if (app != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("app", app);
		} else {
			map.put("code", "5023");
			map.put("result", "failure");
			map.put("reason", "您查询的应用不存在");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版查找departmentid
	 * @url /rest/appInfo/departmentId/{departmentId}
	 */
	@ResponseBody
	@RequestMapping(value="/departmentId/{departmentId}" ,method = RequestMethod.GET)
	public Map<String, Object> oldSearch(@PathVariable Integer departmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppInfo> apps = null;
		apps = appInfoService.getAppsByDepartmentId(departmentId);
		
		if (apps != null && apps.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("apps", apps);
		} else {
			map.put("code", "5000");
			map.put("result", "failure");
			map.put("reason", "您查询的部门没有应用");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版查找creator
	 * @url /rest/appInfo/creator/{creator}
	 */
	@ResponseBody
	@RequestMapping(value="/creator/{creator}" ,method = RequestMethod.GET)
	public Map<String, Object> oldSearch(@PathVariable("creator") String creator) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppInfo> apps = null;
		try {
			apps = appInfoService.getAppsByCreator(new String(creator.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误！");
		}
		System.out.println(creator);
		System.out.println(apps);
		
		if (apps != null && apps.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("apps", apps);
		} else {
			map.put("code", "5001");
			map.put("result", "failure");
			map.put("reason", "您查询的创建者没有应用");
		}
		return map;
	}
	
}
