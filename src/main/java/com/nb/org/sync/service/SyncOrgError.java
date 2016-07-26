package com.nb.org.sync.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nb.org.sync.util.parse.IDataParse;

public class SyncOrgError {
	
	public final static String RESULT = "result";
	public final static String ERRORMSG = "errmsg";
	
	public final static String successCode = "0";
	private final static String otherError = "其他错误"; 
	
	public enum OrgError{
		success(successCode,"成功"),
		appForbidSync("1001","业务系统未被允许同步"),
		appNotOptOrg("1003","业务系统无权操作该组织"),
		parentOrgNotExist("1004","父组织不存在"),
		orderbyIllegal("1005","排序号有误"),
		mustFeildNULL("1006","新增时，必要字段为空"),
		orgcodingIllegal("1007","组织编码不规范"),
		devcodingIllegal("1008","devcoding存在非法字符"),
		argsIllegal("1009","args参数非法"),
		orgcodingUsed("1010","orgcoding已经被使用"),
		orgnameIllegal("1011","orgname参数非法"),
		orgnameExist("1012","orgname已经存在（同级组织，组织名称唯一）"),
		other("1099",otherError),
		/**删除组织*/
		orgNotExist("1104","组织不存在"),
		orgHaveSuborg("1105","组织(部门)，存在子组织(部门)，不能删除"),
		orgHaveUser("1106","组织（部门）下存在用户，不能删除"),
		orgHaveApp("1107","组织（部门）下存在接入资源,不能删除");
		private String errorCode;//错误码
		private String errorMsg;//错误中文信息
		private OrgError(String errorCode, String errorMsg) {
			this.errorCode = errorCode;
			this.errorMsg = errorMsg;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
	}
	
	/**
	 * 获取错误结果的JSON/XML数据
	 * @param parse	解析器
	 * @param error	错误
	 * @return
	 * @author sunwen
	 * @date 2013-9-30
	 */
	public static String getErrResult(IDataParse parse, OrgError error){
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		resultMap.put(SyncOrgError.RESULT, error.getErrorCode());
		resultMap.put(SyncOrgError.ERRORMSG, error.getErrorMsg());
		return parse.bulidSimple(resultMap);
	}
}
