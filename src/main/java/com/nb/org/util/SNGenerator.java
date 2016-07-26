package com.nb.org.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.domain.Person;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IDepartmentSNService;
import com.nb.org.service.IDepartmentService;


/*
 * 所有对象的SN生成器
 * @author upshi
 * @date 20160119
 */
@Component("snGenerate")
public class SNGenerator {
	
	@Autowired
	private IDepartmentSNService departmentSNService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	
	/*
	 * @author upshi
	 * @date 20160120
	 * 根据父部门生成Department的SN
	 */
	public DepartmentSN generateDepartmentSN(Department parentDept) {
		//先查询数据库中该部门是否有之前被删除的子部门的可用编号
		DepartmentSN departmentSN = departmentSNService.selectByParentDeptIdAndFlag(parentDept.getId(), 1);
		//判读是否为空
		if(departmentSN == null) {
			//如果为空,则继续查询该父部门下子部门编号的最大值,即flag为0的记录
			departmentSN = departmentSNService.selectByParentDeptIdAndFlag(parentDept.getId(), 0);
			//继续判断是否为空
			if(departmentSN == null) {
				//如果为空,说明该父部门原来是叶子节点,第一次在该部门下创建子部门,则新建一个parentDept对象,返回
				//########如果部门新建成功,需要在DepartmentServiceImpl中将该对象insert到数据库中(通过判断id==null和flag=0来确定是insert操作)
				departmentSN = new DepartmentSN(parentDept.getId(),"00",parentDept.getSn(),0);
				return departmentSN;
			} else {
				//如果不为空,该对象的number值为该父部门下所有子部门编号的最大值(包括曾经删除掉的子部门),将该number值取出来加1,返回该对象
				//########如果部门新建成功,需要在DepartmentServiceImpl中update数据库中该对象的属性(通过判断id!=null和flag=0来确定是update操作)
				String number = Integer.toHexString(Integer.parseInt(departmentSN.getNumber(), 16) + 1).toUpperCase();
				//如果编号是0开头的,经过上一行的处理会变成一位,所以要处理一下,补上0
				if(number.length() == 1) {
					number = "0" + number;
				}
				departmentSN.setNumber(number);
				return departmentSN;
			}
		} else {
			//如果不为空,则直接返回该记录
			//#######如果部门新建成功,需要在DepartmentServiceImpl中将该对象从数据库中delete(通过判断flag=1来确定是delete操作)
			return departmentSN;
		}
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
	 * @Title: generatePersonSN
	 * @Description: 生成人员SN编号
	 * @param @param d
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String generatePersonSN(Department d) {
		List<Person> list=new ArrayList<Person>();
		int size=getChildPersons(list,d).size();
		String sn=d.getSn()+Integer.toHexString(size);
		return sn;
	}
	
}
