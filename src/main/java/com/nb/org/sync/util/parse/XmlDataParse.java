package com.nb.org.sync.util.parse;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlDataParse implements IDataParse {

	public static final String root = "SSO";
	public static final String encoding = "UTF-8";
	
	/**
	 * 构造xml数据。格式如下：
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <SSO>
	 *	 <loginname>zhangs</loginname>
	 *	 <uusername>张三</username>
	 * </SSO>
	 */
	@Override
	public String bulidSimple(Map<String, String> eles) {
		Document dom = DocumentHelper.createDocument();
		dom.setXMLEncoding(encoding);
	    Element rootele=dom.addElement(root);
	    if(eles != null && eles.size()>0){
	    	for (Map.Entry<String, String> entry : eles.entrySet()) {
	    		rootele.addElement(entry.getKey()).addText(entry.getValue());
			}
	    }
		return dom.asXML();
	}

	@Override
	public Map<String, String> parseSimple(String data) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(data != null && data.length() > 0){
				Document doc = build(data);
				if(doc != null){
					Element rootele =  doc.getRootElement();
					List<Element> eles = rootele.elements();
					for (Element ele : eles) {
						map.put(ele.getName(), ele.getText());
					}
				}
			}
		} catch (Exception e) {
			// TODO: write log
			throw e;
		}
		return map;
	}

	@Override
	public String parseSimpleNode(String data, String node) throws Exception {
		String value = null;
		try {
			if(data != null && data.length() > 0){
				Document doc = this.build(data);
				if(doc != null){
					Element ele = doc.getRootElement();
					value=  ele.elementText(node);
				}
			}
		} catch (Exception e) {
			// TODO: write log
			throw e;
		}
		return value;
	}

	private Document build(String data) throws DocumentException, UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		return saxReader.read(new ByteArrayInputStream(data.getBytes(encoding)));
	}
	
}
