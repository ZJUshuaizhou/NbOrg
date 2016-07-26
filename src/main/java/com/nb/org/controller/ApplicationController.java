package com.nb.org.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppOAuth;
import com.nb.org.domain.AppRole;
import com.nb.org.domain.AppSAML;
import com.nb.org.domain.AppSTS;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.dto.AppDTO;
import com.nb.org.dto.RoleDTO;
import com.nb.org.dto.RoleDepsDTO;
import com.nb.org.dto.RolePersDTO;
import com.nb.org.exception.AppUpdateException;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IAppRoleService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.impl.AppMgtService;
import com.nb.org.util.AppValidator;
import com.nb.org.vo.ApplicationVO;

/**
 * @author huangxin xin 2016年1月22日
 */

@Controller
@RequestMapping("/application")
public class ApplicationController {
	@Autowired
	private AppMgtService service;

	@Autowired
	private IAppRoleService roleService;

	@Autowired
	IDepartmentService departmentService;

	// @RequestMapping("/index")
	// public String manage(HttpSession session, Model model) {
	// Person p = new Person();
	// Department dep = new Department();
	// dep.setId(2);
	// dep.setName("工商局");
	// p.setId(26);
	// p.setName("黄鑫");
	// ArrayList<Department> deps = new ArrayList<Department>();
	// deps.add(dep);
	// p.setDeps(deps);
	// session.setAttribute("user", p);
	// session.setAttribute("dep", dep);
	// session.setAttribute("adminFlag", 0);
	// return "redirect:list";
	// }

	@RequestMapping("/search")
	public String searchApps(HttpSession session, Model model, HttpServletRequest request,
			@RequestParam(required = true, defaultValue = "true") boolean myapp,
			@RequestParam(defaultValue = "1") int pageNo_mine, @RequestParam(defaultValue = "6") int len_mine,
			@RequestParam(defaultValue = "1") int pageNo_dep, @RequestParam(defaultValue = "6") int len_dep,
			@RequestParam(defaultValue = "") String searchAppByName,
			@RequestParam(defaultValue = "") String searchOtherAppByName,
			@RequestParam(defaultValue = "") String searchOtherAppByCreator) {
		String appname = (String) session.getAttribute("searchAppByName");
		String appothername = (String) session.getAttribute("searchOtherAppByName");
		String appothercreator = (String) session.getAttribute("searchOtherAppByCreator");
		if (searchAppByName.length() + searchOtherAppByCreator.length() + searchOtherAppByName.length() < 1) {
			if (request.getMethod().equals("GET")
					&& (appname != null || appothercreator != null || appothername != null)) {
				searchAppByName = appname;
				searchOtherAppByName = appothername;
				searchOtherAppByCreator = appothercreator;
			} else {
				session.removeAttribute("searchAppByName");
				session.removeAttribute("searchOtherAppByName");
				session.removeAttribute("searchOtherAppByCreator");
				return "redirect:list";
			}
		} else {
			session.setAttribute("searchAppByName", searchAppByName);
			session.setAttribute("searchOtherAppByName", searchOtherAppByName);
			session.setAttribute("searchOtherAppByCreator", searchOtherAppByCreator);
		}
		Person p = (Person) session.getAttribute("user");
		Department department = (Department) session.getAttribute("dep");
		ApplicationVO vo = new ApplicationVO(pageNo_mine, len_mine, p.getId(), department.getId(), searchAppByName);
		if (searchAppByName.length() > 0)
			vo = service.getAppInfoService().searchAppByName(vo);
		else
			vo = service.getAppInfoService().listAppsPageByPersonID(vo);
		model.addAttribute("raws_mine", AppDTO.getListFromApps((vo.getRows())));
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage = vo.getTotal() % len_mine == 0 ? vo.getTotal() / len_mine : (vo.getTotal() / len_mine + 1);
		model.addAttribute("totalPage_mine", totalpage);
		model.addAttribute("pageNo_mine", pageNo_mine);

		vo = new ApplicationVO(pageNo_dep, len_dep, p.getId(), department.getId(), searchOtherAppByName,
				searchOtherAppByCreator);
		if (searchOtherAppByCreator.length() + searchOtherAppByName.length() < 1) {
			vo = service.getAppInfoService().listAppsPageByDepartmentID(vo);
		} else
			vo = service.getAppInfoService().searchOtherApp(vo);
		model.addAttribute("raws_dep", AppDTO.getListFromApps((vo.getRows())));
		model.addAttribute("total_dep", vo.getTotal());
		int totalpage_dep = vo.getTotal() % len_dep == 0 ? vo.getTotal() / len_dep : (vo.getTotal() / len_dep + 1);
		model.addAttribute("totalPage_dep", totalpage_dep);
		model.addAttribute("pageNo_dep", pageNo_dep);

		model.addAttribute("myapp", myapp);
		return "application/application";
	}

