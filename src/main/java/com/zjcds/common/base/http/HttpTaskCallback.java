package com.zjcds.common.base.http;

import org.apache.http.client.methods.HttpUriRequest;

import java.util.Map;

/**
 * http任务回调
 * 创建日期：2015年9月12日
 * @author nzg
 */
public interface HttpTaskCallback<T> {
	
	/**
	 * 
	 * @param httpRequest
	 * @param context
	 * @return
	 * 创建日期：2015年9月12日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpUriRequest callback(HttpRequestBuilder httpRequest, Map<String, Object> context);
	
}
