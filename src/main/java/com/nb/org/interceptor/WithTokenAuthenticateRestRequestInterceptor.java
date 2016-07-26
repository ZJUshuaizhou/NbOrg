package com.nb.org.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.service.IDepartmentPermissionService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.service.IPositionService;
import com.nb.org.util.GlobalConfig;
import com.nb.org.util.OAuth2TokenValidationServiceClient;
import com.nb.org.util.ValidationResponse;

/*
 * 该拦截器拦截所有rest的请求,验证请求的用户是否有权限请求相应的rest API
 */
public class WithTokenAuthenticateRestRequestInterceptor implements HandlerInterceptor {

	@Autowired
	private IDepartmentPermissionService departmentPermissionService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private IPositionService positionService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		JSONObject json = new JSONObject();

		// 获取请求路径(格式：/rest/*)
		String servletPath = request.getServletPath();
		// 根据servletPath获取请求的rest path
		String restPath = getRestPath(servletPath);
		// 获取请求的方法
		String method = request.getMethod();
		// 获取请求头认证信息
		//String authorization = request.getHeader("Authorization");
		// 获取请求头中的部门信息
		String departmentName = request.getHeader("departmentName");
		String token=request.getHeader("token");
		System.out.println("----------------------------------------------------------------------------huoqu token"+token);
		if (departmentName != null) {
			departmentName = new String(URLDecoder.decode(departmentName,"UTF-8"));
			System.out.println("departmentName is +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++: "+departmentName);
		}
		
		// 判断请求中是否包含用户身份信息
		if (token != null && !"".equals(token)) {
//		if (authorization != null && !"".equals(authorization) && authorization.length() > 6) {
//			String credential = authorization.substring(6);
			// Base64解码,得到用户名和密码(格式：用户名:密码)
//			credential = new String(Base64.decodeFast(credential));
			// 判断解码后的字符串是否包含:
			OAuth2TokenValidationServiceClient client = new OAuth2TokenValidationServiceClient(GlobalConfig.server_url,
					GlobalConfig.user, GlobalConfig.password, null);
			ValidationResponse vr = client.validate(token);
//			if (credential.contains(":")) {
//				String[] pair = credential.split(":");
				// 调用接口验证用户名密码是否正确,然后再确认是否有相应的权限
				boolean identity = vr.isValid();
				//如果用户名密码验证通过
				if (identity) {
					// 调用接口判断是否有相应的权限
					Person restUser = personService.getPersonByUserName(vr.getAuthorizedUser());
					//调用非查询方法时需要提供 要管理的部门名称。查询方法面对所有人员开放。
					if(!method.equals("GET")) {
						if(departmentName == null) {
							json = new JSONObject();
							json.put("code", "2003");
							json.put("result", "failure");
							json.put("reason", "请输入您要管理的部门名称");
							response.setContentType("application/json");
							response.getWriter().write(json.toJSONString());
							return false;
						}
						Department department = departmentService.selectDepByName(departmentName);
						if(department == null) {
							json.put("code", "2004");
							json.put("result", "failure");
							json.put("reason", "您输入的要管理的部门名称不存在");
							response.setContentType("application/json");
							response.getWriter().write(json.toJSONString());
							return false;
						}
						//把操作者登录的部门放进request中
						request.setAttribute("departmentName", department);
						boolean entitled = departmentPermissionService.getDepartmantPermissionRest(department.getId(), restUser.getId()) == 1;
						if (entitled) {
							try {
								request.setAttribute("restUser", restUser);	
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							// 将该用户的信息放入request中
							return true;
						} else { // 您没有此权限进行该操作
							json = new JSONObject();
							json.put("code", "1000");
							json.put("result", "failure");
							json.put("reason", "您没有此权限进行该操作");
							response.setContentType("application/json");
							response.getWriter().write(json.toJSONString());
							return false;
						}
					} else {
						//到此可判断为执行查询方法,不需要权限,只要用户名密码校验通过即可
						return true;
					}
				} else { // 用户名或密码错误
					json = new JSONObject();
					json.put("code", "2000");
					json.put("result", "failure");
					json.put("reason", "token失效");
					response.setContentType("application/json");
					response.getWriter().write(json.toJSONString());
					return false;
				}
//			} else {
//				json = new JSONObject();
//				json.put("code", "2001");
//				json.put("result", "failure");
//				json.put("reason", "身份凭据格式不正确");
//				response.setContentType("application/json");
//				response.getWriter().write(json.toJSONString());
//				return false;
//			}

		} else {
			// 如果不包含身份信息,
			json = new JSONObject();
			json.put("code", "2002");
			json.put("result", "failure");
			json.put("reason", "未提供token身份凭据");
			response.setContentType("application/json");
			response.getWriter().write(json.toJSONString());
			return false;
		}

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private String getRestPath(String servletPath) {
		String temp = servletPath.substring(6);
		if (temp.contains("/")) {
			int location = temp.indexOf('/');
			temp = temp.substring(0, location);
		}
		return temp;
	}

}
