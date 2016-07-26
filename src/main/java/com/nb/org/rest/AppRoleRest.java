package com.nb.org.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppRole;
import com.nb.org.domain.AppRoleDepartment;
import com.nb.org.domain.AppRolePerson;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppRoleDepService;
import com.nb.org.service.IAppRolePerService;
import com.nb.org.service.IAppRoleService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.service.IPositionService;
import com.nb.org.vo.AppRoleVO;

@Controller
@RequestMapping("/rest/appRole")
public class AppRoleRest {

	@Autowired
	private IAppRoleService appRoleService;

	@Autowired
	private IAppInfoService appInfoService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IAppRolePerService appRolePerService;

	@Autowired
	private IAppRoleDepService appRoleDepService;

	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appRole/search/appId/{appId}
	 */
	@ResponseBody
	@RequestMapping(value = "/search/appId/{appId}", method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable Integer appId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppRole> appRoles = null;
		appRoles = appRoleService.getRoleListForApp(appId);
		if (appRoles != null && appRoles.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("appRoles", appRoles);
		} else {
			map.put("code", "5002");
			map.put("result", "failure");
			map.put("reason", "您查询的应用下没有角色");
		}
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appRole/search/appName/{appName}
	 */
	@ResponseBody
	@RequestMapping(value = "/search/appName/{appName}", method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable String appName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppRole> appRoles = null;
		try {
			appRoles = appRoleService.getRolesByAppName(new String(appName.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误！");
		}

		if (appRoles != null && appRoles.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("appRoles", appRoles);
		} else {
			map.put("code", "5002");
			map.put("result", "failure");
			map.put("reason", "您查询的应用下没有角色");
		}
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160219
	 * @url /rest/appRole/searchUserName/{appName}/{userName}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchUserName/{appName}/{userName}",method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable String appName, @PathVariable String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> roles = null;
		try {
			roles = appRoleService.getRolesForPerson(new String(appName.getBytes("ISO-8859-1"),"UTF-8"), new String(userName.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码错误!");
		}

		if (roles != null && roles.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("roles", roles);
		} else {
			map.put("code", "5003");
			map.put("result", "failure");
			map.put("reason", "您查询的人员在应用中没有角色");
		}
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160301
	 * @url /rest/appRole/addRole
	 *      请求参数json格式：{"name":"","appId":"","personUserNames":[],"departmentNames"
	 *      :[]}
	 */
	@ResponseBody
	@RequestMapping(value="/addRole",method = RequestMethod.POST)
	public Map<String, Object> addRole(@RequestBody AppRoleVO appRoleVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//AppInfo appInfo = appInfoService.getAppById(appRoleVO.getAppId());
		// 判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, appRoleVO.getAppId());
		if (preHandleMap != null) {
			return preHandleMap;
		}

		// 判断角色名是否存在
		List<AppRole> roleListForApp = appRoleService.getRoleListForApp(appRoleVO.getAppId());
		for (AppRole appRole : roleListForApp) {
			if (appRole.getName().equals(appRoleVO.getName())) {
				map.put("code", "5013");
				map.put("result", "failure");
				map.put("reason", "角色名已经存在");
				return map;
			}
		}

		// 判断添加的人员有没有不存在的
		List<String> personUserNames = appRoleVO.getPersonUserNames();
		List<Person> personsResult = null;
		if(personUserNames != null) {
			personsResult = personService.getPersonsByUserNames(personUserNames);
			// 根据参数中的用户名查询出的结果集数量与参数的集合数量是否一致判断是否有不存在的用户名
			if (personsResult != null && personsResult.size() != personUserNames.size()) {
				String inexistenceUserNames = "";
				String resultUserNames = "";
				// 组成查到的用户名字符串
				for (Person p : personsResult) {
					resultUserNames = resultUserNames + p.getUsername() + ",";
				}
				// 判断参数中的用户名，找出不在数据库中的用户名
				for (String s : personUserNames) {
					if (resultUserNames.indexOf("," + s + ",") < 0) {
						inexistenceUserNames = inexistenceUserNames + s + " ";
					}
				}
				map.put("code", "5014");
				map.put("result", "failure");
				map.put("reason", inexistenceUserNames + "用户不存在,创建角色失败");
				return map;
			}
		}

		// 判断部门有没有不存在的
		// 判断添加的人员有没有不存在的
		List<String> departmentsNames = appRoleVO.getDepartmentNames();
		List<Department> departmentsResult = null;
		if(departmentsNames != null) {
			departmentsResult = departmentService.getDepartmentsByNames(departmentsNames);
			// 根据参数中的用户名查询出的结果集数量与参数的集合数量是否一致判断是否有不存在的用户名
			if (departmentsResult != null && departmentsResult.size() != departmentsNames.size()) {
				String inexistenceDepartmentNames = "";
				String resultDepartmentNames = "";
				// 组成查到的用户名字符串
				for (Department d : departmentsResult) {
					resultDepartmentNames = resultDepartmentNames + d.getName() + ",";
				}
				// 判断参数中的用户名，找出不在数据库中的用户名
				for (String s : departmentsNames) {
					if (resultDepartmentNames.indexOf("," + s + ",") < 0) {
						inexistenceDepartmentNames = inexistenceDepartmentNames + s + " ";
					}
				}
				map.put("code", "5015");
				map.put("result", "failure");
				map.put("reason", inexistenceDepartmentNames + "部门不存在,创建角色失败");
				return map;
			}
		}
		
		// 都存在后即可进行添加该角色
		// 构造AppRole对象
		AppRole appRole = new AppRole();
		appRole.setAppId(appRoleVO.getAppId());
		appRole.setName(appRoleVO.getName());
		appRole.setPersons(personsResult);
		appRole.setDepartments(departmentsResult);
		// 调用service方法插入角色信息
		appRoleService.addRoleForApp(appRole);

		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160302
	 * @url /rest/appRole/updateRole
	 *      请求参数json格式：{"id":"","name":"","appId":"","addUserNames":[],"removeUserNames":[],"addDepartmentNames":[],"removeDepartmentNames":[]}
	 */
	@ResponseBody
	@RequestMapping(value="/updateRole",method = RequestMethod.PUT)
	public Map<String, Object> updateRole(@RequestBody AppRoleVO appRoleVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//AppInfo appInfo = appInfoService.getAppById(appRoleVO.getAppId());
		// 1.判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, appRoleVO.getAppId());
		if (preHandleMap != null) {
			return preHandleMap;
		}

		// 根据id获取该条AppRole记录,判断角色名字是否需要修改
		Integer appRoleId = appRoleVO.getId();
		if (appRoleId == null) {
			map.put("code", "5021");
			map.put("result", "failure");
			map.put("reason", "请输入应用角色ID");
			return map;
		}
		// 2.判断应用角色是否存在
		AppRole appRole = appRoleService.getRoleDetailForApp(appRoleId);
		if (appRole == null) {
			map.put("code", "5016");
			map.put("result", "failure");
			map.put("reason", "您输入的应用角色ID不存在");
			return map;
		}

		// 判断角色名是否和数据库中一致
		String roleName = appRoleVO.getName();
		if (roleName != null && !roleName.equals("")) {
			// 如果不一致进行修改
			if (!roleName.equals(appRole.getName())) {
				appRole.setName(roleName);
				appRoleService.updateAppRole(appRole);
			}
		}

		// 3.根据角色名和应用id获取该AppRole对象
		AppRole tempAppRole = new AppRole();
		tempAppRole.setName(appRoleVO.getName());
		tempAppRole.setAppId(appRoleVO.getAppId());
		// 4.查询出的要修改的AppRole对象
		AppRole appRoleResult = appRoleService.getRoleByNameAndApp(tempAppRole);

		// 5.从AppRole对象中获取当前的用户
		List<Person> persons = appRoleResult.getPersons();
		// 把当前用户的名称按逗号隔开拼成字符串，以便后面判断
		StringBuffer personUserNames = new StringBuffer(",");
		for (Person p : persons) {
			personUserNames.append(p.getUsername());
			personUserNames.append(",");
		}

		// 6.从AppRole对象中获取当前的部门
		List<Department> departments = appRoleResult.getDepartments();
		// 把当前部门的名称按逗号隔开拼成字符串，以便后面判断
		StringBuffer departmentNames = new StringBuffer(",");
		for (Department d : departments) {
			departmentNames.append(d.getName());
			departmentNames.append(",");
		}

		// 7.判断用户要增加的用户是否已经是该角色下的用户,如果存在提示不能增加这些用户
		List<String> addUserNames = appRoleVO.getAddUserNames();
		// 定义变量保存要返回的内容
		String returnString = ",";
		if (addUserNames != null && addUserNames.size() > 0) {
			for (String s : addUserNames) {
				if (personUserNames.indexOf("," + s + ",") >= 0) {
					returnString = returnString + s + " ";
				}
			}
			if (returnString.length() != 1) {
				map.put("code", "5017");
				map.put("result", "failure");
				map.put("reason", returnString.substring(1, returnString.length()) + "用户已经属于该角色,无法添加");
				return map;
			}
		}

		// 8.判断用户要移除的用户是否在该角色中，如果不存在提示无法移除这些用户
		List<String> removeUserNames = appRoleVO.getRemoveUserNames();
		returnString = ",";
		if (removeUserNames != null && removeUserNames.size() > 0) {
			for (String s : removeUserNames) {
				if (personUserNames.indexOf("," + s + ",") < 0) {
					returnString = returnString + s + " ";
				}
			}
			if (returnString.length() != 1) {
				map.put("code", "5018");
				map.put("result", "failure");
				map.put("reason", returnString.substring(1, returnString.length()) + "用户不属于该角色,无法移除");
				return map;
			}
		}

		// 9.判断用户要增加的部门是否已经在该角色下，如果存在提示不能增加这些部门
		List<String> addDepartmentNames = appRoleVO.getAddDepartmentNames();
		returnString = ",";
		if (addDepartmentNames != null && addDepartmentNames.size() > 0) {
			for (String s : addDepartmentNames) {
				if (departmentNames.indexOf("," + s + ",") >= 0) {
					returnString = returnString + s + " ";
				}
			}
			if (returnString.length() != 1) {
				map.put("code", "5019");
				map.put("result", "failure");
				map.put("reason", returnString.substring(1, returnString.length()) + "部门已经属于该角色,无法添加");
				return map;
			}
		}

		// 10.判断用户要移除的部门是否存在于该角色中，如果不存在提示不能移除这些部门
		List<String> removeDepartmentNames = appRoleVO.getRemoveDepartmentNames();
		returnString = ",";
		if (removeDepartmentNames != null && removeDepartmentNames.size() > 0) {
			for (String s : removeDepartmentNames) {
				if (departmentNames.indexOf("," + s + ",") < 0) {
					returnString = returnString + s + " ";
				}
			}
			if (returnString.length() != 1) {
				map.put("code", "5020");
				map.put("result", "failure");
				map.put("reason", returnString.substring(1, returnString.length()) + "部门不属于该角色,无法移除");
				return map;
			}
		}

		// 11.都没问题后,把该移除的移除,该添加的添加
		// 11.1添加用户
		if (addUserNames != null && addUserNames.size() > 0) {
			// 根据用户名获取所有要添加用户对象
			List<Person> addUserList = personService.getPersonsByUserNames(addUserNames);
			// 构造要添加的对象的List
			List<AppRolePerson> addAppRolePersons = new ArrayList<AppRolePerson>();
			// 临时变量
			AppRolePerson tempAppRolePerson = null;
			for (Person p : addUserList) {
				tempAppRolePerson = new AppRolePerson();
				tempAppRolePerson.setPerson(p);
				tempAppRolePerson.setAppRole(appRoleResult);

				addAppRolePersons.add(tempAppRolePerson);
			}
			// 执行添加操作
			appRolePerService.addPersonsToRole(addAppRolePersons);
		}

		// 11.2移除用户
		if (removeUserNames != null && removeUserNames.size() > 0) {
			// 根据用户名获取所有要移除用户对象
			List<Person> removeUserList = personService.getPersonsByUserNames(removeUserNames);
			// 构造要移除的对象的List
			List<AppRolePerson> removeAppRolePersons = new ArrayList<AppRolePerson>();
			// 临时变量
			AppRolePerson tempAppRolePerson = null;
			for (Person p : removeUserList) {
				tempAppRolePerson = new AppRolePerson();
				tempAppRolePerson.setPerson(p);
				tempAppRolePerson.setAppRole(appRoleResult);

				removeAppRolePersons.add(tempAppRolePerson);
			}
			// 执行移除操作
			appRolePerService.removePersonsFromRole(removeAppRolePersons);
		}

		// 11.3添加部门
		if (addDepartmentNames != null && addDepartmentNames.size() > 0) {
			// 根据部门名称获取所有要添加部门对象
			List<Department> addDepartmentList = departmentService.getDepartmentsByNames(addDepartmentNames);
			// 构造要添加的对象的List
			List<AppRoleDepartment> addAppRoleDepartments = new ArrayList<AppRoleDepartment>();
			// 临时变量
			AppRoleDepartment tempAppRoleDepartment = null;
			for (Department d : addDepartmentList) {
				tempAppRoleDepartment = new AppRoleDepartment();
				tempAppRoleDepartment.setDepartment(d);
				tempAppRoleDepartment.setAppRole(appRoleResult);

				addAppRoleDepartments.add(tempAppRoleDepartment);
			}
			// 执行添加操作
			appRoleDepService.addDepsToRole(addAppRoleDepartments);
		}
		// 11.4移除部门
		if (removeDepartmentNames != null && removeDepartmentNames.size() > 0) {
			// 根据部门名称获取所有要移除部门对象
			List<Department> removeDepartmentList = departmentService.getDepartmentsByNames(removeDepartmentNames);
			// 构造要移除的对象的List
			List<AppRoleDepartment> removeAppRoleDepartments = new ArrayList<AppRoleDepartment>();
			// 临时变量
			AppRoleDepartment tempAppRoleDepartment = null;
			for (Department d : removeDepartmentList) {
				tempAppRoleDepartment = new AppRoleDepartment();
				tempAppRoleDepartment.setDepartment(d);
				tempAppRoleDepartment.setAppRole(appRoleResult);

				removeAppRoleDepartments.add(tempAppRoleDepartment);
			}
			// 执行添加操作
			appRoleDepService.removeDepsFromRole(removeAppRoleDepartments);
		}

		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160302
	 * @url /rest/appRole/delete/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppRole appRole = appRoleService.getRoleDetailForApp(id);
		if (appRole == null) {
			map.put("code", "5022");
			map.put("result", "failure");
			map.put("reason", "您删除的角色不存在");
			return map;
		}
		// 判断有没有权限进行操作
		Map<String, Object> preHandleMap = preHandle(request, appRole.getAppId());
		if (preHandleMap != null) {
			return preHandleMap;
		}

		appRoleService.removeRoleForApp(id);
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}

	/*
	 * 判断是否有权限执行修改删除等操作
	 */
	private Map<String, Object> preHandle(HttpServletRequest request, Integer appId) {
		Map<String, Object> map = null;
		// 从request中获取执行当操作的用户
		Person person = (Person) request.getAttribute("restUser");
		AppInfo appInfo = appInfoService.getAppById(appId);

		if (appInfo == null) {
			map = new HashMap<String, Object>();
			map.put("code", "5010");
			map.put("result", "failure");
			map.put("reason", "您输入的应用ID不存在");
			return map;
		}

		// 判断人员是否有权删除该应用，应用的拥有者以及应用的管理部门的管理员有权删除
		if (person.getId() != appInfo.getCreator().getId() && !positionService.isAdmin(person.getUsername(), appInfo.getManageDep().getFullname())) {
			map = new HashMap<String, Object>();
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}

		return map;
	}

}
