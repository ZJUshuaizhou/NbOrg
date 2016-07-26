package com.nb.org.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.PersonDto;
import com.nb.org.domain.Position;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.util.SNGenerator;
import com.nb.org.vo.PersonVO;


/**
 * @ClassName: PersonController
 * @Description: 人员管理控制类
 * @author: Naughtior
 * @date:2016年2月21日 下午1:58:02
 */ 
@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Resource
	private IDepartmentService departmentService;
	
	@Resource
	private IPersonService personService;
	
	@Resource
	private SNGenerator snGenerator;
	
	
	/**
	 * @throws PersonException 
	 * @Title: listPersons
	 * @Description: 分页列出所有的人员
	 * @param @param session
	 * @param @param model
	 * @param @param pageNo_mine
	 * @param @param len_mine
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/list")
	public String listPersons(HttpSession session, Model model,
			@RequestParam(defaultValue="1")int pageNo_mine,@RequestParam(defaultValue="6")int len_mine
			) throws PersonException{
		Department department = (Department) session.getAttribute("dep");
		PersonVO vo = new PersonVO(pageNo_mine, len_mine, department.getId(),"",0);
		vo = personService.listPersonPage(vo);
		List<PersonDto> personDtos=PersonToPersonDto(department,vo.getRows());
		model.addAttribute("perlist",personDtos);
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage=vo.getTotal()%len_mine==0?vo.getTotal()/len_mine:(vo.getTotal()/len_mine+1);
		model.addAttribute("pageTotal", totalpage);
		model.addAttribute("pageIndex", pageNo_mine);
		return "person/person";
	}
	/**
	 * @throws PersonException 
	 * @Title: searchPersons
	 * @Description: 根据查询条件查找相关人员，并分页显示结果
	 * @param @param session
	 * @param @param model
	 * @param @param pageNo_mine
	 * @param @param len_mine
	 * @param @param name
	 * @param @param depname
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/search")
	public String searchPersons(HttpSession session, Model model,
			@RequestParam(defaultValue="1")int pageNo_mine,@RequestParam(defaultValue="6")int len_mine,
			@RequestParam(defaultValue="")String name,@RequestParam(defaultValue="")String depname
			) throws PersonException{
		Department department = (Department) session.getAttribute("dep");
		Department d = departmentService.selectDepByFullName(depname);
		int pdep=0;
		if(d!=null){pdep=d.getId();}
		PersonVO vo = new PersonVO(pageNo_mine, len_mine, department.getId(),name,pdep);
		vo = personService.listPersonPage(vo);
		List<PersonDto> personDtos=new ArrayList<PersonDto>();
		personDtos=SearchPersonToPersonDto(department,d,vo.getRows());
		
		model.addAttribute("perlist",personDtos);
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage=vo.getTotal()%len_mine==0?vo.getTotal()/len_mine:(vo.getTotal()/len_mine+1);
		model.addAttribute("pageTotal", totalpage);
		model.addAttribute("pageIndex", pageNo_mine);
		model.addAttribute("name", name);
		model.addAttribute("depname", depname);
		return "person/searchPerson";
	}
	
	
	/**
	 * @throws PersonException 
	 * @Title: PersonDtail
	 * @Description: 人员详情
	 * @param @param id
	 * @param @param depname
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/detail/{id}")
	public String PersonDtail(@PathVariable(value="id") int id ,@RequestParam("depsn") String depsn,HttpSession session,Model model) throws PersonException{
		Department department = (Department) session.getAttribute("dep");
		Person per=new Person();
		try {
			per = personService.getPersonById(id);
		} catch (PersonException e) {
			session.setAttribute("attention", -1);
			String error=e.getMessage();
			session.setAttribute("error", error);
			//TODO
			
		}
		Department dep = null;
		try {
			dep = departmentService.selectDepBySN(depsn);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PersonDto persondto=PersonToPersonDto(per,dep,department);
		model.addAttribute("per", persondto);
		return "person/personDetail";
	}
	
	/**
	 * @Title: PersonAddPage
	 * @Description: 显示添加人员的添加页面
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/add")
	public String PersonAddPage(HttpSession session,Model model){
		//Person user= (Person) session.getAttribute("user");
		Department department=(Department) session.getAttribute("dep");
		List<Department> list=new ArrayList<Department>();
		list.add(department);
		list=getChildDeps(list,department);
		model.addAttribute("deplist",list);
		return "person/addPerson";
	}
	/**
	 * @Title: PersonAdd
	 * @Description: 执行添加人员操作
	 * @param @param pos
	 * @param @param adminFlag
	 * @param @param deps
	 * @param @param p
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String PersonAdd(@RequestParam(value="pos",defaultValue="科员") String pos,@RequestParam(value="adminFlag",defaultValue="0") int adminFlag,@RequestParam(value="deplist",required=false) String[] deps,Person p,HttpSession session,Model model){
		//Person user= (Person) session.getAttribute("user");
		Department department=(Department) session.getAttribute("dep");
		if(p.getGender()==null){
			p.setGender(2);
		}
		p.setSn(snGenerator.generatePersonSN(department));
		p.setCreateDep(department);
		if(p!=null){
			try {
				personService.insertPerson(p);
				System.out.println(p);
			} catch (PersonException e) {
				session.setAttribute("attention", -1);
				e.printStackTrace();
			}
		}
		String dep="";
		if(deps==null || deps.length==0){
            personService.saveRelativity(p, department, pos, adminFlag);
        } else {
		for(int i=0;i<deps.length;i++){
			System.out.println(deps[i]);
			Department d = null;
			d = departmentService.selectDepByFullName(deps[i]);
			if(d!=null){
				personService.saveRelativity(p, d, pos, adminFlag);
				dep=dep+" "+d.getName();
			}
		}
        }
		
		String gender="不详";
		if(p.getGender()!=null){
		if(p.getGender()==0) gender="男";
		if(p.getGender()==1) gender="女";
		}
		PersonDto persondto=new PersonDto(p.getId(),p.getName(), p.getUsername(),gender, p.getTelephone(), p.getMobilephone(), p.getEmail(), dep, pos);
		model.addAttribute("per", persondto);
		session.setAttribute("attention", 2);
		session.setAttribute("tree", null);
		return "person/personDetail";
	}
	
	/**
	 * @Title: PersonEdit
	 * @Description: 显示编辑人员时的编辑页面
	 * @param @param id
	 * @param @param depname
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/edit/{id}")
	public String PersonEdit(@PathVariable(value="id") int id ,@RequestParam("depsn") String depsn,HttpSession session,Model model){
		//Person user= (Person) session.getAttribute("user");
		Department department=(Department) session.getAttribute("dep");
		List<Department> list=new ArrayList<Department>();
		list.add(department);
		list=getChildDeps(list,department);
		model.addAttribute("editdeplist",list);
		String gender="不详";
		Person per=new Person();
		try {
			per = personService.getPersonById(id);
		} catch (PersonException e) {
			e.printStackTrace();
			String error=e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
			//TODO
		}
		Department dep = null;
		try {
			dep = departmentService.selectDepBySN(depsn);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(per.getGender()==0) gender="男";
		if(per.getGender()==1) gender="女";
		Position pos=personService.selectPositionByPerDep(per.getId(),dep.getId());
		PersonDto persondto=new PersonDto(per.getId(),per.getName(), per.getUsername(),gender, per.getTelephone(), per.getMobilephone(), per.getEmail(), dep.getFullname(), pos.getName());
		model.addAttribute("per", persondto);
		model.addAttribute("adminFlag", pos.getAdminFlag());
		return "person/updatePerson";
	}
	/**
	 * @Title: PersonUpdate
	 * @Description: 执行人员编辑更新操作
	 * @param @param pos
	 * @param @param adminFlag
	 * @param @param dep
	 * @param @param predep
	 * @param @param p
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public String PersonUpdate(@RequestParam("pos") String pos,@RequestParam(value="adminFlag",defaultValue="0") int adminFlag,@RequestParam("deplist") String dep,@RequestParam("predep") String predep,Person p,HttpSession session,Model model){
			Department d = null;
			d = departmentService.selectDepByFullName(dep);
			Department pred = null;
			pred = departmentService.selectDepByFullName(predep);
			try {
				personService.updatePersonInfo(p);
			} catch (PersonException e) {
				e.printStackTrace();
				String error=e.getMessage();
				session.setAttribute("attention", -1);
				session.setAttribute("error", error);
				//TODO
			}
			personService.delRelativity(p, pred);
			personService.saveRelativity(p, d, pos, adminFlag);
		String gender="不详";
		if(p.getGender()!=null){
		if(p.getGender()==0) gender="男";
		if(p.getGender()==1) gender="女";
		}
		PersonDto persondto=new PersonDto(p.getId(),p.getName(),p.getUsername(), gender, p.getTelephone(), p.getMobilephone(), p.getEmail(), dep, pos);
		model.addAttribute("per", persondto);
		session.setAttribute("attention",3);
		session.setAttribute("tree", null);
		return "person/personDetail";
	}
	/**
	 * @Title: PersonDelete
	 * @Description: 执行删除人员操作
	 * @param @param id
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/del/{id}")
	public String PersonDelete(@PathVariable(value="id") int id,HttpSession session,Model model){
		try {
			personService.delPerson(id);
		} catch (PersonException e) {
			e.printStackTrace();
			String error=e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		session.setAttribute("attention", 1);
		session.setAttribute("tree", null);
		return "forward:/person/list";
	}
	/**
	 * @Title: RestPassword
	 * @Description: 重置密码操作
	 * @param @param id
	 * @param @param session
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/restpassword/{id}")
	public String RestPassword(@PathVariable(value="id") int id ,HttpSession session,Model model){
		//Person user= (Person) session.getAttribute("user");
		Person person=new Person();
		try {
			person = personService.getPersonById(id);
			session.setAttribute("attention", 4);
		} catch (PersonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			String error=e1.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		if(person!=null){
		try {
			personService.resetPaasword(person.getUsername());
		} catch (Exception e) {
			session.setAttribute("attention", -1);
			e.printStackTrace();
		}
		}
		return "redirect:/person/list";
	}
	
	/**
	 * @Title: CheckUsername
	 * @Description: 添加人员时检测用户名是否存在
	 * @param @param username
	 * @param @param session
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/checkusername",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String CheckUsername(@RequestParam("username") String username,HttpSession session){
		String json="true";
		try {
			Person person=personService.getPersonByUserName(username);
			System.out.println(person);
			if(person!=null){
				json="false";
			}
		} catch (PersonException e) {
			session.setAttribute("attention", -1);
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}

	/**
	 * @Title: getChildPersons
	 * @Description: 查找该部门下的所有人员（包括子部门的人员）
	 * @param @param resultlist
	 * @param @param dep
	 * @param @return
	 * @return List<Person>
	 * @throws
	 */
	private List<Person> getChildPersons(List<Person> resultlist,Department dep ){
		List<Department> list=new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dep.getPersons()!=null)
		resultlist.addAll(dep.getPersons());
		if(!list.isEmpty()){
		for(Department d:list){
		getChildPersons(resultlist,d);	
		}
		}
		HashSet<Person> temp=new HashSet<Person>(resultlist);
		resultlist.clear();
		resultlist.addAll(temp);
		return resultlist;
	}
	/**
	 * @Title: getChildDeps
	 * @Description: 查找该部门下的所有子部门
	 * @param @param resultlist
	 * @param @param dep
	 * @param @return
	 * @return List<Department>
	 * @throws
	 */
	private  List<Department> getChildDeps(List<Department> resultlist,Department dep ){
		List<Department> list=new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!list.isEmpty()){
		for(Department d:list){
		resultlist.add(d);
		 getChildDeps(resultlist,d);	
		}
		}
		return resultlist;
	}
	/**
	 * @throws PersonException 
	 * @Title: PersonToPersonDto
	 * @Description: person对象转换成personDto对象的方法
	 * @param @param p
	 * @param @param d
	 * @param @param department
	 * @param @return
	 * @return PersonDto
	 * @throws
	 */
	private PersonDto PersonToPersonDto(Person p,Department d,Department department) throws PersonException {
		if(p==null||d==null) return null;
		String gender="不详";
		if(p.getGender()==0) gender="男";
		if(p.getGender()==1) gender="女";
		Position pos=personService.selectPositionByPerDep(p.getId(), d.getId());
		int isEdit=0;
		int length=d.getSn().length();
		String string=d.getSn();
		List<Department> list=personService.getPersonById(p.getId()).getDeps();
		PersonDto persondto=new PersonDto();
		for(Department department2:list ){
		    if(department2.getSn().length()<length) {
		        string=department2.getSn();
		        length=department2.getSn().length();
		    }
		}
		if(string.contains(department.getSn())) { isEdit=1;}
		persondto=new PersonDto(p.getId(),p.getName(), p.getUsername(),gender, p.getTelephone(), p.getMobilephone(), p.getEmail(), d.getFullname(), pos.getName(),isEdit,d.getSn());
		return persondto;
}
	 /**
	 * @throws PersonException 
	 * @Title: PersonToPersonDto
	 * @Description: person集合对象转换成personDto集合对象的方法
	 * @param @param department
	 * @param @param list
	 * @param @return
	 * @return List<PersonDto>
	 * @throws
	 */
	private List<PersonDto> PersonToPersonDto(Department department,List<Person> list) throws PersonException{
		 List<PersonDto> reslist=new ArrayList<PersonDto>();
		 for(Person person :list){
			 for(Department d:person.getDeps()){
				 if(d.getId()==department.getId()){
			 PersonDto dto= PersonToPersonDto(person,d,department);
				reslist.add(dto);
				 }
			 }
			}
			return reslist;
			
		}
	 /**
	 * @throws PersonException 
	 * @Title: SearchPersonToPersonDto
	 * @Description: 查找时的person集合对象转换成personDto集合对象的方法
	 * @param @param department
	 * @param @param dep
	 * @param @param list
	 * @param @return
	 * @return List<PersonDto>
	 * @throws
	 */
	private List<PersonDto> SearchPersonToPersonDto(Department department,Department dep,List<Person> list) throws PersonException{
		 List<PersonDto> reslist=new ArrayList<PersonDto>();
		 int samePersonId=0;
		 for(Person person :list){
			 samePersonId++;
			 for(Department d:person.getDeps()){
				 if(dep==null||d.getId()==dep.getId()){
					 PersonDto dto= PersonToPersonDto(person,d,department);
					 dto.setSamePersonId(samePersonId);
						reslist.add(dto);
						 }
			 }
			}
			return reslist;
			
		}
}
