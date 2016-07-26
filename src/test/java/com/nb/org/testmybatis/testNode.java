package com.nb.org.testmybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.nb.org.domain.Node;
import com.nb.org.domain.Person;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.vo.PersonVO;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class testNode {
	private static Logger logger = Logger.getLogger(TestPersonDao.class);
//	private ApplicationContext ac = null;
	@Resource
	private IPersonService personService = null;
	
	@Resource
	private IDepartmentService departmentService = null;

	@Test
	public void test() {
		Node node=new Node();
		node.setId(1);
		node.setLeaf(false);
		node.setText("宁波市");
		node.setType("section");
		Node node1=new Node();
		node1.setId(2);
		node1.setLeaf(true);
		node1.setText("宁波市");
		node1.setType("section");
		List<Node> nodes=new ArrayList<Node>();
		nodes.add(node);
		nodes.add(node1);
		Node node3=new Node();
		node3.setId(1);
		node3.setLeaf(false);
		node3.setText("宁波市");
		node3.setType("section");
		node3.setNodes(nodes);
		String json=JSON.toJSONString(node3);
		System.out.println(json);
	}
	@Test
	public void testListPersonPage() {
		PersonVO vo=new PersonVO(1, 3, 2,"测试人员2", 0);
		List<Person> persons=personService.listPersonPage(vo).getRows();
		for(Person p:persons){
			System.out.println(p);
		}
		
		
	}

}
