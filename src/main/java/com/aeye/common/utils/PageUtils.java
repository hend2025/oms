/**
 *
 *
 *
 *
 *
 */

package com.aeye.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.aeye.common.dto.AeyePageResult;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 *
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int currPage;
	/**
	 * 列表数据
	 */
	private List<?> list;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(IPage<?> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.currPage = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	/**
	 * 适配mybatis-plus的分页对象转换为hsaf分页对象；AeyePageInfo为hsaf适配分页对象
	 */
	public static <T> AeyePageResult pageConvert(IPage<T> page) {
		AeyePageResult aeyePageResult = new AeyePageResult();
		//List数据
		aeyePageResult.setData(page.getRecords());
		//记录条数
		aeyePageResult.setRecordCounts((int)page.getTotal());
		//每页大小
		aeyePageResult.setPageSize((int)page.getSize());
		//当前多少页
		aeyePageResult.setPageNum((int)page.getCurrent());
		//总分页大小
		aeyePageResult.setPages((int)page.getPages());
		aeyePageResult.setFirstPage(page.getCurrent() == 1);
		aeyePageResult.setLastPage(page.getCurrent() == page.getPages());
		return aeyePageResult;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
