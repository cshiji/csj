/**
 * FileName:     Pager.java
 * @Description: TODO(用一句话描述该文件做什么)
 * All rights Reserved, Designed By hylandtec
 * Copyright:    Copyright(C) 2011-2012
 * Company       hylandtec
 * @author:      YangFan
 * @version      V1.0 
 * Createdate:   2014-2-17 上午11:28:09
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-2-17      YangFan          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.cshiji.superviki.base.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:   Pager
 * @Description: 分页数据对象
 *
 */
public class Pager<T> implements Serializable {
	
	public static final String KEY = "pager";
	public static final String PAGE_NO_KEY = "page";
	public static final String PAGE_SIZE_KEY = "rows";
	
	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = -501579436074742233L;
	

	private static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private long start; // 当前页第一条数据在List中的位置,从0开始

	private List<T> rows; // 当前页中存放的记录,类型一般为List

	private long total; // 总记录数

	private int pageNo = 1;// 要跳转到的页码
	
	private int count; //当前页实际记录条数
	

	/**
	 * 构造方法，只构造空页.
	 */
	public Pager() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 构造方法，只构造空页.
	 * 
	 * @param pageNo
	 * @param pageSize
	 */
	public Pager(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.setStart(getStartOfPage(this.pageNo, this.pageSize));
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param start
	 *            本页数据在数据库中的起始位置
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */

	public Pager(long start, long totalSize, int pageSize, List<T> rows) {
		this.pageSize = pageSize;
		this.start = start;
		this.total = totalSize;
		this.rows = rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotal() {
		return this.total;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount() {
		if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 * 
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		// 分页SQL
		int offset = 0;
		if (pageNo < 0) {
			offset = 0;
		} else {
			offset = (pageNo - 1) * pageSize;
		}
		return offset;
	}

	public long getStart() {
		return this.start;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.setStart(getStartOfPage(this.pageNo, this.pageSize));
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setStart(int pageNo, int pageSize) {
		if (pageNo < 0) {
			this.start = 0;
		} else {
			this.start = (pageNo - 1) * pageSize;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 、 设置当前页
	 * 
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		this.setStart(getStartOfPage(this.pageNo, this.pageSize));
	}
	
	
	//...
	private int beginPageIndex; // 页码列表的开始索引（包含）
	private int endPageIndex; // 页码列表的结束索引（包含）


	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
	 
	public void setBeginPageIndexAndEndPageIndex() {
		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (getTotalPageCount() <= 10) {
			beginPageIndex = 1;
			endPageIndex = (int) getTotalPageCount();
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = (int)getCurrentPageNo() - 4;
			endPageIndex = (int)getCurrentPageNo() + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > getTotalPageCount()) {
				endPageIndex = (int) getTotalPageCount();
				beginPageIndex = (int) getTotalPageCount() - 10 + 1;
			}
		}
	}

	/**
	 * 实际当前页大小
	 * @return
	 */
	public int getCount() {
		if(this.rows!=null){
			this.count = this.rows.size();
		}
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
