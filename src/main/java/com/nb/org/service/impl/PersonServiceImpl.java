package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.nb.org.dao.IPersonDao;
import com.nb.org.dao.IPositionDao;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IPersonService;
import com.nb.org.vo.PersonVO;

import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;
import org.wso2.carbon.um.ws.api.WSRealmBuilder;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.utils.WSO2Constants;

import com.nb.org.util.GlobalConfig;

@Service("personService")
public class PersonServiceImpl implements IPersonService {
	private static Logger logger = Logger.getLogger(PersonServiceImpl.class);
	@Resource
	private IPersonDao personDao;
	@Resource
	private IPositionDao positionDao;

	@Override
	@Transactional
	public int insertPerson(Person per) throws PersonException {
		int wso2 = 0;
		int res = 0;
		try {
			wso2 = insertPersonToWSO2(per.getUsername());
			logger.info("insert person to wso2-----");

			res = personDao.insertPerson(per);
			logger.info("insertPerson-----" + JSON.toJSONString(per));
			if (wso2 == 1 && res == 0) {
				deletePersonOfWSO2(per.getUsername());
			}
			res = wso2 * res;

		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("添加用户失败！");
			throw personException;
		}
		return res;
	}

	@Override
	public Person getPersonById(int id) throws PersonException {
		Person per = new Person();
		try {
			per = personDao.selectPerson(id);
			logger.info("getPerson-----" + JSON.toJSONString(per));
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("获取用户失败");
			throw personException;
		}
		return per;
	}

	@Override
	public int updatePersonInfo(Person per) throws PersonException {
		int res = 0;
		try {
			res = personDao.updatePerson(per);
			logger.info("updatePerson-----" + JSON.toJSONString(per));
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("更新用户失败");
			throw personException;
		}
		return res;
	}

	@Override
	@Transactional
	public int delPerson(int id) throws PersonException {
		Person per = personDao.selectPerson(id);
		int wso2 = 0;
		int res = 0;
		try {
			wso2 = deletePersonOfWSO2(per.getUsername());
			logger.info("delPerson From wso2-----" + String.valueOf(wso2) + JSON.toJSONString(per));
			if (wso2 == 1) {
				res = personDao.delPerson(id);
				logger.info("delPerson-----" + JSON.toJSONString(per));
			}
			if (wso2 == 1 && res == 0) {
				insertPersonToWSO2(per.getUsername());
			}
			res = res * wso2;
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = null;
			if (wso2 == 1) {
				personException = new PersonException("删除人员失败！密码已重置！");
			} else {
				personException = new PersonException("删除人员失败！");
			}
			throw personException;
		}

		return res;
	}

