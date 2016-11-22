package com.zjcds.common.base.http;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * 构造http请求
 * 创建日期：2015年9月13日
 * @author nzg
 */
public class HttpRequestBuilder{

	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
	public static final String HEADER_HOST = "Host";
	public static final String HEADER_ETAG = "ETag";
	public static final String HEADER_CONNECTION = "Connection";
	public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
	public static final String HEADER_CLOSE = "Close";
	
	/**http方法*/
	private HttpMethod method;
	/**http版本*/
	private HttpVersion httpVersion = HttpVersion.HTTP_1_1;
	/**http或者https*/
	private String schema;
	/**主机*/
	private String host;
	/**端口*/
	private Integer port;
	/**请求路径*/
	private String path;
	/**请求头*/
	private HttpValuesMap<String> headers = HttpValuesMap.ofStrings();
	/**查询参数*/
	private HttpValuesMap<String> querys = HttpValuesMap.ofStrings();
	
	/**默认body mime类型*/
	public final static String defaultBodyMediaType = "text/html";
	/**默认表单MediaType*/
	public final static String defaultFormMediaType = "application/x-www-form-urlencoded";

	/**默认body编码*/
	public final static String defaultBodyEncoding = "UTF-8";
	/**默认 URI字符集*/
	public final static String defaultURIEncoding = "UTF-8";
	
	/**form data*/
	private HttpValuesMap<String> form;	
	/**raw body string*/
	private String body;
	
	/**内容类型*/
	private String mediaType ;
	/**content-type charset*/
	private String charset ;

	/**path及query字符集*/
	private String uriCharSet = defaultURIEncoding; 
	/**日志记录*/
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 构造方法
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder(){
		this.contentType(defaultBodyMediaType, defaultBodyEncoding);
	}
	
