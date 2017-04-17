package com.zjcds.common.base.mybatis;

import com.zjcds.common.base.domain.BaseBean;
import com.zjcds.common.base.domain.page.Paging;
import org.apache.commons.lang3.StringUtils;

/**
 * 包装一查询参数对象
 * 处理where、分页及orderBy参数
 * 创建日期：2015年9月23日
 * @author nzg
 */
@SuppressWarnings("unchecked")
public class QueryParam<T extends QueryParam> extends BaseBean {
	//分页参数
	private Paging paging;
	/**排序参数*/
	private String orderBy;
	/**临时属性*/
	private String orderProperty;
	
	public static final String SORTORDER_ASC = "asc";
	public static final String SORTORDER_DESC = "desc";
	
	
	public T paging(Paging paging){
		this.paging = paging;
		return (T) this;
	}

	private void addOrder(String column, String sortOrder) {
		if (orderBy == null) {
			orderBy = "";
		} else {
			orderBy = orderBy + ", ";
		}
		orderBy = orderBy + column + " " + sortOrder;
	}

	public T orderBy(String property) {
		this.orderProperty = property;
		return (T)this;
	}

	public T asc() {
		return (T) direction(Direction.ASCENDING);
	}

	public T desc() {
		return (T) direction(Direction.DESCENDING);
	}
  
	private T direction(Direction direction) {
		if (orderProperty == null) {
			throw new IllegalArgumentException("应该先设置orderBy属性");
		}
		addOrder(orderProperty, direction.getName());
		orderProperty = null;
		return (T) this;
	}
	
	/*以下为查询语句可获取的参数*/
	public String getPaging(){
		return paging == null ? "" : "limit " + paging.getStart() + "," + paging.getLimit();
	}

	public String getOrderBy() {
		if(StringUtils.isNotBlank(orderBy))
			return "order by " + orderBy;
		else
			return null;
	}
	
	/**
	 * 排序方向
	 * 创建日期：2015年9月23日
	 * @author nzg
	 */
	private enum Direction {

	  ASCENDING("asc"),
	  DESCENDING("desc");
	  
	  private String name;
	  
	  private Direction(String name) {
	    this.name = name;
	  }

	  public String getName() {
	    return name;
	  }
	 
	}

}