	@RequestMapping("/list")
	public String listAppsByPerson(HttpSession session, Model model,
			@RequestParam(required = true, defaultValue = "true") boolean myapp,
			@RequestParam(defaultValue = "1") int pageNo_mine, @RequestParam(defaultValue = "6") int len_mine,
			@RequestParam(defaultValue = "1") int pageNo_dep, @RequestParam(defaultValue = "6") int len_dep) {
		session.removeAttribute("searchAppByName");
		session.removeAttribute("searchOtherAppByName");
		session.removeAttribute("searchOtherAppByCreator");
		Person p = (Person) session.getAttribute("user");
		Department department = (Department) session.getAttribute("dep");
		ApplicationVO vo = new ApplicationVO(pageNo_mine, len_mine, p.getId(), department.getId());
		vo = service.getAppInfoService().listAppsPageByPersonID(vo);
		model.addAttribute("raws_mine", AppDTO.getListFromApps((vo.getRows())));
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage = vo.getTotal() % len_mine == 0 ? vo.getTotal() / len_mine : (vo.getTotal() / len_mine + 1);
		model.addAttribute("totalPage_mine", totalpage);
		model.addAttribute("pageNo_mine", pageNo_mine);
		vo = new ApplicationVO(pageNo_dep, len_dep, p.getId(), department.getId());
		vo = service.getAppInfoService().listAppsPageByDepartmentID(vo);
		model.addAttribute("raws_dep", AppDTO.getListFromApps((vo.getRows())));
		model.addAttribute("total_dep", vo.getTotal());
		int totalpage_dep = vo.getTotal() % len_dep == 0 ? vo.getTotal() / len_dep : (vo.getTotal() / len_dep + 1);
		model.addAttribute("totalPage_dep", totalpage_dep);
		model.addAttribute("pageNo_dep", pageNo_dep);
		String msg = (String) session.getAttribute("msg");
		String error = (String) session.getAttribute("error");

		if (msg != null) {
			model.addAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		if (error != null) {
			model.addAttribute("error", error);
			session.removeAttribute("error");
		}
		model.addAttribute("myapp", myapp);
		return "application/application";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(HttpSession session, @RequestParam(required = true) int app, Model model) {
		AppInfo appInfo = service.getAppInfoService().getAppById(app);
		try {
			service.removeApplication(appInfo);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			session.setAttribute("error", e.getMessage());
			e.printStackTrace();
			return "{\"data\":\"success\"}";
		}
		session.setAttribute("msg", "删除成功！");
		return "{\"data\":\"success\"}";
	}

	@RequestMapping("/add")
	public String add(HttpSession session) {

		return "application/addApplication";
	}

	@RequestMapping(value="/finishAdd",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String finishAdd(String name, String description, HttpSession session, Model model) {
		if (name == null || name.length() < 1) {
			session.setAttribute("error", "填写信息有误，请重试");
			return "redirect:list";
		}
		Person p = (Person) session.getAttribute("user");
		Department department = (Department) session.getAttribute("dep");
		AppInfo info = new AppInfo();
		info.setCreator(p);
		info.setManageDep(department);
		info.setDescription(description);
		info.setName(name);
		AppSAML saml = new AppSAML(info.getName(), "", "", "");
		AppOAuth oauth = new AppOAuth(info.getName(), "http://", "", "");
		AppSTS sts = null;
		try {
			String ca = service.getWso2Mgt().getData().getDefaultSTSCertAlias();
			if (ca == null || ca.length() < 1)
				ca = "localhost";
			sts = new AppSTS(info.getName(), "endpoint-address", ca);
		} catch (AppUpdateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			session.setAttribute("error", e1.getMsg());
			sts = new AppSTS(info.getName(), "endpoint-address", "localhost");
		}
		info.setSts(sts);
		info.setSaml(saml);
		info.setOauth(oauth);
		try {
			service.addApplication(info);
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			session.setAttribute("error", e.getMsg());
			return "redirect:list";
		}
		AppInfo app = service.getAppInfoService().getAppByName(info.getName());
		model.addAttribute("app", app);
		String[] alias = null;
		try {
			alias = service.getWso2Mgt().getCertAlias();
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", e.getMsg());
			model.addAttribute("alias", new String[] { "localhost" });
			return "application/applicationConfirm";
		}
		model.addAttribute("alias", alias);
		return "application/applicationConfirm";
	}

	// @RequestMapping("/confirm")
	// public String updateApplication(Model model) {
	//
	//
	// return "application/applicationConfirm";
	// }

	@RequestMapping("/detail")
	public String detail(@RequestParam(required = true) int app, Model model) {
		AppInfo appInfo = service.getAppInfoService().getAppById(app);
		List<RoleDTO> roles = new ArrayList<RoleDTO>();
		List<AppRole> appRole = roleService.getRoleListForApp(app);
		for (AppRole r : appRole) {
			ArrayList<RolePersDTO> persDTOs = new ArrayList<RolePersDTO>();
			ArrayList<RoleDepsDTO> depsDTOs = new ArrayList<RoleDepsDTO>();
			List<Person> person = r.getPersons();
			List<Department> department = r.getDepartments();
			for (Person p : person) {
				persDTOs.add(new RolePersDTO(p.getId(), p.getName(), p.getCreateDep().getName()));
			}
			for (Department d : department) {
				depsDTOs.add(new RoleDepsDTO(d.getId(), d.getFullname()));
			}
			roles.add(new RoleDTO(r.getId(), r.getName(), persDTOs, depsDTOs));
		}

		model.addAttribute("app", appInfo);
		model.addAttribute("roles", roles);
		return "application/applicationDetail";
	}

	@RequestMapping("/addRole")
	public String configRoleAndUser(HttpSession session, Model model, int appId) {
		// ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
		// RoleDTO dto = new RoleDTO(1,"role1","黄鑫1，黄鑫2，黄鑫3，黄鑫4","公安局，工商局");
		// roles.add(dto);
		// dto = new RoleDTO(2,"role2","黄鑫1，黄鑫2，黄鑫3，黄鑫4","公安局，工商局");
		// roles.add(dto);
		// System.out.println(dto);
		// model.addAttribute("roles", roles);
		List<AppRole> roles = roleService.getRoleListForApp(appId);
		ArrayList<RoleDTO> dtos = new ArrayList<RoleDTO>();
		for (AppRole r : roles) {
			List<RolePersDTO> pers = new ArrayList<RolePersDTO>();
			List<RoleDepsDTO> deps = new ArrayList<RoleDepsDTO>();
			for (Person p : r.getPersons()) {
				RolePersDTO per = new RolePersDTO(p.getId(), p.getName(), p.getCreateDep().getName());
				pers.add(per);
			}
			for (Department d : r.getDepartments()) {
				RoleDepsDTO dep = new RoleDepsDTO(d.getId(), d.getFullname());
				deps.add(dep);
			}
			dtos.add(new RoleDTO(r.getId(), r.getName(), pers, deps));
		}
		model.addAttribute("roles", dtos);

		Department dep = (Department) session.getAttribute("dep");
		List<Person> resultlist = new ArrayList<Person>();
		try {
			resultlist.addAll(departmentService.getDepartmentById(dep.getId()).getPersons());
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		resultlist = getChildPersons(resultlist, dep);

		List<RolePersDTO> persDTOs = new ArrayList<RolePersDTO>();
		for (Person person : resultlist) {
			persDTOs.add(new RolePersDTO(person.getId(), person.getName(), ""));
		}
		List<Department> depList = new ArrayList<Department>();
		depList.add(dep);
		depList = getChildDeps(depList, dep);
		List<RoleDepsDTO> depsDTOs = new ArrayList<RoleDepsDTO>();
		for (Department d : depList) {
			depsDTOs.add(new RoleDepsDTO(d.getId(), d.getFullname()));
		}
		model.addAttribute("persons", persDTOs);
		model.addAttribute("departments", depsDTOs);
		model.addAttribute("appId", appId);
		model.addAttribute("toggle", false);

		return "application/configRoleAndUser";
	}

	/**
	 * @param session
	 * @param skip
	 * @param oldName
	 * @param app
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String update(HttpSession session, boolean skip, AppInfo app, Model model) {
		if (!skip) {
			String oUrl = app.getOauth().getUrl(), stsEndpoint = app.getSts().getEndpoint(), name = app.getName();
			if (name.length() < 1) {
				model.addAttribute("error", "名称不能为空，更新失败，已自动跳过");
				return "application/configRoleAndUser";
			}
			if (AppValidator.isURL(oUrl))
				app.getOauth().setUrl("http://");
			if (stsEndpoint.length() < 1)
				app.getSts().setEndpoint("endpoint-address");
			AppInfo appInfo = service.getAppInfoService().getAppById(app.getId());
			appInfo.setDescription(app.getDescription());
			appInfo.setName(app.getName());
			appInfo.getSaml().setAppName(app.getName());
			appInfo.getSaml().setUrl(app.getSaml().getUrl());
			appInfo.getSaml().setLogoutUrl(app.getSaml().getLogoutUrl());
			appInfo.getOauth().setAppName(app.getName());
			appInfo.getOauth().setUrl(app.getOauth().getUrl());
			appInfo.getSts().setCertAlias(app.getSts().getCertAlias());
			appInfo.getSts().setAppName(app.getName());
			appInfo.getSts().setEndpoint(app.getSts().getEndpoint());
			try {
				service.updateApplication(appInfo);
			} catch (Exception e) {
				model.addAttribute("error", "填写信息有误，更新失败，已自动跳过");
			}
		}
		model.addAttribute("appId", app.getId());
		return "redirect:listRoles";
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(int roleId, int appId) {
		roleService.removeRoleForApp(roleId);
		return "{\"appId\":\"" + String.valueOf(appId) + "\"}";
	}

	@RequestMapping("/editRole")
	public String editRole(int roleId, int appId, Model model, HttpSession session) {
		AppRole r = roleService.getRoleDetailForApp(roleId);
		List<RolePersDTO> pers = new ArrayList<RolePersDTO>();
		List<RoleDepsDTO> deps = new ArrayList<RoleDepsDTO>();
		int[] pids = new int[r.getPersons().size()];
		int[] dids = new int[r.getDepartments().size()];
		int i = 0;
		for (Person p : r.getPersons()) {
			RolePersDTO per = new RolePersDTO(p.getId(), p.getName(), p.getCreateDep().getName());
			pids[i] = p.getId();
			pers.add(per);
			i++;
		}
		i = 0;
		for (Department d : r.getDepartments()) {
			RoleDepsDTO dep = new RoleDepsDTO(d.getId(), d.getFullname());
			dids[i] = d.getId();
			deps.add(dep);
			i++;
		}
		Arrays.sort(dids);
		Arrays.sort(pids);

		Department dep = (Department) session.getAttribute("dep");
		List<Person> resultlist = new ArrayList<Person>();
		try {
			resultlist.addAll(departmentService.getDepartmentById(dep.getId()).getPersons());
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		resultlist = getChildPersons(resultlist, dep);

		List<RolePersDTO> persDTOs = new ArrayList<RolePersDTO>();
		for (Person person : resultlist) {
			if (Arrays.binarySearch(pids, person.getId()) < 0)
				persDTOs.add(new RolePersDTO(person.getId(), person.getName(), ""));
		}
		List<Department> depList = new ArrayList<Department>();
		depList.add(dep);
		depList = getChildDeps(depList, dep);
		List<RoleDepsDTO> depsDTOs = new ArrayList<RoleDepsDTO>();
		for (Department d : depList) {
			if (Arrays.binarySearch(dids, d.getId()) < 0)
				depsDTOs.add(new RoleDepsDTO(d.getId(), d.getFullname()));
		}

		List<AppRole> approles = roleService.getRoleListForApp(appId);
		ArrayList<RoleDTO> roledtos = new ArrayList<RoleDTO>();
		for (AppRole ro : approles) {
			List<RolePersDTO> pds = new ArrayList<RolePersDTO>();
			List<RoleDepsDTO> dds = new ArrayList<RoleDepsDTO>();
			for (Person pper : ro.getPersons()) {
				RolePersDTO pd = new RolePersDTO(pper.getId(), pper.getName(), pper.getCreateDep().getName());
				pds.add(pd);
			}
			for (Department d : ro.getDepartments()) {
				RoleDepsDTO dd = new RoleDepsDTO(d.getId(), d.getFullname());
				dds.add(dd);
			}
			roledtos.add(new RoleDTO(ro.getId(), ro.getName(), pds, dds));
		}
		model.addAttribute("roles", roledtos);
		model.addAttribute("persons", persDTOs);
		model.addAttribute("departments", depsDTOs);
		model.addAttribute("roleName", r.getName());
		model.addAttribute("roleId", r.getId());

		model.addAttribute("originPer", pers);
		model.addAttribute("originDep", deps);

		model.addAttribute("toggle", false);

		model.addAttribute("appId", appId);
		return "application/configRoleAndUser";
	}

	@RequestMapping(value="/updateRole",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String updateRole(HttpSession session, int[] pidList, int[] didList,
			@RequestParam(required = true) int appId, @RequestParam(defaultValue = "-1") int roleId, String roleName,
			Model model) {
		// Department dep = (Department) session.getAttribute("dep");

		List<Person> persons = new ArrayList<Person>();
		List<Department> departments = new ArrayList<Department>();
		for (int p : pidList) {
			Person person = new Person();
			person.setId(p);
			persons.add(person);
		}

		for (int d : didList) {
			Department department = new Department();
			department.setId(d);
			departments.add(department);
		}

		if (roleId < 0) {
			roleService.addRoleForApp(new AppRole(0, roleName, appId, persons, departments));
		} else {
			roleService.editRoleForApp(new AppRole(roleId, roleName, appId, persons, departments));
		}
		// List<AppRole> roles = roleService.getRoleListForApp(appId);
		// List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		// for (AppRole role : roles) {
		// RoleDTO dto = new RoleDTO();
		// List<RolePersDTO> pers = new ArrayList<RolePersDTO>();
		// List<RoleDepsDTO> deps = new ArrayList<RoleDepsDTO>();
		// for (Person p : role.getPersons()) {
		// RolePersDTO ps = new RolePersDTO(p.getId(), p.getName(),
		// p.getCreateDep().getFullname());
		// pers.add(ps);
		// }
		//
		// for (Department d : role.getDepartments()) {
		// RoleDepsDTO ds = new RoleDepsDTO(d.getId(), d.getFullname());
		// deps.add(ds);
		// }
		// dto.setId(role.getId());
		// dto.setName(role.getName());
		// dto.setPersons(pers);
		// dto.setDepartments(deps);
		// roleDTOs.add(dto);
		// }
		// List<Person> resultlist = new ArrayList<Person>();
		// resultlist = getChildPersons(resultlist, dep);
		//
		// List<RolePersDTO> persDTOs = new ArrayList<RolePersDTO>();
		// for (Person person : resultlist) {
		// persDTOs.add(new RolePersDTO(person.getId(), person.getName(),
		// person.getCreateDep().getFullname()));
		// }
		model.addAttribute("appId", appId);
		// model.addAttribute("roles", roleDTOs);
		// model.addAttribute("toggle", false);
		return "redirect:listRoles";
	}

	@RequestMapping("/listRoles")
	public String listRoles(int appId, Model model) {
		List<AppRole> roles = roleService.getRoleListForApp(appId);
		ArrayList<RoleDTO> dtos = new ArrayList<RoleDTO>();
		for (AppRole r : roles) {
			List<RolePersDTO> pers = new ArrayList<RolePersDTO>();
			List<RoleDepsDTO> deps = new ArrayList<RoleDepsDTO>();
			for (Person p : r.getPersons()) {
				RolePersDTO per = new RolePersDTO(p.getId(), p.getName(), p.getCreateDep().getName());
				pers.add(per);
			}
			for (Department d : r.getDepartments()) {
				RoleDepsDTO dep = new RoleDepsDTO(d.getId(), d.getFullname());
				deps.add(dep);
			}
			dtos.add(new RoleDTO(r.getId(), r.getName(), pers, deps));
		}
		model.addAttribute("roles", dtos);
		model.addAttribute("toggle", true);
		model.addAttribute("appId", appId);
		return "application/configRoleAndUser";
	}

	@RequestMapping("/searchDepartment")
	@ResponseBody
	public String searchDepartment(String name, int[] dids) {
		List<RoleDepsDTO> dtos = roleService.searchDepartment(name);
		StringBuilder sb = new StringBuilder();
		StringBuilder arrayElem = new StringBuilder();
		sb.append("{\"departments\":[");
		if (dids != null && dids.length > 0) {
			Arrays.sort(dids);
		}
		for (RoleDepsDTO d : dtos) {
			int i = Arrays.binarySearch(dids, d.getId());
			if (i < 0) {
				arrayElem.append("{\"id\":");
				arrayElem.append(d.getId());
				arrayElem.append(',');

				arrayElem.append("\"fullname\":\"");
				arrayElem.append(d.getFullname());
				arrayElem.append("\"},");
			}
		}
		if (arrayElem.length() > 0)
			arrayElem.deleteCharAt(arrayElem.length() - 1);
		sb.append(arrayElem);
		sb.append("]}");
		return sb.toString();
	}

	@RequestMapping("/searchPerson")
	@ResponseBody
	public String searchPerson(String name, int[] pids) {
		List<RolePersDTO> dtos = roleService.searchPerson(name);
		StringBuilder sb = new StringBuilder();
		StringBuilder arrayElem = new StringBuilder();
		sb.append("{\"persons\":[");
		if (pids != null && pids.length > 0) {
			Arrays.sort(pids);
		}
		for (RolePersDTO d : dtos) {
			int i = Arrays.binarySearch(pids, d.getId());
			if (i < 0) {
				arrayElem.append("{\"id\":");
				arrayElem.append(d.getId());
				arrayElem.append(',');

				arrayElem.append("\"name\":\"");
				arrayElem.append(d.getName());
				arrayElem.append("\",");

				arrayElem.append("\"dep\":\"");
				arrayElem.append(d.getDep());
				arrayElem.append("\"},");
			}
		}
		if (arrayElem.length() > 0)
			arrayElem.deleteCharAt(arrayElem.length() - 1);
		sb.append(arrayElem);
		sb.append("]}");
		return sb.toString();
	}

	@RequestMapping("/edit")
	public String edit(HttpSession session, @RequestParam(required = true) int app, Model model) {
		// ArrayList<RoleDTO> persons = new ArrayList<RoleDTO>();
		// for (int i = 0; i < 20; i++) {
		// persons.add(new RoleDTO(i, i + ":abc", "hahaha", "deep"));
		// }
		// model.addAttribute("persons", persons);

		AppInfo appInfo = service.getAppInfoService().getAppById(app);
		model.addAttribute("application", appInfo);
		String[] alias = null;
		try {
			alias = service.getWso2Mgt().getCertAlias();
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", e.getMsg());
			model.addAttribute("alias", new String[] { "localhost" });
			return "application/updateApplication";
		}
		model.addAttribute("alias", alias);
		return "application/updateApplication";

	}

	@RequestMapping(value="/finishEdit",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String finishEdit(HttpSession session, AppInfo app, Model model) {
		String oUrl = app.getOauth().getUrl(), stsEndpoint = app.getSts().getEndpoint(), name = app.getName();
		if (name.length() < 1) {
			model.addAttribute("error", "名称不能为空，更新失败");
			return "application/list";
		}
		if (!AppValidator.isURL(oUrl))
			app.getOauth().setUrl("http://");
		if (stsEndpoint.length() < 1)
			app.getSts().setEndpoint("endpoint-address");
		AppInfo appInfo = service.getAppInfoService().getAppById(app.getId());
		appInfo.setDescription(app.getDescription());
		appInfo.setName(app.getName());
		appInfo.getSaml().setAppName(app.getName());
		appInfo.getSaml().setUrl(app.getSaml().getUrl());
		appInfo.getSaml().setLogoutUrl(app.getSaml().getLogoutUrl());
		appInfo.getOauth().setAppName(app.getName());
		appInfo.getOauth().setUrl(app.getOauth().getUrl());
		appInfo.getSts().setCertAlias(app.getSts().getCertAlias());
		appInfo.getSts().setAppName(app.getName());
		appInfo.getSts().setEndpoint(app.getSts().getEndpoint());
		try {
			service.updateApplication(appInfo);
		} catch (AppUpdateException e) {
			model.addAttribute("error", e.getMessage());
			return "application/updateApplication";
		}
		session.setAttribute("msg", "更新成功");
		return "redirect:list";
	}

	@RequestMapping("/validateAppRole")
	@ResponseBody
	public String validateRole(String roleName, int appId, @RequestParam(defaultValue = "-1") int roleId) {
		String result = "";
		if (roleId < 0) {
			if (roleName != null && roleName.length() > 0) {
				AppRole role = new AppRole();
				role.setAppId(appId);
				role.setName(roleName);
				role = roleService.getRoleByNameAndApp(role);
				if (role == null)
					result = "{\"data\":\"success\"}";
			}
		} else if (roleName != null && roleName.length() > 0) {
			result = "{\"data\":\"success\"}";
		}
		return result;

	}

	@RequestMapping("/validateAppName")
	@ResponseBody
	public String validate(String name) {
		String result = "";
		if (name != null && name.length() > 0) {
			AppInfo app = null;
			app = service.getAppInfoService().getAppByName(name);
			if (app == null)
				result = "{\"data\":\"success\"}";
		}
		return result;
	}

	@RequestMapping("/validateIssuer")
	@ResponseBody
	public String validateIssuer(String issuer) {
		String result = "";
		if (issuer != null && issuer.length() > 0) {
			AppSAML app = null;
			app = service.getAppInfoService().getSamlService().getSAMLByAppIssuer(issuer);
			if (app == null)
				result = "{\"data\":\"success\"}";
		}
		return result;
	}

	private List<Person> getChildPersons(List<Person> resultlist, Department dep) {
		List<Department> list = new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dep.getPersons() != null){
			if(resultlist.size()<200)
				resultlist.addAll(dep.getPersons());
		}
		if (!list.isEmpty() && resultlist.size()<200) {
			for (Department d : list) {
				getChildPersons(resultlist, d);
			}
		}
		HashSet<Person> temp = new HashSet<Person>(resultlist);
		resultlist.clear();
		resultlist.addAll(temp);
		return resultlist;
	}

	private List<Department> getChildDeps(List<Department> resultlist, Department dep) {
		List<Department> list = new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);

		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!list.isEmpty() && resultlist.size()<200) {
			for (Department d : list) {
				resultlist.add(d);
				getChildDeps(resultlist, d);
			}
		}
		return resultlist;
	}
}