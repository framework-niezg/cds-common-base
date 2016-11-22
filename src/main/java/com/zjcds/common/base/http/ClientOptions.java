package com.zjcds.common.base.http;

import com.zjcds.common.base.domain.BaseBean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP客户端配置项
 * 创建日期：2015年9月12日
 * @author nzg
 */
public class ClientOptions extends BaseBean {

	public static final int DEFAULT_PORT = -1;
	public static final String DEFAULT_SCHEMA = "http";
	public static final String DEFAULT_PATH = "";
	public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
	public static final int DEFAULT_SO_TIMEOUT = 30000;
	

	/**
	 * HttpClient配置参数
	 */
	private String host = "localhost";
	private int port = DEFAULT_PORT;
	private String schema = DEFAULT_SCHEMA;
	private String path = DEFAULT_PATH;
	/**连接超时*/
	private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
	/**socket超时*/
	private int so_timeout =  DEFAULT_SO_TIMEOUT;
	
	//连接池默认配置
	private int maxConnections = 30; //最大连接数
	private int maxConnectionsPerRoute = 10;//每路由最大连接数
	private int maxWaitTime = 9000; //最大获取连接等待时间（默认9s）
	
	/**预配置的header，可在多个请求中生效，会被请求中设置的同名header覆盖*/
	private Map<String,String> headers = new HashMap<String,String>();
	/**预配置的query参数，可在多个请求中生效，会被请求中设置的同名query参数覆盖*/
	private Map<String,String> query = new LinkedHashMap<String,String>();
	
	/**
	 * 添加一个header
	 * @param name
	 * @param value
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	public void addHeader(String name,String value){
		headers.put(name, value);
	} 
	
	/**
	 * 添加一个query
	 * @param name
	 * @param value
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	public void addQuery(String name,String value){
		query.put(name, value);
	} 
	
	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSo_timeout() {
		return so_timeout;
	}

	public void setSo_timeout(int so_timeout) {
		this.so_timeout = so_timeout;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxConnectionsPerRoute() {
		return maxConnectionsPerRoute;
	}

	public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	
}
