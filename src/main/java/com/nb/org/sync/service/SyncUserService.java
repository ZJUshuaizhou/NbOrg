package com.nb.org.sync.service;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axiom.om.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.SyncOrg;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.service.ISyncOrgService;
import com.nb.org.sync.config.SyncConfig;
import com.nb.org.sync.service.SyncOrgError.OrgError;
import com.nb.org.sync.service.SyncUserError.UserError;
import com.nb.org.sync.util.Sign;
import com.nb.org.sync.util.parse.DataParseFactory;
import com.nb.org.sync.util.parse.IDataParse;
import com.nb.org.util.SNGenerator;
import com.nb.org.util.StringUtil;

import sun.misc.BASE64Decoder;

/**
 * 服务业务处理类 功能：处理用户数据 日期：2013-09-30 说明： 以下代码只是为了方便第三方发布同步用户服务而提供的样例代码。
 * 该代码仅提供的资源认证以及基本的业务流程，只是提供一个参考。
 */
@Controller
public class SyncUserService {
	private static Logger logger = Logger.getLogger(SyncUserService.class);
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentService iDepartmentService;
	@Autowired
	private SNGenerator snGenerate;
	@Resource
	private ISyncOrgService iSyncOrgService;

	@ResponseBody
	@RequestMapping("/synchronizeUser")
	public String synchronizeUser(String servicecode, String time, String sign, String args, String datatype,
			String describe,String method,String loginname, String orgcoding) {
		if(method!=null&&method.equals("deleteUser")){
			return deleteUser(servicecode,time, sign, args, datatype,
					describe, method, loginname, orgcoding);
		}else{
			return syncUser(servicecode,time, sign, args, datatype,
					describe, method, loginname, orgcoding);
		}
	}
	// 新增或更新用户
	public String syncUser(String servicecode, String time, String sign, String args, String datatype,
			String describe,String method,String loginname, String orgcoding){
		String result = "";
		IDataParse dp = DataParseFactory.factory(datatype);
		boolean vali = Sign.sign(servicecode, time, sign);
		if (!vali) { 
			return SyncUserError.getErrResult(dp, UserError.appForbidSync);
		}
		try {
			Map<String, String> userMap = dp.parseSimple(args);// 用户数据集合
			String username = userMap.get(SyncConfig.LOGINNAME);
			if (username.isEmpty()) {
				logger.info("username为空");
				return SyncUserError.getErrResult(dp, UserError.usernameIllegal);
			}
			orgcoding = userMap.get("orgcoding");
			if (orgcoding.isEmpty()) {
				logger.info("orgcoding为空");
				return SyncUserError.getErrResult(dp, UserError.argsIllegal);
			}
			
			Department department = null;
			// 获取orgcoding对应的syncorg对象
			SyncOrg syncOrg = iSyncOrgService.selectByOrgCoding(orgcoding);
			if(syncOrg==null){
				logger.info("syncorg表中没有orgcoding对应的组织");
				return SyncUserError.getErrResult(dp, UserError.usernameIllegal);
			}
			String oldorgcoding = "";
			oldorgcoding = userMap.get("oldorgcoding");
			
			// 根据获取到的orgcoding查询对应的部门
			department = iDepartmentService.getDepartmentById(syncOrg.getDepId());
			String username_query = username+"@"+syncOrg.getDevcoding();
			
			if(!StringUtil.isNoE(oldorgcoding)){
				SyncOrg syncOrg_old = iSyncOrgService.selectByOrgCoding(oldorgcoding);
				String username_old = username+"@"+syncOrg_old.getDevcoding();
				int res =0;
				Person person_old = personService.getPersonByUserName(username_old);
				res = personService.delPerson(person_old.getId());
				if(res!=1){
					logger.info("因需要更改用户组织，进行用户删除，失败！");
					return SyncUserError.getErrResult(dp, UserError.other);
				}
				person_old.setUsername(username_query);
				res = personService.insertPerson(person_old);
				if(res!=1){
					logger.info("因需要更改用户组织，进行用户创建，失败！");
					return SyncUserError.getErrResult(dp, UserError.other);
				}
			}
			Person person_query = personService.getPersonByUserName(username_query);
			// 新增用户
			if (person_query == null) {
				String name = userMap.get("username");
				if (name.isEmpty()) {
					logger.info("name为空");
					return SyncUserError.getErrResult(dp, UserError.usernameIllegal);
				}
				String sex = userMap.get("sex");
				String encryptiontype = userMap.get("encryptiontype");
				String loginpwd = userMap.get("loginpwd");
				BASE64Decoder base64Decoder = new BASE64Decoder();
				String password="";
				try {
					byte[] b = base64Decoder.decodeBuffer(loginpwd);
					password = new String(b);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(password != "" && password.isEmpty()){
					logger.info("password为空");
					return SyncUserError.getErrResult(dp, UserError.other);
				}
				int gender = 0;
				if (sex.equals("1"))
					gender = 0;
				if (sex.equals("2"))
					gender = 1;
				String telephone = "";
				telephone = userMap.get("telephone");
				String mobilePhone = "";
				mobilePhone = userMap.get("mobile");
				String email = "";
				email = userMap.get("email");

				Person person = new Person();
				person.setName(name);
				person.setUsername(username+"@"+syncOrg.getDevcoding());
				person.setGender(gender);
				person.setTelephone(telephone);
				person.setMobilephone(mobilePhone);
				person.setEmail(email);
				// 设置按规则生成的SN
				person.setSn(snGenerate.generatePersonSN(department));
				person.setCreateDep(department);
				person.getDeps().add(department);
				try {
					int res = 0;
					logger.info("待插入的用户信息为："+person.toString());
					res = personService.insertPersonBySync(person, department, "普通用户", 0, password);
					if(res==1){
						logger.info("插入用户成功");
						result = SyncUserError.getErrResult(dp, UserError.success);						
					}else {
						logger.info("插入用户失败");
						result = SyncUserError.getErrResult(dp, UserError.other);
					}
				} catch (PersonException e) {
					result = SyncUserError.getErrResult(dp, UserError.other);
					e.printStackTrace();
				}
			}
			// 更新用户
			else {
				// 获取用户当前的所在部门
				Department department_query = person_query.getCreateDep();
				// 判断用户当前所在的部门是否为杭州同步过来的组织
				SyncOrg syncOrg_query = iSyncOrgService.selectByDepId(department_query.getId());
				if(syncOrg_query==null){
					logger.info("宁波系统中已存在该用户名，不进行同步");
					return SyncUserError.getErrResult(dp, UserError.appForbidSync);
				}
//				if (department_query.getId()!=department.getId()) {
//					logger.info("宁波系统中已存在该用户名，不进行同步");
//					return SyncUserError.getErrResult(dp, UserError.appForbidSync);
//				} 
				else {
					String name = "";
					name = userMap.get("username");
					String sex = "";
					sex = userMap.get("sex");
					int gender = 0;
					if (sex.equals("1"))
						gender = 0;
					if (sex.equals("2"))
						gender = 1;
					String encryptiontype = "";
					String password = "";
					encryptiontype = userMap.get("encryptiontype");
					if (encryptiontype != null&&encryptiontype.equals("2")) {
						String loginpwd = userMap.get("loginpwd");
						Base64 base64 = new Base64();
						password = new String(base64.decode(loginpwd));
					}
					String telephone = "";
					telephone = userMap.get("telephone");
					String mobilePhone = "";
					mobilePhone = userMap.get("mobile");
					String email = "";
					email = userMap.get("email");
					
					Person person = person_query;
					if (name != null && (!name.trim().equals(""))) {
						person.setName(name);
					}
					if (sex != null && (!sex.trim().equals(""))) {
						person.setGender(gender);
					}
					if (telephone != null && (!telephone.trim().equals(""))) {
						person.setTelephone(telephone);
					}
					if (mobilePhone != null && (!mobilePhone.trim().equals(""))) {
						person.setMobilephone(mobilePhone);
					}
					if (email != null && (!email.trim().equals(""))){
						person.setEmail(email);
					}
					try {
						int res = 0;
						logger.info("待更新的用户信息为："+person.toString());
						res = personService.updatePersonInfo(person);
						// 更改用户所在组织
						if(oldorgcoding!=null&&(!oldorgcoding.trim().equals("")&&department!=null)){
							res = personService.delRelativity(person, department_query);
							res = personService.saveRelativity(person, department, "普通用户", 0);
							person.setCreateDep(department);
							res = personService.updatePersonDepId(person);
						}
						if(res ==1){
							logger.info("更新用户成功");
							result = SyncUserError.getErrResult(dp, UserError.success);							
						}else{
							logger.info("更新用户失败");
							result = SyncUserError.getErrResult(dp, UserError.other);
						}
					} catch (PersonException e) {
						result = SyncUserError.getErrResult(dp, UserError.other);
						e.printStackTrace();
					}
					if (password != "" && (!password.isEmpty())) {
						int res = 0;
						res = personService.changePasswordByAdmin(username_query, password);
						if (res == 1) {
							logger.info("更改用户的密码成功");
							result = SyncUserError.getErrResult(dp, UserError.success);
						}else{
							logger.info("更改用户的密码失败");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SyncUserError.getErrResult(dp, UserError.other);
		}
		return result;
	}
	
	//删除用户
	public String deleteUser(String servicecode, String time, String sign, String args, String datatype,
			String describe,String method,String loginname, String orgcoding){
		String result = "";
		IDataParse dp = DataParseFactory.factory(datatype);
		boolean vali = Sign.sign(servicecode, time, sign);
		if (!vali) {
			return SyncUserError.getErrResult(dp, UserError.appForbidSync);
		}
		String username = loginname;
		if (loginname.isEmpty()) {
			logger.info("loginname为空");
			return SyncUserError.getErrResult(dp, UserError.usernameIllegal);
		}
		if (orgcoding.isEmpty()) {
			logger.info("orgcoding为空");
			return SyncUserError.getErrResult(dp, UserError.other);
		}
		SyncOrg syncOrg = iSyncOrgService.selectByOrgCoding(orgcoding);
		if (syncOrg == null) {
			logger.info("syncOrg表中不存在orgcoding对应的组织");
			return SyncOrgError.getErrResult(dp, OrgError.orgNotExist);
		}
		Department department = null;
		try {
			department = iDepartmentService.getDepartmentById(syncOrg.getDepId());
		} catch (DepartmentException e2) {
			e2.printStackTrace();
		}
		Person person_query = null;
		try {
			person_query = personService.getPersonByUserName(username+"@"+syncOrg.getDevcoding());
		} catch (PersonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (person_query == null) {
			logger.info("系统不存在该loginname对应的用户");
			return SyncUserError.getErrResult(dp, UserError.userNotExist);
		}
		Department department_query = person_query.getCreateDep();
		if (department_query.getId()!=department.getId()) {
			logger.info("该loginname对应的用户为宁波系统中的，不能进行删除");
			return SyncUserError.getErrResult(dp, UserError.appForbidSync);
		}
		int res = 0;
		try {
			res = personService.delRelativity(person_query, department_query);
			if(res==1){
				logger.info("删除用户关系成功");
				if (res == 1) {
					res = personService.delPerson(person_query.getId());
					logger.info("删除用户成功");
					result = SyncUserError.getErrResult(dp, UserError.success);
				}else{
					logger.info("删除用户失败");
					result = SyncUserError.getErrResult(dp, UserError.other);
				}
			}else{
				logger.info("删除用户关系失败");
				result = SyncUserError.getErrResult(dp, UserError.other);
			}
		} catch (PersonException e) {
			result = SyncUserError.getErrResult(dp, UserError.other);
			e.printStackTrace();
		}
		return result;
	}

}
