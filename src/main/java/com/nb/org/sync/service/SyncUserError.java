package com.nb.org.sync.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nb.org.sync.util.parse.IDataParse;

public class SyncUserError {
	
	public final static String RESULT = "result";
	public final static String ERRORMSG = "errmsg";
	
	public final static String successCode = "0";
	private final static String otherError = "其他错误"; 
	
	public enum UserError{
		success(successCode,"成功"),
		appForbidSync("2001","业务系统未被允许同步"),
		appNotOptUser("2003", "业务系统无权操作该用户"),
		loginNameIllegal("2004","登录账号存在特殊字符"),
		orgNoExist("2005","用户所在组织(部门)不存在"),
		orderbyIllegal("2006","排序号为非数字"),
		loginpwdIllegal("2007","新增用户时，loginpwd参数不能为空"),
		orgcodingIllegal("2008","组织编码不规范"),
		userLockedup("2009","用户被锁定，信息不能修改"),
		loginnameExist("2010","已经存在相同登录账号"),
		userNotExist("2011","用户不存在"),
		argsIllegal("2012","args参数非法"),
		usertypeIllegal("2013","用户类型非法"),
		entypeIllegal("2014","不支持的密码加密类型"),
		pwdDecodeErr("2015","密码解密出错"),
		modifyOrgFail("2016","用户调整组织发生错误"),
		usernameIllegal("2017","username参数不能为空"),
		phoneUnique("2018","手机号已存在"),
		phoneIllegal("2019","手机号不能为空"),
		other("2099",otherError);
		private String errorCode;//错误码
		private String errorMsg;//错误中文信息
		private UserError(String errorCode, String errorMsg) {
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
	public static String getErrResult(IDataParse parse, UserError error){
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		resultMap.put(SyncUserError.RESULT, error.getErrorCode());
		resultMap.put(SyncUserError.ERRORMSG, error.getErrorMsg());
		return parse.bulidSimple(resultMap);
	}
}
