package com.nb.org.vo;

import java.util.List;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class PageVO<E> {
	protected int total;
	protected int pageNo;
	protected int len;
	protected int index;
	List<E> rows;
	
	
	public PageVO() {
		super();
	}


	public PageVO(int pageNo, int len) {
		super();
		this.pageNo = pageNo;
		this.len = len;
		this.index = pageNo<1?0:(pageNo-1)*len;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public int getLen() {
		return len;
	}


	public void setLen(int len) {
		this.len = len;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public List<E> getRows() {
		return rows;
	}


	public void setRows(List<E> rows) {
		this.rows = rows;
	}


	@Override
	public String toString() {
		return "PageVO [total=" + total + ", pageNo=" + pageNo + ", len=" + len + ", index=" + index + ", rows=" + rows
				+ "]";
	}
	
	
}
