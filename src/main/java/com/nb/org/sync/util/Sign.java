package com.nb.org.sync.util;

import com.nb.org.sync.config.SyncConfig;

public class Sign {
	
	/**
	 * 服务头认证
	 * @param servicecode	资源代码
	 * @param time			时间戳
	 * @param sign			标识
	 * @return boolean false 验证失败 true验证通过
	 * @author sunwen
	 * @date 2013-9-30
	 */
	public static boolean sign(String servicecode, String time, String sign){
		boolean b = false;
		if(SyncConfig.SERVICECODE.equals(servicecode)){
			String _sign = MD5.MD5Encode(servicecode+SyncConfig.SERVICEPWD+time);
			if(_sign.equals(sign)){
				b = true;
			}
		}
		return b;
	}
}
