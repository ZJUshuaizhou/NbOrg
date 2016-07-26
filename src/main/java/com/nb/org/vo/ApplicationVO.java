package com.nb.org.vo;

import com.nb.org.domain.AppInfo;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class ApplicationVO extends PageVO<AppInfo> {

	private int visitorId;
	private int currentDepId;
	private String searchAppByName;
	private String searchOtherAppByName;
	private String searchOtherAppByCreator;
	
	public ApplicationVO(int pageNo, int len,int visitorId, int currentDepId, String searchAppByName, String searchOtherAppByName,
			String searchOtherAppByCreator) {
		super(pageNo, len);
		this.visitorId = visitorId;
		this.currentDepId = currentDepId;
		this.searchAppByName = searchAppByName;
		this.searchOtherAppByName = searchOtherAppByName;
		this.searchOtherAppByCreator = searchOtherAppByCreator;
	}
	
	
	
	
	
	public ApplicationVO(int pageNo, int len,int visitorId, int currentDepId, String searchAppByName) {
		super(pageNo, len);
		this.visitorId = visitorId;
		this.currentDepId = currentDepId;
		this.searchAppByName = searchAppByName;
	}





	public ApplicationVO(int pageNo, int len,int visitorId, int currentDepId, String searchOtherAppByName, String searchOtherAppByCreator) {
		super(pageNo, len);
		this.visitorId = visitorId;
		this.currentDepId = currentDepId;
		this.searchOtherAppByName = searchOtherAppByName;
		this.searchOtherAppByCreator = searchOtherAppByCreator;
	}





	public ApplicationVO(int pageNo, int len, int visitorId, int currentDepId) {
		super(pageNo, len);
		this.visitorId = visitorId;
		this.currentDepId = currentDepId;
	}

	public String getSearchAppByName() {
		return searchAppByName;
	}

	public void setSearchAppByName(String searchAppByName) {
		this.searchAppByName = searchAppByName;
	}

	public String getSearchOtherAppByName() {
		return searchOtherAppByName;
	}

	public void setSearchOtherAppByName(String searchOtherAppByName) {
		this.searchOtherAppByName = searchOtherAppByName;
	}

	public String getSearchOtherAppByCreator() {
		return searchOtherAppByCreator;
	}

	public void setSearchOtherAppByCreator(String searchOtherAppByCreator) {
		this.searchOtherAppByCreator = searchOtherAppByCreator;
	}

	public int getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}

	public int getCurrentDepId() {
		return currentDepId;
	}

	public void setCurrentDepId(int currentDepId) {
		this.currentDepId = currentDepId;
	}

	@Override
	public String toString() {
		return "ApplicationVO [visitorId=" + visitorId + ", currentDepId=" + currentDepId + ", total=" + total
				+ ", pageNo=" + pageNo + ", len=" + len + ", index=" + index + ", rows=" + rows + "]";
	}

}