	/**
	 * 设置URI
	 * @param bodyCharSet
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder uri(String uri){
		try{
			return this.uri(new URI(uri));
		}
		catch(URISyntaxException e){
			throw new IllegalArgumentException("URI格式错误！",e);
		}
	}
	
	
	/**
	 * 设置URI
	 * @param bodyCharSet
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder uri(URI uri){
		//设置schema
		if(uri.getScheme() != null){
			this.schema = uri.getScheme();
		}
		//设置host
		if(uri.getHost() != null){
			this.host = uri.getHost();
		}
		//设置port
		if(uri.getPort() > 0 ){
			this.port = uri.getPort();
		}
		//设置path
		if(uri.getPath() != null ){
			this.path = uri.getPath();
		}
		//设置query
		String query = uri.getQuery();
		if(StringUtils.isNotBlank(query)){
			String[] nameValues = StringUtils.split(query,"&");
			if(ArrayUtils.isNotEmpty(nameValues)){
				String[] param = null;
				for(String nameValue : nameValues){
					param = StringUtils.split(nameValue, "=");
					if(ArrayUtils.isNotEmpty(param) && param.length == 2){
						this.querys.add(param[0], param[1]);
					}
				}
			}
		}
		return this;
	}
	
	/**
	 * 设置URI字符集
	 * @param bodyCharSet
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder uriCharSet(String uriCharSet){
		this.uriCharSet = uriCharSet;
		return this;
	}
	
	
	/**
	 * get调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder get(){
		this.method = HttpMethod.GET;
		return this;
	}
	
	/**
	 * get调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder get(String uri){
		this.method = HttpMethod.GET;
		return uri(uri);
	}
	
	/**
	 * get调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder get(URI uri){
		this.method = HttpMethod.GET;
		return uri(uri);
	}
	
	/**
	 * post调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder post(){
		this.method = HttpMethod.POST;
		return this;
	}
	
	/**
	 * post调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder post(String uri){
		this.method = HttpMethod.POST;
		return uri(uri);
	}
	
	/**
	 * post调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder post(URI uri){
		this.method = HttpMethod.POST;
		return uri(uri);
	}
	
	/**
	 * @param method
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder delete(){
		this.method = HttpMethod.DELETE;
		return this;
	}
	
	/**
	 * delete调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder delete(String uri){
		this.method = HttpMethod.DELETE;
		return uri(uri);
	}
	
	/**
	 * delete调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder delete(URI uri){
		this.method = HttpMethod.DELETE;
		return uri(uri);
	}
	
	/**
	 * put调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder put(){
		this.method = HttpMethod.PUT;
		return this;
	}
	
	/**
	 * put调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder put(String uri){
		this.method = HttpMethod.PUT;
		return uri(uri);
	}
	
	/**
	 * put调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder put(URI uri){
		this.method = HttpMethod.PUT;
		return uri(uri);
	}
	
	/**
	 * head调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder head(){
		this.method = HttpMethod.HEAD;
		return this;
	}
	
	/**
	 * head调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder head(String uri){
		this.method = HttpMethod.HEAD;
		return uri(uri);
	}
	
	/**
	 * head调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder head(URI uri){
		this.method = HttpMethod.HEAD;
		return uri(uri);
	}
	
	/**
	 * options调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder options(){
		this.method = HttpMethod.OPTIONS;
		return this;
	}
	
	/**
	 * options调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder options(String uri){
		this.method = HttpMethod.OPTIONS;
		return uri(uri);
	}
	
	/**
	 * options调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder options(URI uri){
		this.method = HttpMethod.OPTIONS;
		return uri(uri);
	}
	
	/**
	 * trace调用
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder trace(){
		this.method = HttpMethod.TRACE;
		return this;
	}
	
	/**
	 * trace调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder trace(String uri){
		this.method = HttpMethod.TRACE;
		return uri(uri);
	}
	
	/**
	 * trace调用
	 * @param uri
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder trace(URI uri){
		this.method = HttpMethod.TRACE;
		return uri(uri);
	}
	
	/**
	 * 设置http版本
	 * @param httpVersion
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder httpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		return this;
	}
	
	/**
	 * 指定schema
	 * @param schema
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder schema(String schema) {
		this.schema = schema;
		return this;
	}
	
	/**
	 * 指定host
	 * @param host
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder host(String host) {
		this.host = host;
		return this;
	}
	
	/**
	 * 指定端口
	 * @param port
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder port(Integer port) {
		this.port = port;
		return this;
	}
	
	/**
	 * 指定url的子路径
	 * @param path
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder path(String path) {
		this.path = path;
		return this;
	}

	// ---------------------------------------------------------------- headers

	/**
	 * 返回header对应的值
	 * @param name
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	private String header(String name) {
		String key = name.trim().toLowerCase();

		Object value = headers.getFirst(key);

		if (value == null) {
			return null;
		}
		return value.toString();
	}

	/**
	 * 返回header对应的多值
	 * @param name
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	private String[] headers(String name) {
		String key = name.trim().toLowerCase();

		return headers.getStrings(key);
	}

	/**
	 * 添加一个header头，如果name已经存在，会附加为多值
	 * @param name
	 * @param value
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder header(String name, String value) {
		return header(name, value, false);
	}

	/**
	 * 添加一个header头
	 * @param name
	 * @param value
	 * @param overwrite
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder header(String name, String value, boolean overwrite) {
		String key = name.trim().toLowerCase();

		value = value.trim();

		if (key.equalsIgnoreCase(HEADER_CONTENT_TYPE)) {
			mediaType = extractMediaType(value);
			charset = extractContentTypeCharset(value);
		}

		if (overwrite == true) {
			headers.set(key, value);
		} else {
			headers.add(key, value);
		}
		return (HttpRequestBuilder) this;
	}
	
	/**
	 * 添加一个header头
	 * @param name
	 * @param value
	 * @param overwrite
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	private void _header(String name, String value, boolean overwrite) {
		String key = name.trim().toLowerCase();
		value = value.trim();
		if (overwrite) {
			headers.set(key, value);
		} else {
			headers.add(key, value);
		}
	}
	
	/**
	 * 添加int值作为header头
	 * @param name
	 * @param value
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBuilder header(String name, int value) {
		_header(name, String.valueOf(value), false);
		return (HttpRequestBuilder) this;
	}

	/**
	 * 返回所有header
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public Map<String, String[]> headers() {
		return Collections.unmodifiableMap(headers);
	}
	
	/**
	 * 添加一个查询值
	 * @param name
	 * @param value
	 * @return
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder query(String name, int value) {
		_query(name, String.valueOf(value), false);
		return (HttpRequestBuilder) this;
	}
	
	/**
	 * 添加一个查询值
	 * @param name
	 * @param value
	 * @return
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder query(String name, String value) {
		_query(name, value, false);
		return (HttpRequestBuilder) this;
	}
	
	/**
	 * 添加一个查询值
	 * @param name
	 * @param value
	 * @param overwrite
	 * @return
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder query(String name, String value,boolean overwrite) {
		_query(name, value, overwrite);
		return (HttpRequestBuilder) this;
	}
	
	
	/**
	 * 查询参数添加辅助方法
	 * @param name
	 * @param value
	 * @param overwrite
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	private void _query(String name,String value,boolean overwrite){
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
			name = name.trim();
			value = value.trim();
			if (overwrite) {
				querys.set(name, value);
			} else {
				querys.add(name, value);
			}
		}
	}
	
	/**
	 * 设置字符集
	 * @param charset
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder charset(String charset) {
		this.charset = charset;
		contentType(mediaType, charset);
		return (HttpRequestBuilder) this;
	}

	/**
	 * 设置mediaType
	 * @param mediaType
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder mediaType(String mediaType) {
		contentType(mediaType, charset);
		return (HttpRequestBuilder) this;
	}

	/**
	 * 设置mediaType及charset
	 * @param mediaType
	 * @param charset
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder contentType(String mediaType, String charset) {
		if (mediaType == null) {
			mediaType = this.mediaType;
		} else {
			this.mediaType = mediaType;
		}

		if (charset == null) {
			charset = this.charset;
		} else {
			this.charset = charset;
		}

		String contentType = mediaType;
		if (charset != null) {
			contentType += ";charset=" + charset;
		}

		_header(HEADER_CONTENT_TYPE, contentType, true);
		return (HttpRequestBuilder) this;
	}

	/**
	 * 设置contentLength
	 * @param value
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder contentLength(int value) {
		_header(HEADER_CONTENT_LENGTH, String.valueOf(value), true);
		return (HttpRequestBuilder) this;
	}

	/**
	 * set accept header
	 * @param encodings
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder accept(String encodings) {
		header(HEADER_ACCEPT, encodings, true);
		return (HttpRequestBuilder) this;
	}
	
	

	/**
	 * set Accept-Encoding header
	 * @param encodings
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder acceptEncoding(String encodings) {
		header(HEADER_ACCEPT_ENCODING, encodings, true);
		return (HttpRequestBuilder) this;
	}


	/**
	 * 初始化表单
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	protected void initForm() {
		if (form == null) {
			form = HttpValuesMap.ofStrings();
		}
	}

	/**
	 * Wraps non-Strings form values with {@link jodd.http.up.Uploadable uploadable content}.
	 * Detects invalid types and throws an exception. So all uploadable values
	 * are of the same type.
	 */
	protected String wrapFormValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof CharSequence) {
			return value.toString();
		}
		if (value instanceof Number) {
			return value.toString();
		}
		if (value instanceof Boolean) {
			return value.toString();
		}
		throw new RuntimeException("Unsupported value type: " + value.getClass().getName());
	}

	/**
	 * Adds the form parameter. Existing parameter will not be overwritten.
	 */
	public HttpRequestBuilder form(String name, Object value) {
		initForm();

		String wrappedValue = wrapFormValue(value);
		form.add(name, wrappedValue);

		return (HttpRequestBuilder) this;
	}

	/**
	 * Sets form parameter. Optionally overwrite existing one.
	 */
	public HttpRequestBuilder form(String name, Object value, boolean overwrite) {
		initForm();

		String wrappedValue = wrapFormValue(value);

		if (overwrite) {
			form.set(name, wrappedValue);
		} else {
			form.add(name, wrappedValue);
		}

		return (HttpRequestBuilder) this;
	}

	/**
	 * Sets many form parameters at once.
	 */
	public HttpRequestBuilder form(String name, Object value, Object... parameters) {
		initForm();

		form(name, value);

		for (int i = 0; i < parameters.length; i += 2) {
			name = parameters[i].toString();

			form(name, parameters[i + 1]);
		}
		return (HttpRequestBuilder) this;
	}

	/**
	 * Sets many form parameters at once.
	 */
	public HttpRequestBuilder form(Map<String, Object> formMap) {
		initForm();

		for (Entry<String, Object> entry : formMap.entrySet()) {
			form(entry.getKey(), entry.getValue());
		}
		return (HttpRequestBuilder) this;
	}

	/**
	 * 设置body
	 * @param body
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpRequestBuilder body(String body) {
		this.body = body;
		return (HttpRequestBuilder) this;
	}

	/**
	 * Extracts media-type from value of "Content Type" header.
	 */
	private static String extractMediaType(String contentType) {
		int index = contentType.indexOf(';');

		if (index == -1) {
			return contentType;
		}

		return contentType.substring(0, index);
	}

	/**
	 * 抽取ContentTypeCharset参数值
	 * @param contentType
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	private static String extractContentTypeCharset(String contentType) {
		return extractHeaderParameter(contentType, "charset", ';');
	}
	
	/**
	 * 抽取header参数值
	 * @param header
	 * @param parameter
	 * @param separator
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	private static String extractHeaderParameter(String header, String parameter, char separator) {
		int index = 0;

		while (true) {
			index = header.indexOf(separator, index);

			if (index == -1) {
				return null;
			}

			index++;

			// skip whitespaces
			while (header.charAt(index) == ' ') {
				index++;
			}

			int eqNdx = header.indexOf('=', index);

			if (eqNdx == -1) {
				return null;
			}

			String paramName = header.substring(index, eqNdx);

			eqNdx++;

			if (!paramName.equalsIgnoreCase(parameter)) {
				index = eqNdx;
				continue;
			}

			int endIndex = header.indexOf(';', eqNdx);

			if (endIndex == -1) {
				return header.substring(eqNdx);
			} else {
				return header.substring(eqNdx, endIndex);
			}
		}
	}
	
	/**
	 * 构建HttpUriRequest对象 
	 * @param clientOptions 可以包含全局属性，如header，queryparam等等。会被HttpRequestBuilder对象中设置的重复属性覆盖
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	public HttpRequestBase bulid(ClientOptions clientOptions){
		HttpRequestBase httpRequestBase = null;
		if(method == null)
			throw new IllegalArgumentException("请求方法不能为空！");
		//创建
		httpRequestBase = createHttpUriRequest(method);
		//设置协议，默认为http1.1
		httpRequestBase.setProtocolVersion(httpVersion);
		
		//build URI
		try{
			httpRequestBase.setURI(buildURI(clientOptions));
		}
		catch(Exception e){
			throw new IllegalArgumentException("构建URL出错", e);
		}
		//build header
		httpRequestBase.setHeaders(buildHeader(clientOptions));
		
		//with body
		if(method.isWithBody() && httpRequestBase instanceof HttpEntityEnclosingRequestBase){
			HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase) httpRequestBase;
			httpEntityEnclosingRequestBase.setEntity(buildBody(httpEntityEnclosingRequestBase));
		}
		return httpRequestBase;
	}
	
	/**
	 * 构建headers
	 * @param clientOptions
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	private Header[] buildHeader(ClientOptions clientOptions){
		//合并header
		Map<String,String> globalHeaders = clientOptions.getHeaders();
		for(String key : globalHeaders.keySet()){
			if(headers.get(key) == null){
				headers.add(key, globalHeaders.get(key));
			}
		}
		
		List<Header> ret = new ArrayList<Header>();
		Set<Entry<String,String[]>> sets = headers.entrySet();
		String key;
		String[] values;
		for(Entry<String,String[]> entry : sets){
			key = entry.getKey();
			values = entry.getValue();
			if(values != null){
				for(String value : values){
					ret.add(new BasicHeader(key, value));
				}
			}
		}
		Header[] retArray = new Header[ret.size()];
		int i = 0;
		for(Header header : ret){
			retArray[i++] = header;
		}
		return retArray;
	}
	
	/**
	 * 构建URI
	 * @param clientOptions
	 * @return
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	private URI buildURI(ClientOptions clientOptions) throws URISyntaxException{
		//创建URIBuilder对象，并构建path
		URIBuilder builder = new URIBuilder().setCharset(Charset.forName(uriCharSet))
						.setScheme(StringUtils.isNotBlank(schema) ? schema : clientOptions.getSchema())
						.setHost(StringUtils.isNotBlank(host) ? host : clientOptions.getHost())
						.setPort(port != null ? port : clientOptions.getPort())
						.setPath(StringUtils.isNotBlank(path) ? path :  clientOptions.getPath());
		//合并请求参数
		Map<String,String> globalQuerys = clientOptions.getQuery();
		for(String key : globalQuerys.keySet()){
			if(querys.get(key) == null){
				querys.add(key, globalQuerys.get(key));
			}
		}
		//附加查询参数
		String[] values = null;
		for(String key : querys.keySet()){
			values = querys.get(key);
			if(values != null){
				for(String value : values){
					builder.addParameter(key, value);
				}
			}
		}
		URI uri = builder.build();
		logger.info("本次请求的URL为："+uri.toString());
		return uri;
	}
	
	/**
	 * 创建HttpRequestBase对象
	 * @param method
	 * @return
	 * 创建日期：2015年9月14日
	 * 修改说明：
	 * @author Administrator
	 */
	private HttpRequestBase createHttpUriRequest(HttpMethod method) {
		try{
			Class<?> c = Class.forName(method.getClassName());
			return (HttpRequestBase) c.newInstance();
		}
		catch(ClassNotFoundException e){
			throw new IllegalArgumentException("初始化HttpUriRequest对象失败：类路径中为找到"+method.getClassName(),e);
		}
		catch(InstantiationException | IllegalAccessException e){
			throw new IllegalArgumentException("通过反射创建"+method.getClassName()+"对象失败",e);
		}
	}
	
	/**
	 * 构建httpbody
	 * @return
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	private HttpEntity buildBody(HttpRequestBase httpRequestBase){
		 final AbstractHttpEntity httpEntity;
         if (this.body != null) {
        	 httpEntity = new StringEntity(this.body,ContentType.create(mediaType, charset));
         }
         else if(form != null){
        	 List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        	 //设置成表单content-type
        	 String contentType = charset != null ? defaultFormMediaType + ";charset=" + charset : defaultFormMediaType;
        	 
        	 httpRequestBase.setHeader(HEADER_CONTENT_TYPE, contentType);
        	
        	 String[] values;
        	 for(String key : form.keySet()){
        		 values = form.get(key);
        		 if(values != null){
	        		 for(String value : values){
	        			 formparams.add(new BasicNameValuePair(key, value));
	        		 }
        		 }
        	 }
        	 httpEntity = new UrlEncodedFormEntity(formparams,Charset.forName(charset));
         }
         else{
        	 httpEntity = new BasicHttpEntity();
         }
		return httpEntity;
	}

}
