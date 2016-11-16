package com.nb.org.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentDto;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.domain.Node;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentPermissionService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.util.SNGenerator;
import com.nb.org.vo.DepartmentVO;

/**
 * @ClassName: DepartmentController
 * @Description: 部门管理控制类
 * @author: Naughtior
 * @date:2016年2月21日 下午1:41:32
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Resource
	private IDepartmentService departmentService;

	@Resource
	private IPersonService personService;

	@Resource
	private SNGenerator snGenerator;

	/**
	 * @Title: ChangeDep @Description:
	 *         一个管理员可以管理多个部门，该方法用于改变管理员所管理的当前部门 @param @param id @param @param
	 *         session @param @param model @param @return @return String @throws
	 */
	@RequestMapping("/changedep/{id}")
	public String ChangeDep(@PathVariable(value = "id") int id, HttpSession session, Model model) {
		Person p = (Person) session.getAttribute("user");
		Department department = new Department();
		try {
			department = departmentService.getDepartmentById(id);
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);

		}
		Position position = personService.selectPositionByPerDep(p.getId(), department.getId());
		int adminFlag = position.getAdminFlag();
		session.setAttribute("dep", department);
		session.setAttribute("adminFlag", adminFlag);
		session.setAttribute("tree", null);
		return "index";
	}

	/**
	 * @Title: listDeps @Description: 列出管理员所管理的当前部门的一级子部门。 @param @param
	 *         session @param @param model @param @param
	 *         pageNo_mine @param @param len_mine @param @return @return
	 *         String @throws
	 */
	@RequestMapping("/list")
	public String listDeps(HttpSession session, Model model, @RequestParam(defaultValue = "1") int pageNo_mine,
			@RequestParam(defaultValue = "6") int len_mine) {
		Department department = (Department) session.getAttribute("dep");
		DepartmentVO vo = new DepartmentVO(pageNo_mine, len_mine, department.getId());
		vo = departmentService.listDepsPage(vo);
		model.addAttribute("deplist", DepartmentToDepartmentDto(department, vo.getRows()));
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage = vo.getTotal() % len_mine == 0 ? vo.getTotal() / len_mine : (vo.getTotal() / len_mine + 1);
		model.addAttribute("pageTotal", totalpage);
		model.addAttribute("pageIndex", pageNo_mine);
		return "department/department";
	}

	/**
	 * @Title: DepartmentAddpage @Description: 显示添加部门时的添加页面 @param @param
	 *         session @param @param model @param @return @return String @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String DepartmentAddpage(HttpSession session, Model model) {
		// Person user= (Person) session.getAttribute("user");
		Department department = (Department) session.getAttribute("dep");
		System.out.println(department);
		List<Department> list = new ArrayList<Department>();
		list.add(department);
		list = getChildDeps(list, department);
		model.addAttribute("deplist", list);
		return "department/addDepartment";
	}

	/**
	 * @Title: DepartmentAdd @Description: 执行添加部门操作 @param @param
	 *         pdepname @param @param dep @param @param model @param @param
	 *         session @param @return @return String @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String DepartmentAdd(@RequestParam("pdep") String pdepname, Department dep, Model model,
			HttpSession session) {
		System.out.println(dep);
		Department department = (Department) session.getAttribute("dep");
		Department pdep = null;

		pdep = departmentService.selectDepByFullName(pdepname);
		DepartmentSN sn = snGenerator.generateDepartmentSN(pdep);
		dep.setParentdep(pdep);
		dep.setSn(sn.getBase() + sn.getNumber());
		// dep.setFullname(generateDepartmentFullName(pdepname,dep));
		dep.setFullname(dep.getName());
		try {
			System.out.println("-------------添加----------------");
			departmentService.insertDepartment(dep, sn);
		} catch (DepartmentException e) {
			session.setAttribute("attention", -1);
			e.printStackTrace();
		}
		DepartmentDto dto = DepartmentToDepartmentDto(department, dep);
		model.addAttribute("dep", dto);
		session.setAttribute("attention", 2);
		session.setAttribute("tree", null);
		return "department/departmentDetail";
	}

	/**
	 * @Title: CheckName @Description: 添加部门时检测名称是否存在 @param @param
	 *         username @param @param session @param @return @return
	 *         String @throws
	 */
	@RequestMapping(value = "/checkname", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String CheckUsername(@RequestParam("name") String name, HttpSession session) {
		String json = "true";

		Department department = departmentService.selectDepByFullName(name);
		// System.out.println(person);
		if (department != null) {
			json = "false";
		}
		System.out.println(json);
		return json;
	}

	/**
	 * @Title: DepartmentDtail @Description: 显示部门详情 @param @param
	 *         id @param @param model @param @param
	 *         session @param @return @return String @throws
	 */
	@RequestMapping("/detail/{id}")
	public String DepartmentDtail(@PathVariable(value = "id") int id, Model model, HttpSession session) {
		Department department = (Department) session.getAttribute("dep");
		Department dep = new Department();
		try {
			dep = departmentService.getDepartmentById(id);
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		DepartmentDto dto = DepartmentToDepartmentDto(department, dep);
		model.addAttribute("dep", dto);
		return "department/departmentDetail";
	}

	/**
	 * @Title: DepartmentEdit @Description: 显示编辑部门时的编辑页面 @param @param
	 *         id @param @param session @param @param
	 *         model @param @return @return String @throws
	 */
	@RequestMapping("/edit/{id}")
	public String DepartmentEdit(@PathVariable(value = "id") int id, HttpSession session, Model model) {
		// Person user= (Person) session.getAttribute("user");
		Department department = (Department) session.getAttribute("dep");
		List<Department> list = new ArrayList<Department>();
		Department dep = new Department();
		try {
			dep = departmentService.getDepartmentById(id);
			if (dep.getFullname().equals(department.getFullname())) {
				list.add(dep.getParentdep());
			}
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		list.add(department);
		list = getChildDeps(list, department);
		model.addAttribute("deplist", list);
		DepartmentDto dto = DepartmentToDepartmentDto(department, dep);
		System.out.println(dto);
		model.addAttribute("dep", dto);
		return "department/updateDepartment";
	}

	/**
	 * @Title: DepartmentUpdate @Description: 执行编辑部门操作 @param @param
	 *         pdepname @param @param dep @param @param session @param @param
	 *         model @param @return @return String @throws
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String DepartmentUpdate(@RequestParam("pdep") String pdepname, Department dep, HttpSession session,
			Model model) {
		Department department = (Department) session.getAttribute("dep");
		Department pdep = null;
		pdep = departmentService.selectDepByFullName(pdepname);
		dep.setParentdep(pdep);
		dep.setFullname(dep.getName());
		try {
			departmentService.updateDepartment(dep);
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		Department d = new Department();
		try {
			d = departmentService.getDepartmentById(dep.getId());
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		DepartmentDto dto = DepartmentToDepartmentDto(department, d);
		model.addAttribute("dep", dto);
		session.setAttribute("attention", 3);
		session.setAttribute("tree", null);
		return "department/departmentDetail";
	}

	/**
	 * @Title: DepartmentDelete @Description: 执行删除部门的操作 @param @param
	 *         id @param @param session @param @param
	 *         model @param @return @return String @throws
	 */
	@RequestMapping("/del/{id}")
	public String DepartmentDelete(@PathVariable(value = "id") int id, HttpSession session, Model model) {
		try {
			departmentService.delDepartment(id);
		} catch (DepartmentException e) {
			e.printStackTrace();
			String error = e.getMessage();
			session.setAttribute("attention", -1);
			session.setAttribute("error", error);
		}
		session.setAttribute("attention", 1);
		session.setAttribute("tree", null);
		return "forward:/department/list";
	}

	/**
	 * @Title: searchDepartments @Description:
	 *         根据查询条件查询相应部门，并进行分页显示 @param @param session @param @param
	 *         model @param @param pageNo_mine @param @param
	 *         len_mine @param @param name @param @param
	 *         pdep @param @return @return String @throws
	 */
	@RequestMapping("/search")
	public String searchDepartments(HttpSession session, Model model, @RequestParam(defaultValue = "1") int pageNo_mine,
			@RequestParam(defaultValue = "6") int len_mine, @RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String pdep) {
		Department department = (Department) session.getAttribute("dep");
		Department pDepartment = departmentService.selectDepByFullName(pdep);
		int pdepid = 0;
		DepartmentVO vo = null;
		if (pDepartment != null) {
			pdepid = pDepartment.getId();
		}
		if (departmentService.selectDepByFullName(name) == null)
			vo = new DepartmentVO(pageNo_mine, len_mine, department.getId(), pdepid, name, "");
		else
			vo = new DepartmentVO(pageNo_mine, len_mine, department.getId(), pdepid, "", name);
		vo = departmentService.listDepsPage(vo);
		List<DepartmentDto> departmentDtos = DepartmentToDepartmentDto(department, vo.getRows());
		model.addAttribute("deplist", departmentDtos);
		model.addAttribute("total_mine", vo.getTotal());
		int totalpage = vo.getTotal() % len_mine == 0 ? vo.getTotal() / len_mine : (vo.getTotal() / len_mine + 1);
		model.addAttribute("pageTotal", totalpage);
		model.addAttribute("pageIndex", pageNo_mine);
		model.addAttribute("name", name);
		model.addAttribute("pdep", pdep);
		return "department/searchDepartment";
	}

	/**
	 * @Title: TreeNodeDelete @Description: 从缓存的部门人员树中删除节点 @param @param
	 *         id @param @param session @return void @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/treenodedelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void TreeNodeDelete(@RequestParam("id") int id, HttpSession session) {
		List<Node> tree = (List<Node>) session.getAttribute("tree");
		TreeDeleteNodes(tree, id);
		session.setAttribute("tree", tree);

	}

	@Autowired
	private IDepartmentPermissionService departmentPermissionService;

	/**
	 * @Title: GetTree @Description: 生成部门人员树 @param @param id @param @param
	 *         res @param @param session @return void @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTree")
	@ResponseBody
	public Map<String, Object> GetTree(@RequestParam("id") int id, HttpServletResponse res, HttpSession session) {

		/**
		 * 判断下人员显示权限
		 */
		Person person1 = (Person) session.getAttribute("user");
		Department department1 = (Department) session.getAttribute("dep");
		/**
		 * operateDepId:需要操作的部门id operatorId:操作者id depId:操作者所属部门id
		 */
		boolean entitled1 = false;
		try {
			entitled1 = departmentPermissionService.getDepartmantOperationPermissionWithoutAdmin(id, person1.getId(),department1.getId()) == 1;
//			entitled1 = departmentPermissionService.getDepartmantOperationPermission(id, person1.getId(),department1.getId()) == 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.println(id + "-------------------是一样的值-----------------------------" + department1.getId());
//		if (entitled1) {
//			System.out.println("判断子部门以下---------------------有权限");
//		} else {
//			System.out.println("判断子部门以下---------------------您没有权限进行此操作！！！");
//		}

		Map<String, Object> map = new HashMap<String, Object>();
		res.setCharacterEncoding("utf-8");
		List<Node> tree = (List<Node>) session.getAttribute("tree");
		List<Node> nodes = new ArrayList<Node>();
		int rootId = id;
		map.put("rootId", rootId);
		if (id == 0) {
			id = 1;
			map.put("rootId", 0);
			if (tree == null) {
				Department d = new Department();
				try {
					d = departmentService.getDepartmentById(id);
				} catch (DepartmentException e) {
					e.printStackTrace();
					String error = e.getMessage();
					session.setAttribute("attention", -1);
					session.setAttribute("error", error);
				}
				nodes.add(BuildNodeByDepartment(d, entitled1));
				session.setAttribute("tree", nodes);
			} else
				nodes = tree;
		} else {
			Department d = new Department();
			try {
				d = departmentService.getDepartmentById(id);
			} catch (DepartmentException e) {
				e.printStackTrace();
				String error = e.getMessage();
				session.setAttribute("attention", -1);
				session.setAttribute("error", error);
			}
			nodes = BuildNodeByDepartmentNoPdep(d, entitled1);
			TreeAddNodes(tree, nodes, id);
			session.setAttribute("tree", tree);
		}
		for (Node node : nodes) {
			System.out.println(node);
		}
		/**
		 * 把session全部打出来，希望能得到用户名
		 */
		java.util.Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String sessionName = (String) e.nextElement();
			System.out.println("\nsession item name=" + sessionName);
			System.out.println("\nsession item value=" + session.getAttribute(sessionName));
		}

		map.put("nodes", nodes);

		return map;
	}

	/**
	 * @Title: SearchTree @Description: 根据搜索条件，从部门人员树中搜索出相应的子树 @param @param
	 *         id @param @param keyword @param @param res @param @param
	 *         session @return void @throws
	 */
	@RequestMapping("/searchTree")
	public void SearchTree(@RequestParam("id") int id,
			@RequestParam(value = "keyword", required = false) String keyword, HttpServletResponse res,
			HttpSession session) {
		/**
		 * 判断下人员显示权限
		 */
		Person person1 = (Person) session.getAttribute("user");
		Department department1 = (Department) session.getAttribute("dep");
		/**
		 * operateDepId:需要操作的部门id operatorId:操作者id depId:操作者所属部门id
		 */
		boolean entitled1 = false;
		try {
			entitled1 = departmentPermissionService.getDepartmantOperationPermission(id, person1.getId(),
					department1.getId()) == 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		res.setCharacterEncoding("utf-8");
		List<Node> nodes = new ArrayList<Node>();
		String json = "{\"rootId\":" + 0 + ",\"nodes\":";
		Department d = null;
		if (keyword != null && !keyword.isEmpty()) {
			d = departmentService.selectDepByFullName(keyword);
			System.out.println(d == null);
			if (d != null) {
				/*
				 * upshi 20160303 List<Department>
				 * list=departmentService.selectDepByName(keyword);
				 * if(list!=null && !list.isEmpty()){ for(Department
				 * department:list){
				 * System.out.println("搜索到了同名部门-------》"+department); Node
				 * node=BuildNodeByDepartment(department); nodes.add(node); } }
				 */
				Node node = BuildNodeByDepartment(d,entitled1);

				nodes.add(node);
				/*
				 * } else { List<Person> perlist = new ArrayList<Person>(); try
				 * { perlist = personService.selectPersonsByName(keyword); }
				 * catch (PersonException e) { e.printStackTrace(); String error
				 * = e.getMessage(); session.setAttribute("attention", -1);
				 * session.setAttribute("error", error); } if (perlist != null
				 * && !perlist.isEmpty()) { for (Person p : perlist) {
				 * System.out.println("搜索到了人-------》" + p);
				 * nodes.addAll(BuildNodeByPerson(p)); } } }
				 */
			} else {
				session.setAttribute("error", "未找到查询结果！");
			}

		} else {
			d = departmentService.selectDepByFullName("宁波市");
			nodes.add(BuildNodeByDepartment(d,entitled1));
			session.setAttribute("error", "请输入查询条件！");
		}
		// session.setAttribute("tree",nodes);
		json = json + JSON.toJSONString(nodes) + "}";
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Department> getChildDeps(List<Department> resultlist, Department dep) {
		List<Department> list = new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);
		} catch (DepartmentException e) {
			e.printStackTrace();

		}
		if (!list.isEmpty()) {
			for (Department d : list) {
				resultlist.add(d);
				getChildDeps(resultlist, d);
			}
		}
		return resultlist;
	}

	private String generateDepartmentFullName(String pdepname, Department d) {
		if (d.getName().contains(pdepname)) {
			return d.getName();
		}
		return pdepname + d.getName();
	}

	/**
	 * @Title: BuildNodeByDepartment @Description: 生成完整的树节点： >工商局 >办公室 >城管局
	 *         >... @param @param d @param @return @return Node @throws
	 */
	private Node BuildNodeByDepartment(Department d, boolean permission) {
		Node pNode = new Node();
		if (d != null) {
			List<Department> deplist = new ArrayList<Department>();
			try {
				deplist = departmentService.getDepByParentDep(d);
			} catch (DepartmentException e) {
				e.printStackTrace();
			}
			List<Node> nodes = new ArrayList<Node>();
			if (permission) {
				for (int j = 0; j < d.getPersons().size(); j++) {
					Person p = d.getPersons().get(j);
					Node node = new Node();
					node.setId(p.getId());
					node.setExpanded(true);
					node.setLeaf(true);
					node.setText(p.getName());
					node.setType("staff");
					node.setUrl("person/detail/" + p.getId() + "?depsn=" + d.getSn());
					nodes.add(node);

				}
			}
			for (int i = 0; i < deplist.size(); i++) {
				Department dep = deplist.get(i);
				Node node = new Node();
				node.setId(dep.getId());
				node.setExpanded(false);
				node.setLeaf(false);
				node.setText(dep.getName());
				node.setType("section");
				node.setUrl("department/detail/" + dep.getId());
				nodes.add(node);
			}
			pNode.setId(d.getId());
			pNode.setExpanded(true);
			pNode.setLeaf(false);
			pNode.setNodes(nodes);
			pNode.setText(d.getName());
			pNode.setType("section");
			pNode.setUrl("department/detail/" + d.getId());
		}
		return pNode;
	}

	/**
	 * @Title: BuildNodeByDepartmentNoPdep @Description: 生成不带父节点的树，如工商局下的： >办公室
	 *         >城管局 >... @param @param d @param @return @return List
	 *         <Node> @throws
	 */
	private List<Node> BuildNodeByDepartmentNoPdep(Department d, boolean permisson) {
		List<Node> nodes = new ArrayList<Node>();
		if (d != null) {
			List<Department> deplist = new ArrayList<Department>();
			try {
				deplist = departmentService.getDepByParentDep(d);
			} catch (DepartmentException e) {
				e.printStackTrace();
			}
			if (permisson) {
				for (int j = 0; j < d.getPersons().size(); j++) {
					Person p = d.getPersons().get(j);
					Node node = new Node();
					node.setId(p.getId());
					node.setExpanded(true);
					node.setLeaf(true);
					node.setText(p.getName());
					node.setType("staff");
					node.setUrl("person/detail/" + p.getId() + "?depsn=" + d.getSn());
					nodes.add(node);

				}

			}
			for (int i = 0; i < deplist.size(); i++) {
				Department dep = deplist.get(i);
				Node node = new Node();
				node.setId(dep.getId());
				node.setExpanded(false);
				node.setLeaf(false);
				node.setText(dep.getName());
				node.setType("section");
				node.setUrl("department/detail/" + dep.getId());
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * @Title: BuildNodeByPerson @Description: 生成人的树 @param @param
	 *         p @param @return @return List<Node> @throws
	 */
	private List<Node> BuildNodeByPerson(Person p) {
		List<Node> nodes = new ArrayList<Node>();
		if (p != null) {
			List<Department> deplist = p.getDeps();
			for (Department d : deplist) {
				Node pnode = new Node();
				pnode.setId(p.getId());
				pnode.setExpanded(true);
				pnode.setLeaf(true);
				pnode.setText(p.getName());
				pnode.setType("staff");
				pnode.setUrl("person/detail/" + p.getId() + "?depsn=" + d.getSn());
				List<Node> pnodes = new ArrayList<Node>();
				pnodes.add(pnode);
				Node node = new Node();
				node.setId(d.getId());
				node.setExpanded(true);
				node.setLeaf(false);
				node.setText(d.getName());
				node.setType("section");
				node.setUrl("department/detail/" + d.getId());
				node.setNodes(pnodes);
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * @Title: TreeAddNodes @Description: 往树中添加节点 @param @param
	 *         tree @param @param nodes @param @param id @param @return @return
	 *         List<Node> @throws
	 */
	private List<Node> TreeAddNodes(List<Node> tree, List<Node> nodes, int id) {
		if (tree != null) {
			for (int i = 0; i < tree.size(); i++) {
				Node temp = tree.get(i);
				if (temp.getId() == id) {
					tree.get(i).setNodes(nodes);
					tree.get(i).setExpanded(true);
					return tree;
				}
				if (temp != null) {
					TreeAddNodes(temp.getNodes(), nodes, id);
				}
			}
		}
		return tree;
	}

	/**
	 * @Title: TreeDeleteNodes @Description: 从树中删除节点 @param @param
	 *         tree @param @param id @param @return @return List<Node> @throws
	 */
	private List<Node> TreeDeleteNodes(List<Node> tree, int id) {
		if (tree != null) {
			for (int i = 0; i < tree.size(); i++) {
				Node temp = tree.get(i);
				if (temp.getId() == id) {
					tree.get(i).setNodes(null);
					tree.get(i).setExpanded(false);
					return tree;
				}
				if (temp != null) {
					TreeDeleteNodes(temp.getNodes(), id);
				}
			}
		}
		return tree;
	}

	/**
	 * @Title: DepartmentToDepartmentDto @Description:
	 *         Department对象转换成DepartmentDto对象的方法 @param @param
	 *         department @param @param d @param @return @return
	 *         DepartmentDto @throws
	 */
	private DepartmentDto DepartmentToDepartmentDto(Department department, Department d) {
		String pdepname = "宁波市";
		if (d.getParentdep() != null)
			pdepname = d.getParentdep().getName();
		int isEdit = 0;
		if (d.getSn().contains(department.getSn()))
			isEdit = 1;
		DepartmentDto dto = new DepartmentDto(d.getId(), d.getSn(), d.getName(), d.getFullname(), d.getContactPerson(),
				d.getContactNumber(), d.getAddress(), d.getDescription(), pdepname, isEdit);
		return dto;
	}

	/**
	 * @Title: DepartmentToDepartmentDto @Description:
	 *         Department对象集合转换成DepartmentDto对象集合的方法 @param @param
	 *         department @param @param list @param @return @return List
	 *         <DepartmentDto> @throws
	 */
	private List<DepartmentDto> DepartmentToDepartmentDto(Department department, List<Department> list) {
		List<DepartmentDto> reslist = new ArrayList<DepartmentDto>();
		for (Department d : list) {
			DepartmentDto dto = DepartmentToDepartmentDto(department, d);
			reslist.add(dto);
		}
		return reslist;

	}
}
