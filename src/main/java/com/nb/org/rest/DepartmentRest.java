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

import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.domain.Person;
import com.nb.org.domain.SimpleDepartment;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.util.SNGenerator;
import com.nb.org.util.StringUtil;

@Controller
@RequestMapping("/rest/department")
public class DepartmentRest {
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private SNGenerator snGenerator;
	
	/**
	 * @author upshi
	 * @date 20160120
	 * @url /rest/department/add
	 * 需要的请求体json格式：{"name":"","contactPerson":"","contactNumber":"","address":"","description":""}
	 */
	@ResponseBody
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Map<String, String> input, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户登录的部门 
		Department parentDep = (Department) request.getAttribute("departmentName");
		// 判断父部门是否存在
		// 如果父部门存在,检查传入各参数是否为空
		String msg = validInput(input);
		if (msg == null) {
			// 如果传入参数没有错,则调用本地方法生成一个bean
			Department department = generateBean(input, parentDep);
			department.setFullname(parentDep.getFullname() + input.get("name"));
			// 调用service层插入bean
			DepartmentSN departmentSN = snGenerator.generateDepartmentSN(parentDep);
			try {
				departmentService.insertDepartment(department, departmentSN);
			} catch (DepartmentException e) {
				map.put("code", "4000");
				map.put("result", "failure");
				map.put("reason", "新增部门失败");
			}
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			// 如果传入的参数有错
			map.put("code", "4008");
			map.put("result", "failure");
			map.put("reason", "名称,联系人,以及联系人手机号必填");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @throws UnsupportedEncodingException 
	 * @date 20160121
	 * @url /rest/department/search/{name}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchName/{name}", method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable String name){
		Map<String, Object> map = new HashMap<String, Object>();
		Department department=null;
		try {
			department = departmentService.selectDepByName(new String(name.getBytes("ISO-8859-1"),"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码出错！");
		}
		if (department != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("department", department);
		} else {
			map.put("code", "4003");
			map.put("result", "failure");
			map.put("reason", "您查询的部门不存在");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160317
	 * @url /rest/department/searchBySN/{sn}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchBySN/{sn}", method = RequestMethod.GET)
	public Map<String, Object> searchBySN(@PathVariable String sn) {
		Map<String, Object> map = new HashMap<String, Object>();
		Department department = null;
		try {
			department = departmentService.selectDepBySN(sn);
			department.getPersons().clear();
		} catch (DepartmentException e) {
			map.put("code", "4003");
			map.put("result", "failure");
			map.put("reason", "您查询的部门不存在");
		}
		if (department != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("department", department);
		} else {
			map.put("code", "4003");
			map.put("result", "failure");
			map.put("reason", "您查询的部门不存在");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160121
	 * @url /rest/department/delete/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Department loginDept = (Department) request.getAttribute("departmentName");
		int result=0;
		if(id == null) {
			map.put("code", "4001");
			map.put("result", "failure");
			map.put("reason", "请输入要删除的部门ID");
			return map;
		}
		
		Department department = null;
		
		try {
			department = departmentService.getDepartmentById(id);
			if(department == null) {
				map.put("code", "4004");
				map.put("result", "failure");
				map.put("reason", "您删除的部门不存在");
				return map;
			}
		} catch (DepartmentException e1) {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
			return map;
		}
		
		boolean entitled = beforeUpdateAndDelete(loginDept,department);
		if(!entitled) {
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}
		
		try {
			result = departmentService.delDepartment(id);
		} catch (DepartmentException e) {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
			return map;
		}
		if (result == 1) {
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
		}
		return map;
	}
	
	
	/**
	 * @author upshi
	 * @date 20160121
	 * @url /rest/department/update
	 * @desc 暂不支持修改部门名称和父部门信息
	 * 需要的请求体json格式：{"id":"","name":"","contactPerson":"","contactNumber":"","address":"","description":""}
	 */
	@ResponseBody
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody Map<String, String> input, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取用户登录的部门
		Department loginDept = (Department) request.getAttribute("departmentName");
		
		//首先获得传入的部门id,如果为空则返回
		Integer id = null;
		try {
			id = Integer.parseInt(input.get("id"));
		} catch (Exception e) {
			map.put("code", "4005");
			map.put("result", "failure");
			map.put("reason", "请输入要修改的部门ID");
			return map;
		}
		
		//先根据id从数据库中获取原来的信息
		Department department=new Department();
		try {
			department = departmentService.getDepartmentById(id);
		} catch (DepartmentException e1) {
			e1.printStackTrace();
		}
		if (department != null) {
			boolean entitled = beforeUpdateAndDelete(loginDept,department);
			if(!entitled) {
				map.put("code", "1000");
				map.put("result", "failure");
				map.put("reason", "您没有此权限进行该操作");
				return map;
			}
			
			//根据传来的信息更改数据库中查出来的信息
			department = beforeUpdateDepartment(department, input);
			int result=0;
			try {
				result = departmentService.updateDepartment(department);
			} catch (DepartmentException e) {
				map.put("code", "4006");
				map.put("result", "failure");
				map.put("reason", "修改部门失败");
				return map;
			}
			if (result == 1) {
				//如果修改成功
				map.put("code", "8000");
				map.put("result", "success");
			} else {
				map.put("code", "4006");
				map.put("result", "failure");
				map.put("reason", "修改部门失败");
			}
		} else {
			//如果传来的id数据库中查不到
			map.put("code", "4007");
			map.put("result", "failure");
			map.put("reason", "您修改的部门不存在");
		}
		
		return map;
	}
	
	
	/**
	 * @author upshi
	 * @date 20160121
	 * @param department 数据库中查出来的原来的部门信息
	 * @param input 用户传入的参数
	 * @return 更新后的部门信息
	 * @desc 暂不支持修改部门名称和父部门信息
	 */
	private Department beforeUpdateDepartment(Department department, Map<String, String> input) {
		if (!StringUtil.isNoE(input.get("name"))) {
			department.setName(input.get("name"));
			department.setFullname(department.getParentdep().getFullname() + input.get("name"));
		}
		if (!StringUtil.isNoE(input.get("contactPerson"))) {
			department.setContactPerson(input.get("contactPerson"));
		}
		if (!StringUtil.isNoE(input.get("contactNumber"))) {
			department.setContactNumber(input.get("contactNumber"));
		}
		if (!StringUtil.isNoE(input.get("address"))) {
			department.setAddress(input.get("address"));
		}
		if (!StringUtil.isNoE(input.get("description"))) {
			department.setDescription(input.get("description"));
		}
		return department;
	}

	/**
	 * @author upshi
	 * @date 20160121
	 * @param input 用户输入参数
	 * @return 如果有错误返回具体错误信息,如果没错误返回null
	 */
	private String validInput(Map<String, String> input) {
		String msg = null;
		if(StringUtil.isNoE(input.get("name"))) {
			msg = "请输入部门名称";
			return msg;
		}
		if(StringUtil.isNoE(input.get("contactPerson"))) {
			msg = "请输入联系人名称";
			return msg;
		}
		if(StringUtil.isNoE(input.get("contactNumber"))) {
			msg = "请输入联系人手机";
			return msg;
		}
		return msg;
	}
	
	/**
	 * @author upshi 
	 * @date 20160121
	 * @param input 用户输入参数
	 * @return 根据输入参数返回一个bean对象,供增加时使用
	 */
	private Department generateBean(Map<String, String> input, Department parent) {
		Department dep = new Department();
		DepartmentSN departmentSN = snGenerator.generateDepartmentSN(parent);
		dep.setSn(departmentSN.getBase() + departmentSN.getNumber());
		dep.setName(input.get("name"));
		dep.setContactPerson(input.get("contactPerson"));
		dep.setContactNumber(input.get("contactNumber"));
		dep.setAddress(input.get("address"));
		dep.setDescription(input.get("description"));
		dep.setParentdep(parent);
		return dep;
	}
	
	/*
	 * 20160305
	 * 在执行修改和删除操作前判断，当前被操作人是否属于   操作人员登录的部门(权限范围内的)
	 * */
	private boolean beforeUpdateAndDelete(Department loginDept, Department modifyDept) {
		boolean entitled = false; 
		Department temp = modifyDept;
		do {
			if(loginDept.getName().equals(temp.getName())) {
				entitled = true;
				break;
			}
			temp = temp.getParentdep();
		} while (temp != null);
		
		return entitled;
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
	 * @url /rest/department/add
	 * 需要的请求体json格式：{"name":"","contactPerson":"","contactNumber":"","address":"","description":""}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> oldAdd(@RequestBody Map<String, String> input, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户登录的部门 
		Department parentDep = (Department) request.getAttribute("departmentName");
		// 判断父部门是否存在
		// 如果父部门存在,检查传入各参数是否为空
		String msg = validInput(input);
		if (msg == null) {
			// 如果传入参数没有错,则调用本地方法生成一个bean
			Department department = generateBean(input, parentDep);
			department.setFullname(parentDep.getFullname() + input.get("name"));
			// 调用service层插入bean
			DepartmentSN departmentSN = snGenerator.generateDepartmentSN(parentDep);
			try {
				departmentService.insertDepartment(department, departmentSN);
			} catch (DepartmentException e) {
				map.put("code", "4000");
				map.put("result", "failure");
				map.put("reason", "新增部门失败");
			}
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			// 如果传入的参数有错
			map.put("code", "4008");
			map.put("result", "failure");
			map.put("reason", "名称,联系人,以及联系人手机号必填");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @throws UnsupportedEncodingException 
	 * @date 20160820
	 * 旧版查找
	 * @url /rest/department/{name}
	 */
	@ResponseBody
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Map<String, Object> oldSearch(@PathVariable String name){
		Map<String, Object> map = new HashMap<String, Object>();
		Department department=null;
		try {
			department = departmentService.selectDepByName(new String(name.getBytes("ISO-8859-1"),"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码出错！");
		}
		if (department != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("department", department);
		} else {
			map.put("code", "4003");
			map.put("result", "failure");
			map.put("reason", "您查询的部门不存在");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * 旧版删除
	 * @url /rest/department/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> oldDelete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Department loginDept = (Department) request.getAttribute("departmentName");
		int result=0;
		if(id == null) {
			map.put("code", "4001");
			map.put("result", "failure");
			map.put("reason", "请输入要删除的部门ID");
			return map;
		}
		
		Department department = null;
		
		try {
			department = departmentService.getDepartmentById(id);
			if(department == null) {
				map.put("code", "4004");
				map.put("result", "failure");
				map.put("reason", "您删除的部门不存在");
				return map;
			}
		} catch (DepartmentException e1) {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
			return map;
		}
		
		boolean entitled = beforeUpdateAndDelete(loginDept,department);
		if(!entitled) {
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}
		
		try {
			result = departmentService.delDepartment(id);
		} catch (DepartmentException e) {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
			return map;
		}
		if (result == 1) {
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			map.put("code", "4004");
			map.put("result", "failure");
			map.put("reason", "您删除的部门不存在");
		}
		return map;
	}
	
	
	/**
	 * @author ishadow
	 * @date 20160820
	 * @url /rest/department/
	 * 旧版更新部门
	 * @desc 暂不支持修改部门名称和父部门信息
	 * 需要的请求体json格式：{"id":"","name":"","contactPerson":"","contactNumber":"","address":"","description":""}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> oldUpdate(@RequestBody Map<String, String> input, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取用户登录的部门
		Department loginDept = (Department) request.getAttribute("departmentName");
		
		//首先获得传入的部门id,如果为空则返回
		Integer id = null;
		try {
			id = Integer.parseInt(input.get("id"));
		} catch (Exception e) {
			map.put("code", "4005");
			map.put("result", "failure");
			map.put("reason", "请输入要修改的部门ID");
			return map;
		}
		
		//先根据id从数据库中获取原来的信息
		Department department=new Department();
		try {
			department = departmentService.getDepartmentById(id);
		} catch (DepartmentException e1) {
			e1.printStackTrace();
		}
		if (department != null) {
			boolean entitled = beforeUpdateAndDelete(loginDept,department);
			if(!entitled) {
				map.put("code", "1000");
				map.put("result", "failure");
				map.put("reason", "您没有此权限进行该操作");
				return map;
			}
			
			//根据传来的信息更改数据库中查出来的信息
			department = beforeUpdateDepartment(department, input);
			int result=0;
			try {
				result = departmentService.updateDepartment(department);
			} catch (DepartmentException e) {
				map.put("code", "4006");
				map.put("result", "failure");
				map.put("reason", "修改部门失败");
				return map;
			}
			if (result == 1) {
				//如果修改成功
				map.put("code", "8000");
				map.put("result", "success");
			} else {
				map.put("code", "4006");
				map.put("result", "failure");
				map.put("reason", "修改部门失败");
			}
		} else {
			//如果传来的id数据库中查不到
			map.put("code", "4007");
			map.put("result", "failure");
			map.put("reason", "您修改的部门不存在");
		}
		
		return map;
	}
	
	/**
	 * @author ishadow
	 * @throws UnsupportedEncodingException 
	 * @date 20160822
	 * @url /rest/department/searchalldepartment
	 */
	@ResponseBody
	@RequestMapping(value = "/searchalldepartment", method = RequestMethod.GET)
	public Map<String, Object> searchalldepartment(/*@PathVariable String name*/){
		Map<String, Object> map = new HashMap<String, Object>();
		List<SimpleDepartment> result=null;
		Department department=null;
		try {
			result=departmentService.selectAllDepartments("");
			//department = departmentService.selectDepByName(new String(name.getBytes("ISO-8859-1"),"GBK"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "参数转码出错！");
		}
		if (result != null) {
//			map.put("code", "8000");
//			map.put("result", "success");
//			map.put("department", department);
			for (SimpleDepartment departmentresult : result) {
				map.put(departmentresult.getName(), departmentresult);
			}
		} else {
			map.put("code", "4003");
			map.put("result", "failure");
			map.put("reason", "查询失败");
		}
		return map;
	}
}
