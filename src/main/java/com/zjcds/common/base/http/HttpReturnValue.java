package com.zjcds.common.base.http;

import org.apache.http.HttpResponse;

/**
 * 回调返回对象
 * 创建日期：2015年9月12日
 * @author nzg
 */
public class HttpReturnValue<T> {
	/**判定请求知否执行成功*/
	private boolean success = true;
	/**HttpResponse*/
	private HttpResponse response;
	
	private T bodyContent;
	/**执行失败时捕获到的异常对象*/
	private Exception exception;

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public T getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(T bodyContent) {
		this.bodyContent = bodyContent;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
