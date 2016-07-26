package com.nb.org.testapp;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppOAuth;
import com.nb.org.domain.AppSAML;
import com.nb.org.domain.AppSTS;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppMgtService;
import com.nb.org.service.IAppOAuthService;
import com.nb.org.service.IAppSAMLService;
import com.nb.org.service.IAppSTSService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestAppInfo {
	private static Logger logger = Logger.getLogger(TestAppInfo.class);
	
	@Autowired
	private IAppMgtService mgtService;
	@Autowired
	private IAppInfoService service;
	@Autowired
	private IAppOAuthService oauthService;
	@Autowired
	private IAppSAMLService samlService;
	@Autowired
	private IAppSTSService stsService;
	/**
	 * 
	 */
	@Test
	public void testInsertApp() {

		Person person = new Person();
		person.setId(10);
		Department department = new Department();
		department.setId(2);
		String appName = "第101次测试";
		try {
			AppOAuth oauth = new AppOAuth(appName,"test123", "abc123", "78910");
			AppSAML saml = new AppSAML(appName,"abc123456", "test", "test");
			AppSTS sts = new AppSTS(appName,"aaa","ccc");
			int id = oauthService.insertOAuth(oauth);
			oauth.setId(id);
			int sid = samlService.insertSAML(saml);
			saml.setId(sid);
			int ssid = stsService.insertSTS(sts);
			AppInfo app = new AppInfo(appName, "haha", person, department, oauth,
					saml,sts);
			service.insertApp(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestDeleteApp() {
		try{
		AppInfo app = service.getAppByName("abc");
		System.out.println(app);
		}catch (Exception e){
			System.out.println("eeeeeeccccc");
		}
//		AppOAuth oauth = app.getOauth();
//		AppSAML saml = app.getSaml();
//		System.out.println(oauth);
//
//		oauthService.deleteOAuth(oauth.getId());
//		samlService.deleteSAML(saml.getId());
//
//		service.deleteApp(app.getId());
	}

	@Test
	public void TestUpdate() {

		AppInfo app = service.getAppByName("abc");
		AppOAuth oauth = app.getOauth();
		AppSAML saml = app.getSaml();
		saml.setUrl("修改一下");
		oauth.setUrl("改改改");
		samlService.updateSAML(saml);
		oauthService.updateOAuth(oauth);
		app.setDescription("测试1123");
		try {
			service.updateApp(app);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetApps() {
		List<AppInfo> list = service.getApps();
		for (AppInfo app : list) {
			System.out.println(app);
		}
	}

	@Test
	public void testGet() {
		AppInfo app = service.getAppByName("www");
		if (app != null)
			System.out.println(app);
		else
			System.out.println("null");
	}

	@Test
	public void testTranInsert() {
		String appName = "goodjob";
		AppOAuth oauth = new AppOAuth(appName,"test123", "abc123", "78910");
		AppSAML saml = new AppSAML(appName,"goodjob", "test", "test");
		AppInfo app = service.getAppByName("trriee222");
//		app.setName("trriee222");
//		app.setOauth(null);
//		app.setSaml(null);
//		int i = service.insertApp(app);
//		System.out.println(i);
		System.out.println(app);
		
		
		
		app = service.getAppByName("trriee22");
		System.out.println(app);
	}
	
	@Test
	public void testRegister(){
		Person person = new Person();
		person.setId(10);
		Department department = new Department();
		department.setId(2);
		String appName = "aaaaeeeeebbbbb";
		AppOAuth oauth = new AppOAuth(appName,"abc", "http://", "78910");
		AppSAML saml = new AppSAML(appName,"goodjob1", "", "");
		AppSTS sts = new AppSTS(appName,"http://aaa","wso2carbon.cert");
		AppInfo app = new AppInfo(appName, "haha", person, department, oauth,
				saml,sts);
		try {
			mgtService.addApplication(app);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdate(){
		AppInfo app = service.getAppByName("aaaaeeeeebbbbb");
		app.getSaml().setLogoutUrl("I am the best, by huangxin");
		app.getOauth().setUrl("edit by huangxin");
		app.getSts().setCertAlias("localhost");
		try {
			mgtService.updateApplication(app);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDelete(){
		AppInfo app = service.getAppByName("goodjob");
		try {
			mgtService.removeApplication(app);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
