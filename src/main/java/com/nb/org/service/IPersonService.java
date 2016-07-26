package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.exception.PersonException;
import com.nb.org.vo.PersonVO;

public interface IPersonService {
	/*
	 * 插入人员，只保存人员基本信息，不保存与部门之间的关联关系
	 */
	int insertPerson(Person per) throws PersonException;

	/*
	 * 通过person的Id获取人员对象
	 */
	Person getPersonById(int id) throws PersonException;

	/*
	 * 通过person的Id获取人员对象
	 */
	Person getPersonByUserName(String userName) throws PersonException;
	
	/*
	 * 更新人员基本信息
	 */
	int updatePersonInfo(Person per) throws PersonException;
	
	int updatePersonDepId(Person per)throws PersonException;

	/*
	 * 通过id删除人员
	 */
	int delPerson(int id) throws PersonException;

	/*
	 * 保存人员与部门之间的关联关系
	 */
	int saveRelativity(Person p, Department dep, String posName, int adminFlag);
			

	/*
	 * 通过名字查询用户（允许重名所以会返回一个用户列表）
	 */
	List<Person> selectPersonsByName(String name) throws PersonException;

	/*
	 * 保存人员，包括人员与部门之间的关系
	 */
	int savePerson(Person p, Department dep, String posName, int adminFlag) throws PersonException;

	/*
	 * 更新人员，包括更新人员与部门之间的关系
	 */
	int updateRelativity(Person p, Department dep, String posName, int adminFlag);

	/*
	 * 解除人员与部门之间的关系
	 */
	int delRelativity(Person p, Department dep);

	/*
	 * 改变职位
	 */
	int changePosition(Person p, Department dep, String posName);

	/*
	 * 改变管理员权限
	 */
	int changeAdminFlag(Person p, Department dep, int adminFlag);

	/*
	 * 获取职位
	 */

	Position selectPositionByPerDep(int perId, int depId);
	
	/*
	 * 分页列出所有人员
	 */

	PersonVO listPersonPage(PersonVO vo);
	

	/*
	 * 用户根据用户名、新密码、旧密码进行密码更改
	 */
	int changePassword(String username, String newPaasword,String oldPassword) throws Exception;
	
	/*
	 * 管理员根据用户名进行密码重置（默认123456）
	 */
	int resetPaasword(String username) throws Exception;
	/*
	 * 管理员根据用户名进行密码修改
	 */
	int changePasswordByAdmin(String username,String password) throws Exception;
	
	/*
	 * 验证用户名密码是否正确  by upshi 20160221
	 * */
	boolean authenticate (String userName, String password) throws Exception;
	
	/*
	 * 通过用户名的范围查询用户   by  upshi 20160302
	 * */
	List<Person> getPersonsByUserNames(List<String> spliceUserNames);
	
	/*
	 * 保存人员，包括人员与部门之间的关系
	 */
	int insertPersonBySync(Person p, Department dep, String posName, int adminFlag,String password) throws PersonException;
	
}
