package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.nb.org.dao.IDepartmentDao;
import com.nb.org.dao.IDepartmentSNDao;
import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.vo.DepartmentVO;

@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {
	private static Logger logger = Logger.getLogger(DepartmentServiceImpl.class);
	@Resource
	private IDepartmentDao departmentDao;
	
	@Resource
	private IDepartmentSNDao departmentSNDao;

	@Override
	public int insertDepartment(Department dep , DepartmentSN sn) throws DepartmentException {
		if(dep==null||dep.getName().isEmpty()){
			DepartmentException exception=new DepartmentException("部门名称不能为空！");
			throw exception;
		}
		if(dep.getParentdep()==null){
			DepartmentException exception=new DepartmentException("请选择上级主管部门！");
			throw exception;
		}
		int res=0;
		try {
			res = departmentDao.insertDepartment(dep);
		//部门创建成功
		if(res == 1) {
			//判断sn id==null && flag == 0，插入
			if(sn.getId() == null && sn.getFlag() == 0) {
				departmentSNDao.insert(sn);
			} 
			//判断sn id!=null && flag == 0，更新
			if(sn.getId() != null && sn.getFlag() == 0) {
				departmentSNDao.updateByPrimaryKey(sn);
			}
			//判断sn flag == 1，删除
			if(sn.getFlag() == 1) {
				departmentSNDao.deleteByPrimaryKey(sn.getId());
			}
		}
		logger.info("insertDep-----" + JSON.toJSONString(dep));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DepartmentException("增加部门失败");
		}
		return res;
	}

	@Override
	public Department getDepartmentById(int id) throws DepartmentException {
		Department dep=new Department();
		try {
			dep = departmentDao.getDepartmentById(id);
			logger.info("getDep-----" + JSON.toJSONString(dep));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DepartmentException("获取部门失败");
		}
		return dep;
	}

	@Override
	public int updateDepartment(Department dep) throws DepartmentException {
		int res=0;
		try {
			res = departmentDao.updateDepartment(dep);
			logger.info("updateDep-----" + JSON.toJSONString(dep));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DepartmentException("更新部门失败");
		}
		return res;
	}

	@Override
	public int delDepartment(int id) throws DepartmentException {
		int res=0;
		try {
			Department dep = departmentDao.getDepartmentById(id);
			res = departmentDao.delDepartment(id);
			//如果删除成功,把删除的部门的编号保存到数据库已被以后使用
			if(res == 1) {
				DepartmentSN sn = new DepartmentSN();
				String number = dep.getSn();
				number = number.substring(number.length()-2);
				sn.setParentDeptId(dep.getParentdep().getId());
				sn.setBase(dep.getParentdep().getSn());
				sn.setFlag(1);
				sn.setNumber(number);
				departmentSNDao.insert(sn);
			}
			logger.info("delDep-----" + JSON.toJSONString(dep));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DepartmentException("删除部门失败");
		}
		return res;
	}

	/*
	 * @modifier upshi
	 * @date 20160119
	 */
	@Override
	public Department selectDepBySN(String sn) throws DepartmentException {
		if(sn == null || "".equals(sn)) {
			throw new DepartmentException("请输入部门SN");
		} else {
			Department dep=new Department();
			try {
				dep = departmentDao.selectDepBySN(sn);
				logger.info("selectDepByName-----" + JSON.toJSONString(dep));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DepartmentException("查询部门失败");
			}
			return dep;
		}
	}

	@Override
	public List<Department> getDepByParentDep(Department parentdep) throws DepartmentException {
		List<Department> deplist=new ArrayList<Department>();
		try {
			deplist = departmentDao.getDepByParentDep(parentdep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DepartmentException("查询部门失败");
		}
		return deplist;
	}

	@Override
	public Department selectDepByName(String name) {
		return departmentDao.selectDepByName(name);
	}

	@Override
	public Department selectDepByFullName(String fullname) {
		return departmentDao.selectDepByFullName(fullname);
	}

	@Override
	public DepartmentVO listDepsPageByPdep(DepartmentVO vo) {
		// TODO Auto-generated method stub
		List<Department> departments=departmentDao.getPageDepByParentDep(vo);
		int total=departmentDao.getTotalDepPagesByParentDep(vo.getDepartmentId());
		vo.setTotal(total);
		vo.setRows(departments);
		return vo;
	}

	@Override
	public DepartmentVO listDepsPageByName(DepartmentVO vo) {
		List<Department> departments=departmentDao.getPageDepByName(vo);
		int total=departmentDao.getTotalDepPagesByName(vo.getName());
		vo.setTotal(total);
		vo.setRows(departments);
		return vo;
	}

	@Override
	public DepartmentVO listDepsPageByFullName(DepartmentVO vo) {
		List<Department> departments=departmentDao.getPageDepByFullName(vo);
		int total=departmentDao.getTotalDepPagesByFullName(vo.getFullname());
		vo.setTotal(total);
		vo.setRows(departments);
		return vo;
	}

	@Override
	public DepartmentVO listDepsPage(DepartmentVO vo) {
		List<Department> departments=departmentDao.getPageDep(vo);
		int total=departmentDao.getTotalDepPages(vo);
		vo.setTotal(total);
		vo.setRows(departments);
		return vo;
	}
	
	@Override
	public List<Department> getAllChildDeps(List<Department> resultlist, Department dep) {
		List<Department> list=new ArrayList<Department>();
		try {
			list = getDepByParentDep(dep);
		} catch (DepartmentException e) {
			e.printStackTrace();
			
		}
		if(!list.isEmpty()){
		for(Department d:list){
		resultlist.add(d);
		getAllChildDeps(resultlist,d);	
		}
		}
		return resultlist;
	}

	@Override
	public List<Department> getDepartmentsByNames(List<String> spliceNames) {
		return departmentDao.getDepartmentsByNames(spliceNames);
	}
}
