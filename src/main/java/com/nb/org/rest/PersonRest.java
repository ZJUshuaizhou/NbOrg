package com.nb.org.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IPersonService;
import com.nb.org.service.IPositionService;
import com.nb.org.util.SNGenerator;
import com.nb.org.util.StringUtil;
import com.nb.org.vo.PersonRestVO;

/**
 * @author upshi
 * @date 20160120 请求头部参数 departmentSN:部门全称 (Base64编码)
 */
@Controller
@RequestMapping("/rest/person")
public class PersonRest {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private SNGenerator snGenerate;
	
	@Autowired
	private IPositionService positionService;

	/**
	 * @author upshi
	 * @date 20160120
	 * @url /rest/person/add
	 * 需要的请求体json格式：{"name":"","username":"","gender":"","telephone":"","mobilephone":"","email":"","position":"","adminFlag":""}
	 */
	@ResponseBody
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Map<String, Object> add(@RequestBody PersonRestVO personRestVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//设置该人员的所在部门,从request域里取得(拦截器中设置的)
		Department dept = (Department) request.getAttribute("departmentName");
		//校验必填信息是否为空
		if(StringUtil.isNoE(personRestVO.getName())) {
			map.put("code", "3006");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的姓名");
			return map;
		}
		if(StringUtil.isNoE(personRestVO.getUsername())) {
			map.put("code", "3007");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的用户名");
			return map;
		}
		if(personRestVO.getGender() == null || (personRestVO.getGender() != 0 && personRestVO.getGender() != 1) ) {
			map.put("code", "3008");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的性别;0:男,1:女");
			return map;
		}
		if(StringUtil.isNoE(personRestVO.getPosition())) {
			map.put("code", "3009");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的职位");
			return map;
		}
		if(personRestVO.getAdminFlag() == null || (personRestVO.getAdminFlag() != 0 && personRestVO.getAdminFlag() != 1) ) {
			map.put("code", "3010");
			map.put("result", "failure");
			map.put("reason", "请设置用户是否为管理员;0:否,1:是");
			return map;
		}
		
		try {
			Person personByUserName = personService.getPersonByUserName(personRestVO.getUsername());
			if(personByUserName != null) {
				map.put("code", "3015");
				map.put("result", "failure");
				map.put("reason", "用户名已经存在");
				return map;
			}
		} catch (PersonException e1) {
			map.put("code", "3000");
			map.put("result", "failure");
			map.put("reason", "新增用户失败");
			return map;
		}
		
		Person person = new Person();
		person.setName(personRestVO.getName());
		person.setUsername(personRestVO.getUsername());
		person.setGender(personRestVO.getGender());
		person.setTelephone(personRestVO.getTelephone());
		person.setMobilephone(personRestVO.getMobilephone());
		person.setEmail(personRestVO.getEmail());
		//设置按规则生成的SN
		person.setSn(snGenerate.generatePersonSN(dept));
		person.setCreateDep(dept);
		person.getDeps().add(dept);
		try {
			personService.savePerson(person, dept, personRestVO.getPosition(), personRestVO.getAdminFlag());
		} catch (PersonException e) {
			map.put("code", "3000");
			map.put("result", "failure");
			map.put("reason", "新增用户失败");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160120
	 * @url /rest/person/searchByName/{name}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchByName/{name}", method = RequestMethod.GET)
	public Map<String, Object> searchByName(@PathVariable String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Person> persons = null;
		try {
			persons = personService.selectPersonsByName(new String(name.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (PersonException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (persons != null && persons.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("persons", persons);
		} else {
			map.put("code", "3001");
			map.put("result", "failure");
			map.put("reason", "您查询的用户不存在");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @throws UnsupportedEncodingException 
	 * @date 20160317
	 * @url /rest/person/token/searchByUserName/{userName}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchByUserName/{userName}", method = RequestMethod.GET)
	public Map<String, Object> searchByUserName(@PathVariable String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		Person person = null;
		try {
			person = personService.getPersonByUserName(new String(userName.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (PersonException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (person != null) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("person", person);
		} else {
			map.put("code", "3001");
			map.put("result", "failure");
			map.put("reason", "您查询的用户不存在");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @date 20160303
	 * @url /rest/person/searchPosition/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPosition/{id}", method = RequestMethod.GET)
	public Map<String, Object> searchPosition(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3001");
				map.put("result", "failure");
				map.put("reason", "您查询的用户不存在");
				return map;
			}
			if (person != null) {
				List<Position> positions = positionService.selectPositionsByPersonId(person.getId());
				System.out.println(positions);
				map.put("code", "8000");
				map.put("result", "success");
				map.put("positions", positions);
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3001");
				map.put("result", "failure");
				map.put("reason", "您查询的用户不存在");
			}
		} else {
			map.put("code", "3014");
			map.put("result", "failure");
			map.put("reason", "请输入要查询的用户的ID");
		}
		return map;
	}

	/**
	 * @author upshi
	 * @date 20160120
	 * @url /rest/person/delete/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Person person = null;
		try {
			person = personService.getPersonById(id);
		} catch (PersonException e1) {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
			return map;
		}
		
		if(person == null) {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
			return map;
		}
		
		//从request域中拿到用户登录的部门
		Department dept = (Department) request.getAttribute("departmentName");
		boolean entitled = beforeUpdateAndDelete(person, dept);
		if(!entitled) {
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}
		
		int result = 0;
		try {
			result = personService.delPerson(id);
		} catch (PersonException e) {
			e.printStackTrace();
		}
		if (result == 1) {
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
		}
		return map;
	}

	/**
	 * @author upshi
	 * @throws PersonException 
	 * @date 20160120
	 * @url /rest/person/update
	 * 需要的请求体json格式：{"id":"","name":"","gender":"","telephone":"","mobilephone":"","email":""}
	 */
	@ResponseBody
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody Person input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3003");
				map.put("result", "failure");
				map.put("reason", "修改用户失败");
				return map;
			}
			if (person != null) {
				//判断登录的人能否修改这个人,根据登录的部门和该人是否是该部门的人。
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				//根据传来的信息更改数据库中查出来的信息
				person = beforeUpdatePerson(person, input);
				
				if(input.getGender() != 0 && input.getGender() != 1) {
					map.put("code", "3016");
					map.put("result", "failure");
					map.put("reason", "性别输入有误；0:男,1:女");
					return map;
				}
				
				int result = personService.updatePersonInfo(person);
				if (result == 1) {
					//如果修改成功
					map.put("code", "8000");
					map.put("result", "success");
				} else {
					map.put("code", "3003");
					map.put("result", "failure");
					map.put("reason", "修改用户失败");
				}
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @throws PersonException 
	 * @date 20160224
	 * @url /rest/person/updatePosiion
	 * 需要的请求体json格式：{"id":"","position":"","adminFlag":""}
	 */
	@ResponseBody
	@RequestMapping(value="/updatePosiion", method = RequestMethod.PUT)
	public Map<String, Object> updatePosiion(@RequestBody PersonRestVO input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		//从request中获取操作者管理的部门
		Department department = (Department) request.getAttribute("departmentName");
		//查询position判断要修改的用户是否属于操作者管理的部门
		Position position = positionService.selectPositionByPerDep(id, department.getId());
		if(position == null) {
			map.put("code", "3011");
			map.put("result", "failure");
			map.put("reason", "该用户不属于您目前管理的部门");
			return map;
		}
		
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3013");
				map.put("result", "failure");
				map.put("reason", "修改用户职务失败");
				return map;
			}
			if (person != null) {
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				if(StringUtil.isNoE(input.getPosition())) {
					input.setPosition(position.getName());
				}
				if(input.getAdminFlag() == null) {
					input.setAdminFlag(position.getAdminFlag());
				} else if(input.getAdminFlag() != 0 && input.getAdminFlag() != 1) {
					map.put("code", "3010");
					map.put("result", "failure");
					map.put("reason", "请设置用户是否为管理员;0:否,1:是");
					return map;
				}
				personService.updateRelativity(person, department, input.getPosition(), input.getAdminFlag());
				map.put("code", "8000");
				map.put("result", "success");
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
	
	/**
	 * @author upshi
	 * @throws PersonException 
	 * @date 20160224
	 * @url /rest/person/resetPassword
	 * 需要的请求体json格式：{"id":""}
	 */
	@ResponseBody
	@RequestMapping(value="/resetPassword", method = RequestMethod.PUT)
	public Map<String, Object> resetPassword(@RequestBody PersonRestVO input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		//从request中获取操作者管理的部门
		Department department = (Department) request.getAttribute("departmentName");
		//查询position判断要修改的用户是否属于操作者管理的部门
		Position position = positionService.selectPositionByPerDep(id, department.getId());
		if(position == null) {
			map.put("code", "3011");
			map.put("result", "failure");
			map.put("reason", "该用户不属于您目前管理的部门");
			return map;
		}
		
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
				return map;
			}
			if (person != null) {
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				try {
					personService.resetPaasword(person.getUsername());
					map.put("code", "8000");
					map.put("result", "success");
				} catch (Exception e) {
					map.put("code", "3012");
					map.put("result", "failure");
					map.put("reason", "重置用户密码失败");
				}
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
	
	

	/**
	 * @author upshi
	 * @date 20160120
	 * @param old
	 *            数据库中查出的记录
	 * @param input
	 *            用户传递过来的记录
	 * @return 更新的记录
	 */
	private Person beforeUpdatePerson(Person old, Person input) {
		if (!StringUtil.isNoE(input.getName())) {
			old.setName(input.getName());
		}
		if (input.getGender() != null) {
			old.setGender(input.getGender());
		}
		if (!StringUtil.isNoE(input.getTelephone())) {
			old.setTelephone(input.getTelephone());
		}
		if (!StringUtil.isNoE(input.getMobilephone())) {
			old.setMobilephone(input.getMobilephone());
		}
		if (!StringUtil.isNoE(input.getEmail())) {
			old.setEmail(input.getEmail());
		}
		return old;
	}
	
	/*
	 * 20160305
	 * 在执行修改和删除操作前判断，当前被操作人是否属于   操作人员登录的部门(权限范围内的)
	 * */
	private boolean beforeUpdateAndDelete(Person person, Department dept) {
		boolean entitled = false; 
		for(Department d : person.getDeps()) {
			Department temp = d;
			do {
				if(dept.getName().equals(temp.getName())) {
					entitled = true;
					break;
				}
				temp = temp.getParentdep();
			} while (temp != null);
			if(entitled) {
				break;
			}
		}
		return entitled;
	}
	
	
	
	/**
	 * 从此处以下是旧版api，为了兼容其支持，但不建议使用***********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 * * 从此处以下是旧版api，为了兼容其支持，但不建议使用*********************************************************************************************************************
	 */
	
	/**
	 *  ishadow
	 * 旧版添加人员
	 * @param personRestVO
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> oldAdd(@RequestBody PersonRestVO personRestVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//设置该人员的所在部门,从request域里取得(拦截器中设置的)
		Department dept = (Department) request.getAttribute("departmentName");
		//校验必填信息是否为空
		if(StringUtil.isNoE(personRestVO.getName())) {
			map.put("code", "3006");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的姓名");
			return map;
		}
		if(StringUtil.isNoE(personRestVO.getUsername())) {
			map.put("code", "3007");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的用户名");
			return map;
		}
		if(personRestVO.getGender() == null || (personRestVO.getGender() != 0 && personRestVO.getGender() != 1) ) {
			map.put("code", "3008");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的性别;0:男,1:女");
			return map;
		}
		if(StringUtil.isNoE(personRestVO.getPosition())) {
			map.put("code", "3009");
			map.put("result", "failure");
			map.put("reason", "请输入新增用户的职位");
			return map;
		}
		if(personRestVO.getAdminFlag() == null || (personRestVO.getAdminFlag() != 0 && personRestVO.getAdminFlag() != 1) ) {
			map.put("code", "3010");
			map.put("result", "failure");
			map.put("reason", "请设置用户是否为管理员;0:否,1:是");
			return map;
		}
		
		try {
			Person personByUserName = personService.getPersonByUserName(personRestVO.getUsername());
			if(personByUserName != null) {
				map.put("code", "3015");
				map.put("result", "failure");
				map.put("reason", "用户名已经存在");
				return map;
			}
		} catch (PersonException e1) {
			map.put("code", "3000");
			map.put("result", "failure");
			map.put("reason", "新增用户失败");
			return map;
		}
		
		Person person = new Person();
		person.setName(personRestVO.getName());
		person.setUsername(personRestVO.getUsername());
		person.setGender(personRestVO.getGender());
		person.setTelephone(personRestVO.getTelephone());
		person.setMobilephone(personRestVO.getMobilephone());
		person.setEmail(personRestVO.getEmail());
		//设置按规则生成的SN
		person.setSn(snGenerate.generatePersonSN(dept));
		person.setCreateDep(dept);
		person.getDeps().add(dept);
		try {
			personService.savePerson(person, dept, personRestVO.getPosition(), personRestVO.getAdminFlag());
		} catch (PersonException e) {
			map.put("code", "3000");
			map.put("result", "failure");
			map.put("reason", "新增用户失败");
			return map;
		}
		map.put("code", "8000");
		map.put("result", "success");
		return map;
	}

	/**
	 * @author ishadow
	 * @date 20160819
	 * @url /rest/person/{name}
	 */
	@ResponseBody
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Map<String, Object> oldSearchByName(@PathVariable String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Person> persons = null;
		try {
			persons = personService.selectPersonsByName(new String(name.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (PersonException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (persons != null && persons.size() != 0) {
			map.put("code", "8000");
			map.put("result", "success");
			map.put("persons", persons);
		} else {
			map.put("code", "3001");
			map.put("result", "failure");
			map.put("reason", "您查询的用户不存在");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @date 20160819
	 * @url /rest/person/position/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/position/{id}", method = RequestMethod.GET)
	public Map<String, Object> oldSearchPosition(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3001");
				map.put("result", "failure");
				map.put("reason", "您查询的用户不存在");
				return map;
			}
			if (person != null) {
				List<Position> positions = positionService.selectPositionsByPersonId(person.getId());
				System.out.println(positions);
				map.put("code", "8000");
				map.put("result", "success");
				map.put("positions", positions);
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3001");
				map.put("result", "failure");
				map.put("reason", "您查询的用户不存在");
			}
		} else {
			map.put("code", "3014");
			map.put("result", "failure");
			map.put("reason", "请输入要查询的用户的ID");
		}
		return map;
	}

	/**
	 * @author ishadow
	 * @date 20160819
	 *旧版删除person
	 * @url /rest/person/{id}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> oldDelete(@PathVariable Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Person person = null;
		try {
			person = personService.getPersonById(id);
		} catch (PersonException e1) {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
			return map;
		}
		
		if(person == null) {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
			return map;
		}
		
		//从request域中拿到用户登录的部门
		Department dept = (Department) request.getAttribute("departmentName");
		boolean entitled = beforeUpdateAndDelete(person, dept);
		if(!entitled) {
			map.put("code", "1000");
			map.put("result", "failure");
			map.put("reason", "您没有此权限进行该操作");
			return map;
		}
		
		int result = 0;
		try {
			result = personService.delPerson(id);
		} catch (PersonException e) {
			e.printStackTrace();
		}
		if (result == 1) {
			map.put("code", "8000");
			map.put("result", "success");
		} else {
			map.put("code", "3002");
			map.put("result", "failure");
			map.put("reason", "您删除的用户不存在");
		}
		return map;
	}

	/**
	 * @author ishadow
	 * @throws PersonException 
	 * @date 20160819
	 * 旧版更新person
	 * @url /rest/person/update
	 * 需要的请求体json格式：{"id":"","name":"","gender":"","telephone":"","mobilephone":"","email":""}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> oldUpdate(@RequestBody Person input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3003");
				map.put("result", "failure");
				map.put("reason", "修改用户失败");
				return map;
			}
			if (person != null) {
				//判断登录的人能否修改这个人,根据登录的部门和该人是否是该部门的人。
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				//根据传来的信息更改数据库中查出来的信息
				person = beforeUpdatePerson(person, input);
				
				if(input.getGender() != 0 && input.getGender() != 1) {
					map.put("code", "3016");
					map.put("result", "failure");
					map.put("reason", "性别输入有误；0:男,1:女");
					return map;
				}
				
				int result = personService.updatePersonInfo(person);
				if (result == 1) {
					//如果修改成功
					map.put("code", "8000");
					map.put("result", "success");
				} else {
					map.put("code", "3003");
					map.put("result", "failure");
					map.put("reason", "修改用户失败");
				}
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @throws PersonException 
	 * @date 20160819
	 * 旧版更新position
	 * @url /rest/person/updatePosiion
	 * 需要的请求体json格式：{"id":"","position":"","adminFlag":""}
	 */
	@ResponseBody
	@RequestMapping(value="/position", method = RequestMethod.PUT)
	public Map<String, Object> oldUpdatePosiion(@RequestBody PersonRestVO input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		//从request中获取操作者管理的部门
		Department department = (Department) request.getAttribute("departmentName");
		//查询position判断要修改的用户是否属于操作者管理的部门
		Position position = positionService.selectPositionByPerDep(id, department.getId());
		if(position == null) {
			map.put("code", "3011");
			map.put("result", "failure");
			map.put("reason", "该用户不属于您目前管理的部门");
			return map;
		}
		
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3013");
				map.put("result", "failure");
				map.put("reason", "修改用户职务失败");
				return map;
			}
			if (person != null) {
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				if(StringUtil.isNoE(input.getPosition())) {
					input.setPosition(position.getName());
				}
				if(input.getAdminFlag() == null) {
					input.setAdminFlag(position.getAdminFlag());
				} else if(input.getAdminFlag() != 0 && input.getAdminFlag() != 1) {
					map.put("code", "3010");
					map.put("result", "failure");
					map.put("reason", "请设置用户是否为管理员;0:否,1:是");
					return map;
				}
				personService.updateRelativity(person, department, input.getPosition(), input.getAdminFlag());
				map.put("code", "8000");
				map.put("result", "success");
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
	
	/**
	 * @author ishadow
	 * @throws PersonException 
	 * @date 20160819
	 * 旧版重置密码的接口
	 * @url /rest/person/resetPassword
	 * 需要的请求体json格式：{"id":""}
	 */
	@ResponseBody
	@RequestMapping(value="/password", method = RequestMethod.PUT)
	public Map<String, Object> oldResetPassword(@RequestBody PersonRestVO input, HttpServletRequest request) throws PersonException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = input.getId();
		//从request中获取操作者管理的部门
		Department department = (Department) request.getAttribute("departmentName");
		//查询position判断要修改的用户是否属于操作者管理的部门
		Position position = positionService.selectPositionByPerDep(id, department.getId());
		if(position == null) {
			map.put("code", "3011");
			map.put("result", "failure");
			map.put("reason", "该用户不属于您目前管理的部门");
			return map;
		}
		
		if (id != null) {
			//先根据id从数据库中获取原来的信息
			Person person = null;
			try {
				person = personService.getPersonById(id);
			} catch (PersonException e) {
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
				return map;
			}
			if (person != null) {
				
				//从request域中拿到用户登录的部门
				Department dept = (Department) request.getAttribute("departmentName");
				boolean entitled = beforeUpdateAndDelete(person, dept);
				if(!entitled) {
					map.put("code", "1000");
					map.put("result", "failure");
					map.put("reason", "您没有此权限进行该操作");
					return map;
				}
				
				try {
					personService.resetPaasword(person.getUsername());
					map.put("code", "8000");
					map.put("result", "success");
				} catch (Exception e) {
					map.put("code", "3012");
					map.put("result", "failure");
					map.put("reason", "重置用户密码失败");
				}
			} else {
				//如果传来的id数据库中查不到
				map.put("code", "3004");
				map.put("result", "failure");
				map.put("reason", "您修改的用户不存在");
			}
		} else {
			map.put("code", "3005");
			map.put("result", "failure");
			map.put("reason", "请输入您要修改的用户的ID");
		}
		return map;
	}
}
