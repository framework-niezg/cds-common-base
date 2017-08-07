package com.zjcds.common.base.mybatis;

import java.io.Serializable;
import java.util.List;

/**
 * 封装基本的增删改查方法的Mapper接口
 * 创建日期：2016年10月20日
 * @author niezhegang
 */
public interface BaseMapper<ID extends Serializable,T> {

	/**
	 * 插入一条记录
	 * @param obj
	 * @return
	 * 创建日期：2016年10月20日
	 * 修改说明：
	 * @author niezhegang
	 */
	public Integer insert(T obj) ;
	
	/**
	 * 根据ID删除一条对象
	 * @param id
	 * 创建日期：2016年10月20日
	 * 修改说明：
	 * @author niezhegang
	 */
	public Integer delete(ID id);
	
	/**
	 * 修改一条对象
	 * @param obj
	 * 创建日期：2016年10月20日
	 * 修改说明：
	 * @author niezhegang
	 */
	public Integer update(T obj) ;
	
	/**
	 * 根据ID查询一个对象
	 * @param id
	 * @return
	 * 创建日期：2016年10月20日
	 * 修改说明：
	 * @author niezhegang
	 */
	public T select(ID id) ;
	/**
	 * 获取所有对象
	 * @return
	 * 创建日期：2016年10月20日
	 * 修改说明：
	 * @author niezhegang
	 */
	public List<T> selectAll();

}
