package com.springapp.common.collection;

import java.util.List;

/**
 * .
 * 列表页管理类
 *
 * @param 数据类型
 * 
 */
public class PageHolder<T> {
	/**
	 * .
	 * 当前页码数
	 */
	private final int pageIndex;

	private final int pageSize;
	private final List<T> datas;
	private final int totalCount;

	public PageHolder(int pageIndex, int pageSize, int totalCount, List<T> datas) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.datas = datas;
		this.totalCount = totalCount;
	}

	/**
	 * .
	 * 获取当前页码
	 *
	 * @return 当前页码
	 */
	public final int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * .
	 * 获取当前页数据量
	 *
	 * @return 当前页数据量
	 */
	public final int getPageSize() {
		return this.pageSize;
	}

	/**
	 * .
	 * 获取当前页显示的数据内容
	 *
	 * @return 当前页的数据内容
	 */
	public final List<T> getDatas() {
		return this.datas;
	}

	/**
	 * .
	 * 获取总数据量
	 *
	 * @return 总数据量
	 */
	public final int getTotalCount() {
		return this.totalCount;
	}
}