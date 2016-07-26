package com.nb.org.sync.config;

import com.nb.org.util.SyncProperties;

public class SyncConfig {

	/**
	 * SSO提供的资源接入代码
	 * 根据不同接入资源修改
	 * */
	public static final String SERVICECODE = SyncProperties.servicecode;
	
	/**
	 * SSO提供的资源接入密码
	 * 用于对服务的信任验证
	 * 根据不同接入资源修改
	 */
	public static final String SERVICEPWD = SyncProperties.servicepwd;
	
	/**
	 * SSO用户登录名称字段
	 */
	public static final String LOGINNAME = "loginname";
	
	/**
	 * SSO组织编码字段
	 */
	public static final String ORGCODING = "orgcoding";
	
	/**
	 * SSO原组织编码字段
	 * 用于用户更换组织时记录原组织编码
	 */
	public static final String OLDORGCODING = "oldorgcoding";
	
	public static final String XML = "0";
	
	public static final String JSON = "1";
	
}
