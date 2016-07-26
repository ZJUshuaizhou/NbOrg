package com.nb.org.rest;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nb.org.util.GlobalConfig;
import com.nb.org.util.OAuth2TokenValidationServiceClient;
import com.nb.org.util.SyncProperties;
import com.nb.org.util.ValidationResponse;

import com.nb.org.sync.util.MD5;
import com.nb.org.sync.util.SimpleAuthServiceStub;
import com.nb.org.sync.util.SimpleAuthServiceStub.GetUserInfo;
import com.nb.org.sync.util.SimpleAuthServiceStub.GetUserInfoResponse;

@Controller
@RequestMapping("/rest/validateToken")
public class ValidateTokenRest {

	@ResponseBody
	@RequestMapping(value="/{token}" ,method = RequestMethod.GET)
	public Map<String, String> searchByName(@PathVariable String token) {
		Map<String, String> map = new HashMap<String, String>();
		OAuth2TokenValidationServiceClient client = new OAuth2TokenValidationServiceClient(GlobalConfig.server_url,GlobalConfig.user,GlobalConfig.password,null);
		ValidationResponse vr = client.validate(token);
		if(vr.isValid()){
			map.put("user", vr.getAuthorizedUser());
			map.put("result", "1");
			return map;
		}
		//调杭州获取用户的api 判断token
		SimpleAuthServiceStub serviceStub =null;
		try {
			serviceStub = new SimpleAuthServiceStub();
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		GetUserInfo getUserInfo = new GetUserInfo();
		getUserInfo.setServicecode(SyncProperties.servicecode);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String time =sdf.format(new Date());
		getUserInfo.setTime(time);
		String sign = SyncProperties.servicecode+SyncProperties.servicepwd+time;
		sign = MD5.MD5Encode(sign);
		getUserInfo.setSign(sign);
		getUserInfo.setToken(token);
		getUserInfo.setDatatype("json");
		try {
			GetUserInfoResponse getUserInfoResponse= serviceStub.getUserInfo(getUserInfo);
			System.out.println(getUserInfoResponse.getOut());
			String result =getUserInfoResponse.getOut();
			map.put("useinfonget from token", result);
			result= result.split(":")[1];
			result =result.split(",")[0];
			if(result=="\"0\""){
				map.put("result", "1");
				return map;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		map.put("result", "0");
		return map;
	}
	
}
