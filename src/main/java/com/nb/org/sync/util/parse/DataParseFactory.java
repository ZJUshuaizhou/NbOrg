package com.nb.org.sync.util.parse;

import com.nb.org.sync.config.SyncConfig;

/**
 * 数据解析工厂类
 * @author sunwen
 * @date 2013-9-30
 */
public class DataParseFactory {
	
	public static IDataParse factory(String dataType){
		if("xml".equalsIgnoreCase(dataType) || SyncConfig.XML.equals(dataType)){
			return new XmlDataParse();
		}else{
			return new JsonDataParse();
		}
	}
}
