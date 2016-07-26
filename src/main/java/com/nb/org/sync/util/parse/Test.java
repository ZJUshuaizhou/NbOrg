package com.nb.org.sync.util.parse;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.util.Base64;
import com.alibaba.fastjson.util.UTF8Decoder;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
//		HashMap<String,String> map =new HashMap<>();
//		map.put("loginname", "test");
//		map.put("username", "测试");
//		map.put("sex", "1");
//		map.put("encryptiontype", "2");
//		map.put("loginpwd", "MTIzNDU2");
//		map.put("telephone", "18812340000");
//		map.put("mobile", "18812340000");
//		map.put("email", "123@123.com");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.accumulateAll(map);
//		System.out.println(jsonObject.toString());
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//		String c=sdf.format(new Date());
//		System.out.println(c);
//		Base64 base64 = new Base64();
//		try {
//			System.out.println(new String(base64.decodeFast("YWRtaW4lM0FhZG1pbg=="),"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		IDataParse iDataParse = new JsonDataParse();
//		String result ="{\"result\":\"0\",\"errmsg\":\"成功\"}";
//		result= result.split(":")[1].split("\"")[0];
//		System.out.println(result);
		String orgcoding = "001001001";
		String org_p = orgcoding.substring(0, orgcoding.length()-3);
		System.out.println(org_p);
	}
}
