package com.zjcds.common.base.http;

/**
 * http方法
 * 创建日期：2015年9月13日
 * @author nzg
 */
public enum HttpMethod {
	GET("org.apache.http.client.methods.HttpGet",false),
	POST("org.apache.http.client.methods.HttpPost",true),
	PUT("org.apache.http.client.methods.HttpPut",true),
	DELETE("org.apache.http.client.methods.HttpDelete",false),
	HEAD("org.apache.http.client.methods.HttpHead",false),
    TRACE("org.apache.http.client.methods.HttpTrace",false),
    OPTIONS("org.apache.http.client.methods.HttpOptions",false);
	/**对应操作类*/
	private String className;
	/**是否带消息体*/
	private boolean withBody;
	
	private HttpMethod(String className,boolean withBody){
		this.className = className;
		this.withBody = withBody;
	}
	
	public String getClassName(){
		return this.className;
	}

	public boolean isWithBody() {
		return withBody;
	}
	
}
