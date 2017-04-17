package com.zjcds.common.base.domain.page;

import com.zjcds.common.base.domain.BaseBean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页结果集，包括总记录数和数据集合
 */
@Getter
@Setter
public class PagingResult<T> extends BaseBean {
	private static final long serialVersionUID = 8856755452533047842L;

	/** 总记录数 */
	private int totals = -1;
	/** 记录集合 */
	private List<T> records;

	public PagingResult() {
	}

	/**
	 * 构造方法
	 * @param totals    总记录数
	 * @param records   记录集合
	 */
	public PagingResult(int totals, List<T> records) {
		this.totals = totals;
		this.records = records;
	}

}