	@Override
	public List<Person> selectPersonsByName(String name) throws PersonException {
		List<Person> list = new ArrayList<Person>();
		try {
			list = personDao.selectPersonsByName(name);
			logger.info("selectPersonsByName-----" + JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("获取用户失败");
			throw personException;
		}
		return list;
	}

	@Override
	public int savePerson(Person p, Department dep, String posName, int adminFlag) throws PersonException {
		int res = 0;
		try {
			Position pos = new Position();
			pos.setDep(dep);
			pos.setPerson(p);
			pos.setName(posName);
			pos.setAdminFlag(adminFlag);
			res = insertPerson(p);
			logger.info("插入人员基本信息----" + JSON.toJSONString(p));
			res = personDao.saveRelativity(pos);
			logger.info("关联人员部门----" + JSON.toJSONString(pos));
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("插入用户失败");
			throw personException;
		}
		return res;
	}

	@Override
	public int updateRelativity(Person p, Department dep, String posName, int adminFlag) {
		Position pos = new Position();
		pos.setDep(dep);
		pos.setPerson(p);
		pos.setName(posName);
		pos.setAdminFlag(adminFlag);
		int res = personDao.updateRelativity(pos);
		logger.info("更新了部门与人员的关系----" + JSON.toJSONString(pos));
		return res;
	}

	@Override
	public int saveRelativity(Person p, Department dep, String posName, int adminFlag) {
		Position pos = new Position();
		pos.setDep(dep);
		pos.setPerson(p);
		pos.setName(posName);
		pos.setAdminFlag(adminFlag);
		int res = personDao.saveRelativity(pos);
		logger.info("增加了了部门与人员的关系----" + JSON.toJSONString(pos));
		return res;
	}

	@Override
	public int delRelativity(Person p, Department dep) {
		Position pos = new Position();
		pos.setDep(dep);
		pos.setPerson(p);
		int res = 0 ; 
		res = personDao.deleteRelativity(pos);
		if(res==0){
			logger.info("解除了了部门与人员的关系----失败" + JSON.toJSONString(pos));			
		}else{			
			logger.info("解除了了部门与人员的关系----" + JSON.toJSONString(pos));
		}
		return res;
	}

	@Override
	public int changePosition(Person p, Department dep, String posName) {
		Position pos = positionDao.selectPositionByPerDep(p.getId(), dep.getId());
		pos.setName(posName);
		int res = positionDao.updatePosition(pos);
		logger.info("改变了人员的职位----" + JSON.toJSONString(pos));
		return res;
	}

	@Override
	public int changeAdminFlag(Person p, Department dep, int adminFlag) {
		Position pos = positionDao.selectPositionByPerDep(p.getId(), dep.getId());
		pos.setAdminFlag(adminFlag);
		int res = positionDao.updatePosition(pos);
		logger.info("改变了人员的管理权限----" + JSON.toJSONString(pos));
		return res;
	}

	@Override
	public Position selectPositionByPerDep(int perId, int depId) {
		return positionDao.selectPositionByPerDep(perId, depId);
	}

	/*
	 * @author upshi
	 * 
	 * @date 20160119 根据姓名返回用户信息
	 */
	@Override
	public Person getPersonByUserName(String userName) throws PersonException {
		if (userName == null || "".equals(userName)) {
			throw new PersonException("请输入用户名");
		} else {
			return personDao.selectPersonByUserName(userName);
		}
	}

	@Override
	public PersonVO listPersonPage(PersonVO vo) {
		List<Person> persons = personDao.getPagePer(vo);
		int total = personDao.getTotalPerPages(vo);
		vo.setTotal(total);
		vo.setRows(persons);
		return vo;
	}

	@Override
	public int changePassword(String username, String newPaasword, String oldPassword) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中存在该用户
		if (storeManager.isExistingUser(username)) {
			storeManager.updateCredential(username, newPaasword, oldPassword);
			logger.info("用户密码更改成功---");
			return 1;
		} else {
			logger.info("用户不存在，密码更改失败---");
			return 0;
		}
	}

