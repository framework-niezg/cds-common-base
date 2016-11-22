package com.zjcds.common.base.http;

import org.apache.http.client.ResponseHandler;

/**
 * http客户端操作接口
 * 创建日期：2015年9月12日
 * @author nzg
 */
public interface HttpClientOperation {
	
	public <T> HttpReturnValue<T> execute(HttpRequestBuilder requestBuilder, ResponseHandler<T> responseHandler);

}
