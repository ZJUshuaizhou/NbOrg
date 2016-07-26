package com.nb.org.sync.util.parse;

import java.util.Map;

/**
 * 数据解析
 * @author sunwen
 * @date 2013-9-30
 */
public interface IDataParse {
	
	/**
	 * 构造简单属性返回方式。 无列表结构
	 * @param eles 普通数据
	 * @return
	 */
	public String bulidSimple(Map<String, String> eles);
	
	/**
	 * 解析简单的属性 成 map返回
	 * @param data 数据
	 * @return 
	 */
	public Map<String, String> parseSimple(String data) throws Exception ;
	
	/**
	 * 解析简单的属性返回节点Value
	 * @param data 数据(返回的结果xml)
	 * @param node 需解析的节点
	 * @return
	 */
	public String parseSimpleNode(String data, String node) throws Exception ;
}
