package com.person.myspringboot.entity;

import java.util.List;

public class PageInfo<T> {

	private int count;
	private int pageNo;
	private int pageSize;

	private List<T> data;
	
	public PageInfo(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public PageInfo(int count, int pageNo, int pageSize, List<T> data) {
		super();
		this.count = count;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
