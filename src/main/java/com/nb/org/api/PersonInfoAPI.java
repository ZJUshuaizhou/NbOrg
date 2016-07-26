package com.nb.org.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.Person;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IAppRoleService;
import com.nb.org.service.IPersonService;
import com.nb.org.util.GlobalConfig;
import com.nb.org.util.OAuth2TokenValidationServiceClient;
import com.nb.org.util.ValidationResponse;

@Controller
@RequestMapping("/api")
public class PersonInfoAPI {

	@Autowired
	private IPersonService personService;
	@Autowired
	private IAppRoleService appRoleService;
	@ResponseBody
	@RequestMapping(value = "/getPersonInfo/{token}/{username}/{appName}", method = RequestMethod.GET)
	public Map<String, Object> getPersonInfo(@PathVariable String token, @PathVariable String username,
			@PathVariable String appName) {
		Map<String, Object> map = new HashMap<String, Object>();
		OAuth2TokenValidationServiceClient client = new OAuth2TokenValidationServiceClient(GlobalConfig.server_url,
				GlobalConfig.user, GlobalConfig.password, null);
		ValidationResponse vr = client.validate(token);
		if (!vr.isValid()) {
			map.put("result", "failure");
			map.put("reason", "token不正确");
			return map;
		}
		Person person = null;
		try {
			person = personService.getPersonByUserName(username);
		} catch (PersonException e) {
			e.printStackTrace();
		}
		if (person != null) {
			map.put("result", "success");
			map.put("person", person);
			if (appName != null && appName.trim().length() > 0) {
				List<String> roles = null;
				roles = appRoleService.getRolesForPerson(appName, username);

				if (roles != null && roles.size() != 0) {
					map.put("code", "8000");
					map.put("result", "success");
					map.put("roles", roles);
				} else {
					map.put("code", "5003");
					map.put("result", "failure");
					map.put("reason", "您查询的人员在应用中没有角色");
				}
			} else {
				map.put("appName", "is null!failure!");
			}
		} else {
			map.put("result", "failure");
			map.put("reason", "username不正确，用户不存在");
		}
		return map;
	}

}
