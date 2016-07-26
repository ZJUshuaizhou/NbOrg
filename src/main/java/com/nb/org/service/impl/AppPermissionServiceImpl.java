package com.nb.org.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nb.org.domain.AppInfo;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppPermissionService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;

@Service("appPermissionService")
public class AppPermissionServiceImpl implements IAppPermissionService {
	@Resource
	private IAppInfoService appInfoService;
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentService departmentService;
	@Override
	public int getAppOperationPermission(int appId, int operatorId,int depId) throws Exception{
		//获取应用信息
		AppInfo appInfo = appInfoService.getAppById(appId);
		//获取用户
		Person person = personService.getPersonById(operatorId);
		//获取用户的所属部门
		Department department = departmentService.getDepartmentById(depId) ;
		//获取用户的职位
		Position position = personService.selectPositionByPerDep(person.getId(), department.getId());
		//判断用户是否为管理员
		int adminFlag = position.getAdminFlag();
		//当前操作者是应用创建者时赋予权限
		if(appInfo.getCreator().getId()==person.getId()){
			return 1;
		}
		//当前操作者的登录部门是应用所属部门且操作者为该部门的管理员是也赋予权限
		else if(appInfo.getManageDep().getId()==depId&&adminFlag==1){
			return 1;
		}else {
			return 0;
		}
	}

}
