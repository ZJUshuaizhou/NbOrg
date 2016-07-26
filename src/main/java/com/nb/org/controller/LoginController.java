package com.nb.org.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.PersonDto;
import com.nb.org.domain.Position;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IPersonService;

@Controller
public class LoginController {
	@Resource
	private IPersonService personService;
	
	@RequestMapping("loginreturn") 
	public String loginReturn(HttpSession session){
		// 取返回的用户名
		String subject = null;
		if (session.getAttribute(SSOAgentConfigs.getSessionBeanName()) != null) {
			if (((SSOAgentSessionBean) session.getAttribute(SSOAgentConfigs.getSessionBeanName()))
					.getSAMLSSOSessionBean() != null) {
				subject = ((SSOAgentSessionBean) session.getAttribute(SSOAgentConfigs.getSessionBeanName()))
						.getSAMLSSOSessionBean().getSubjectId();
				System.out.println(subject);
				session.setAttribute("subject", subject);
				// 根据用户名取用户 Person
				if(subject!=null){
					try {
						Person person = personService.getPersonByUserName(subject);
						//获取用户的所属部门
						Department department = person.getDeps().get(0);
						//获取用户的职位
						Position position = personService.selectPositionByPerDep(person.getId(), department.getId());
						//判断用户是否为管理员
						int adminFlag = position.getAdminFlag();
						//将用户、部门、职位、是否为管理员村session
						session.setAttribute("user", person);
						session.setAttribute("dep", department);
						session.setAttribute("adminFlag", adminFlag);
						/*-1：表示操作出错；
						 * 0:表示没有弹框；
						 * 1：表示删除成功；
						 * 2：表示添加成功；
						 * 3：表示更新成功；
						 * 4：表示重置密码成功；
						 */
						session.setAttribute("attention", 0);
						session.setAttribute("tree",null);
					} catch (PersonException e) {
						e.printStackTrace();
					}
				}			
				return "index";
				
			} else {
				return "redirect:login.jsp";
			}
		} else {
			return "redirect:login.jsp";
		}
		
	}
	
	@RequestMapping("logout")
	public String logout(){
		return "redirect:login.jsp";
	}
	@RequestMapping("/index")
	public String Index(){
		return "index";
	}
	@RequestMapping("permissionDeny")
	public String permissionDeny(){
		return "permissionDeny";
	}
	@RequestMapping("userinfo")
	public String UserInfo(HttpSession session,Model model){
		Department department=(Department) session.getAttribute("dep");
		Person person=(Person) session.getAttribute("user");
		PersonDto dto=PersonToPersonDto(person,department,department);
		model.addAttribute("per", dto);
		return "userinfo";
	}
	@RequestMapping("usersetting")
	public String UserSetting(@RequestParam(required = true, defaultValue = "true") boolean usersetting,HttpSession session,Model model){
		Department department=(Department) session.getAttribute("dep");
		Person person=(Person) session.getAttribute("user");
		PersonDto dto=PersonToPersonDto(person,department,department);
		model.addAttribute("per", dto);
		model.addAttribute("usersetting", usersetting);
		return "userSetting";
	}
	@RequestMapping(value="updateuserinfo",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String UserInfoUpdate(Person person,HttpSession session,Model model){
		Department department=(Department) session.getAttribute("dep");
		try {
			personService.updatePersonInfo(person);
			session.setAttribute("attention", 3);
		} catch (PersonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("attention", -1);
		}
		PersonDto dto=PersonToPersonDto(person,department,department);
		model.addAttribute("per", dto);
		try {
			Person p=personService.getPersonById(person.getId());
			session.setAttribute("user", p);
		} catch (PersonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "userinfo";
	}
	@RequestMapping(value="changepsw",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String ChangePsw(@RequestParam("oldpsw")String oldpsw,@RequestParam("newpsw")String newpsw,@RequestParam("confirm_newpsw")String confirm_newpsw,HttpSession session,Model model){
		Person person=(Person) session.getAttribute("user");
		if(newpsw.equals(confirm_newpsw)){
			try {
				personService.changePassword(person.getUsername(), newpsw, oldpsw);
				session.setAttribute("attention", 5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute("attention", -1);
			}
		}
		return "redirect:/usersetting?usersetting=false";
	}
	private PersonDto PersonToPersonDto(Person p,Department d,Department department) {
		if(p==null||d==null) return null;
		String gender="不详";
		if(p.getGender()==0) gender="男";
		if(p.getGender()==1) gender="女";
		Position pos=personService.selectPositionByPerDep(p.getId(), d.getId());
		int isEdit=0;
		PersonDto persondto=new PersonDto();
		if(d.getSn().contains(department.getSn())) { isEdit=1;}
		persondto=new PersonDto(p.getId(),p.getName(), p.getUsername(),gender, p.getTelephone(), p.getMobilephone(), p.getEmail(), d.getFullname(), pos.getName(),isEdit);
		return persondto;
}
}
