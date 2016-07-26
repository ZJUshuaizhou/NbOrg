package com.nb.org.domain;

public class PageInfo {
	/*
	 * 当前页索引
	 */
	private int pageIndex;
	/*
	 * 总页数
	 */
	private int pageTotal;
	/*
	 * 下一页索引
	 */
	private int pageNext;
	/*
	 * 上一页索引
	 */
	private int pagePrevious;
	
	public static int pageNumber=2;
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public int getPageNext() {
		return pageNext;
	}
	public void setPageNext(int pageNext) {
		this.pageNext = pageNext;
	}
	public int getPagePrevious() {
		return pagePrevious;
	}
	public void setPagePrevious(int pagePrevious) {
		this.pagePrevious = pagePrevious;
	}
	@Override
	public String toString() {
		return "PageInfo [pageIndex=" + pageIndex + ", pageTotal=" + pageTotal + ", pageNext=" + pageNext
				+ ", pagePrevious=" + pagePrevious + "]";
	}
	

}
