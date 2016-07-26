package com.nb.org.exception;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppUpdateException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -961349783722251894L;
	
	private String msg;

	public AppUpdateException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