	@Override
	public int resetPaasword(String username) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中存在该用户
		if (storeManager.isExistingUser(username)) {
			storeManager.updateCredentialByAdmin(username, "123456");
			logger.info("用户密码重置成功---");
			return 1;
		} else {
			logger.info("用户不存在，密码重置失败---");
			return 0;
		}
	}

	/*
	 * 验证用户名密码是否正确 by upshi 20160221
	 */
	public boolean authenticate(String userName, String password) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		boolean authenticate = storeManager.authenticate(userName, password);
		return authenticate;
	}

	// 根据用户名向WSO2中插入新用户(默认密码为123456)
	public int insertPersonToWSO2(String username) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中不存在该用户
		if (!storeManager.isExistingUser(username)) {
			storeManager.addUser(username, "123456", new String[] {,}, null, null);
			System.out.println("The user added successfully to the system");
			return 1;
		} else {
			System.out.println("The user trying to add - alraedy there in the system");
			return 0;
		}
	}

	// 根据用户名向WSO2中插入新用户,可设置密码
	public int insertPersonToWSO2(String username, String password) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中不存在该用户
		if (!storeManager.isExistingUser(username)) {
			storeManager.addUser(username, password, new String[] {,}, null, null);
			System.out.println("The user added successfully to the system");
			return 1;
		} else {
			System.out.println("The user trying to add - alraedy there in the system");
			return 0;
		}
	}

	// 根据用户名删除WSO2中的用户
	public int deletePersonOfWSO2(String username) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中存在该用户
		if (storeManager.isExistingUser(username)) {
			storeManager.deleteUser(username);
			System.out.println("The user has been deleted successfully to the system");
			return 1;
		} else {
			System.out.println("The user is not exist in the system");
			return 0;
		}
	}

	// 调用WSO2 API的工具方法，返回UserStoreManager对象
	private UserStoreManager commonOfWSO2Connect() throws Exception {
		// String SERVER_URL = "https://115.28.232.4:9443/services/";
		String SERVER_URL = GlobalConfig.server_url;
		String APP_ID = "115.28.232.4"; // 如果连接的是远程服务器，则为服务器ip

		/* 忽略证书 */
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		SSLContext.setDefault(ctx);

		AuthenticationAdminStub authstub = null;
		ConfigurationContext configContext = null;
		String cookie = null;
		System.setProperty("javax.net.ssl.trustStore", "wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

		UserStoreManager storeManager = null;

		try {
			configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
			authstub = new AuthenticationAdminStub(configContext, SERVER_URL + "AuthenticationAdmin");
			// 验证一个用户是否有权限增加用户
			if (authstub.login(GlobalConfig.user, GlobalConfig.password, APP_ID)) {
				cookie = (String) authstub._getServiceClient().getServiceContext()
						.getProperty(HTTPConstants.COOKIE_STRING);
				UserRealm realm = WSRealmBuilder.createWSRealm(SERVER_URL, cookie, configContext);
				// 获得管理用户的对象
				storeManager = realm.getUserStoreManager();
				return storeManager;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storeManager;
	}

	/* 忽略证书工具类 */
	private static class DefaultTrustManager implements X509TrustManager {
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public List<Person> getPersonsByUserNames(List<String> spliceUserNames) {
		return personDao.getPersonsByUserNames(spliceUserNames);
	}

	@Override
	public int insertPersonBySync(Person p, Department dep, String posName, int adminFlag, String password)
			throws PersonException {
		int wso2 = 0;
		int res1 = 0;
		int res2 = 0;
		try {
			wso2 = insertPersonToWSO2(p.getUsername(), password);
			logger.info("insert person to wso2-----");
			if (wso2 == 1) {
				res1 = personDao.insertPerson(p);
				logger.info("insertPerson-----" + JSON.toJSONString(p));
			}
			if (res1 == 1) {
				Position pos = new Position();
				pos.setDep(dep);
				pos.setPerson(p);
				pos.setName(posName);
				pos.setAdminFlag(adminFlag);
				res2 = personDao.saveRelativity(pos);
			}
			if (wso2 == 1 && res1 == 0) {
				deletePersonOfWSO2(p.getUsername());
			}
			res2 = wso2 * res1 * res2;
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("添加用户失败！");
			throw personException;
		}
		return res2;

	}

	@Override
	public int changePasswordByAdmin(String username, String password) throws Exception {
		UserStoreManager storeManager = commonOfWSO2Connect();
		// 如果系统中存在该用户
		if (storeManager.isExistingUser(username)) {
			storeManager.updateCredentialByAdmin(username, password);
			logger.info("用户密码修改成功---");
			return 1;
		} else {
			logger.info("用户不存在，密码修改失败---");
			return 0;
		}
	}

	@Override
	public int updatePersonDepId(Person per) throws PersonException {
		int res = 0;
		try {
			res = personDao.updatePersonDepId(per);
			logger.info("updatePersonDepId-----" + JSON.toJSONString(per));
		} catch (Exception e) {
			e.printStackTrace();
			PersonException personException = new PersonException("更新用户的createDepId失败");
			throw personException;
		}
		return res;
	}

}
