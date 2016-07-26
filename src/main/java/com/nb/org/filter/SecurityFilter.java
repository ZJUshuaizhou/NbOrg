package com.nb.org.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppPermissionService;
import com.nb.org.service.IDepartmentPermissionService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonPermissionService;
import com.nb.org.service.IPersonService;

@Component("SecurityFilter")
public class SecurityFilter implements Filter {

	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentPermissionService departmantPermissionService;
	@Resource
	private IPersonPermissionService personPermissionService;
	@Resource
	private IAppInfoService appInfoService;
	@Resource
	private IAppPermissionService appPermissionService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();

		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();
		// 登陆页面无需过滤
		if (path.indexOf("login.jsp")> -1||(path.matches("^/NbOrg/rest.*") == true)||
				(path.matches("^/NbOrg/(synchronizeUser|synchronizeOrg|deleteUser|deleteOrg|).*") == true)||
				(path.matches("^/NbOrg/api/getPersonInfo.*") == true)) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		String subject = null;
		if (session.getAttribute(SSOAgentConfigs.getSessionBeanName()) != null) {
			if (((SSOAgentSessionBean) session.getAttribute(SSOAgentConfigs.getSessionBeanName()))
					.getSAMLSSOSessionBean() != null) {
				subject = ((SSOAgentSessionBean) session.getAttribute(SSOAgentConfigs.getSessionBeanName()))
						.getSAMLSSOSessionBean().getSubjectId();
				// 判断如果没有取到用户信息,就跳转到登陆页面
				if (subject == null || subject.equals("")) {
					// 跳转到登陆页面
					servletResponse.sendRedirect("/NbOrg/login.jsp");
					return;
				}
				// 已经登陆,继续此次请求
				else {
					// 从session中取出user
					Person person = (Person) session.getAttribute("user");
					// 从session中取出 dep
					Department department = (Department) session.getAttribute("dep");

					// 校验部门和人员的权限
					if (path.matches("^/NbOrg/(department|person)/(add|edit|delete|update).*") == true) {
						// 校验部门添加的权限
						if (path.matches("^/NbOrg/department/add") == true) {
							HttpServletRequest res = (HttpServletRequest) request;
							String id = (String) res.getParameter("deplist");
							if (id == null || id.equals("")) {
								chain.doFilter(request, response);
								return;
							} else {
								int depId = Integer.parseInt(id);
								int permission = 0;
								try {
									permission = departmantPermissionService.getDepartmantOperationPermission(depId,
											person.getId(), department.getId());
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}
						}
						// 校验部门编辑、删除、更新的权限
						if (path.matches("^/NbOrg/department/(edit|delete|update)/[0-9]{1,}") == true) {
							// 从url中取出部门id
							String regex = "(\\d+)";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(path);
							String id = null;
							if (m.find()) {
								id = m.group(1);
							}
							if (id == null || id.equals("")) {
								chain.doFilter(request, response);
								return;
							} else {
								int depId = Integer.parseInt(id);
								int permission = 0;
								try {
									permission = departmantPermissionService.getDepartmantOperationPermission(depId,
											person.getId(), department.getId());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}
						}
						// 校验人员删除的权限
						else if (path.matches("^/NbOrg/person/delete/[0-9]{1,}") == true) {
							// 从url中取出人员id
							String regex = "(\\d+)";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(path);
							String id = null;
							if (m.find()) {
								id = m.group(1);
							}
							if (id == null || id.equals("")) {
								chain.doFilter(request, response);
								return;
							} else {
								int perId = Integer.parseInt(id);
								int permission = 0;
								try {
									permission = personPermissionService.getPersonOperationPermission(perId,
											person.getId(), department.getId());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}

						}
						// 校验人员编辑、更新的权限
						 else if(path.matches("^/NbOrg/person/(edit|update)/[0-9]{1,}.*")== true) {
						 String depName = (String)request.getParameter("dep");
						 Department dep = departmentService.selectDepByFullName(depName);
						 if (dep == null || dep.equals("")) {
								chain.doFilter(request, response);
								return;
							} else {
								int depId = dep.getId();
								int permission = 0;
								try {
									permission = departmantPermissionService.getDepartmantOperationPermission(depId,
											person.getId(), department.getId());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}
						 }
						// 其他链接直接通过
						else {
							chain.doFilter(request, response);
							return;
						}
					}
					// 验证应用和角色的权限
					else if (path.matches("^/NbOrg/application/(edit|delete|finishEdit|listRoles|editRole|deleteRole|addRole|updateRole).*") == true) {
						if(path.matches("^/NbOrg/application/(edit|delete|listRoles|editRole|addRole|deleteRole|updateRole).*") == true) {
							String appId = null;
							appId = (String) request.getParameter("app");
							if(appId== null){
								appId = (String) request.getParameter("appId");
							}
							if(appId!=null){
								int permission = 0;
								try {
									permission = appPermissionService.getAppOperationPermission(Integer.parseInt(appId),
											person.getId(), department.getId());
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}else{
								chain.doFilter(request, response);
							}	
						}
						else if(path.matches("^/NbOrg/application/finishEdit.*") == true) {
							String appId = (String) request.getParameter("id");
							System.out.println(appId);
							if(appId!=null){
								int permission = 0;
								try {
									permission = appPermissionService.getAppOperationPermission(Integer.parseInt(appId),
											person.getId(), department.getId());
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (permission == 1) {
									chain.doFilter(request, response);
									return;
								} else {
									servletResponse.sendRedirect("/NbOrg/permissionDeny");
									return;
								}
							}else{
								chain.doFilter(request, response);
								return;
							}	
						}
					}
					// 如果访问的页面不需要进行权限控制，则直接访问
					else {
						chain.doFilter(request, response);
						return;
					}
				}

			} else {
				servletResponse.sendRedirect("/NbOrg/login.jsp");
				return;
			}
		} else {
			servletResponse.sendRedirect("/NbOrg/login.jsp");
			return;
		}

	}

	@Override
	public void destroy() {

	}

}
