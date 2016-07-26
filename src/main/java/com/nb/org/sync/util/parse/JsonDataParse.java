package com.nb.org.sync.util.parse;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

public class JsonDataParse implements IDataParse {

	@Override
	public String bulidSimple(Map<String, String> eles) {
		JSONObject data = new JSONObject();
		//简单元素
		if(eles != null && eles.size()>0){
			data.accumulateAll(eles);
		}
		return data.toString();
	}

	@Override
	public Map<String, String> parseSimple(String data) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jObj = JSONObject.fromObject(data);
			Set<Entry> entrySet =  jObj.entrySet();
			for (Entry entry : entrySet) {
				map.put((String)entry.getKey(), (String)entry.getValue());
			}
		} catch (Exception e) {
			//TODO write log
			throw e;
		}
		return map;
	}

	@Override
	public String parseSimpleNode(String data, String node) throws Exception {
		String value = null;
		try {
			if(data != null && node !=null){
				JSONObject jObj = JSONObject.fromObject(data);
				value = (String)jObj.get(node);
			}
		} catch (Exception e) {
			//TODO write log
			throw e;
		}
		return value;
	}
	
	public static void main(String[] args) {
		JsonDataParse x = new JsonDataParse();
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginname", "zhangs");
		map.put("username", "张三");
		String str = x.bulidSimple(map);
		System.out.println(str);
		try {
			Map<String, String> map1 = x.parseSimple(str);
			for (Map.Entry<String, String> entry : map1.entrySet()) {
				System.out.println(entry.getKey()+"--"+entry.getValue());
			}
			System.out.println(x.parseSimpleNode(str, "username"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
