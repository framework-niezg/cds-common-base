package com.zjcds.common.base.http;



import com.fasterxml.jackson.databind.ObjectMapper;

import com.zjcds.common.base.http.handler.ByteArrayResponseHandler;
import com.zjcds.common.base.http.handler.StringResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Log4jConfigurer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HttpClientTemplateTest {
	
	HttpClientTemplate httpClientTemplate = new HttpClientTemplate();
	
	 static {  
	        try {  
	            Log4jConfigurer.initLogging("classpath:config/test-log4j.properties");
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  

	@Before
	public void setUp() throws Exception {
		ClientOptions clientOptions = new ClientOptions();
		clientOptions.setSchema("http");
		clientOptions.setHost("localhost");
		clientOptions.setSo_timeout(300000);
		clientOptions.setMaxConnectionsPerRoute(2);
		clientOptions.setMaxConnections(2);
		httpClientTemplate.setClientOptions(clientOptions);
		httpClientTemplate.init();
	}

	@Test
	public void test() {
		HttpReturnValue<String> ret = httpClientTemplate.execute(new HttpRequestBuilder()
										.get()
										.path("/ytm/file/user")
										, new StringResponseHandler());
		if(ret.isSuccess())
			System.out.println(ret.getBodyContent());
		
	}
	
	@Test
	public void testGetByteArray() throws Exception{
		HttpReturnValue<byte[]> ret = httpClientTemplate.execute(new HttpRequestBuilder()
										.get()
										.path("/ytm/file/download/3")
										, new ByteArrayResponseHandler());
		if(ret.isSuccess()){
			FileOutputStream fos = new FileOutputStream(new File("D:/text.jpg"));
			fos.write(ret.getBodyContent());
			fos.close();
		}
	}
	/*
	@Test
	public void testGetJsonObject() throws Exception{
		
		HttpReturnValue<ResponseResult<User>> ret = httpClientTemplate.execute(new HttpRequestBuilder()
				.get()
				.path("/ytm/file/user")
				, new JsonObjectResponseHandler<ResponseResult<User>>()
					.typeReference(new TypeReference<ResponseResult<User>>(){})
					.objectMapper(new ObjectMapper()));
		if(ret.isSuccess()){
			ResponseResult<User> responseResult = ret.getBodyContent();
			System.out.println(responseResult.getData());
		}
	}

	public void testMultiThread() throws Exception{
		new Thread(new Runnable() {
			public void run() {
				try {
					test();
				} catch (Exception e) {
				}
			}
		}).start();
		
		HttpReturnValue<ResponseResult<User>> ret = httpClientTemplate.execute(new HttpRequestBuilder()
				.get()
				.path("/ytm/file/user")
				, new JsonObjectResponseHandler<ResponseResult<User>>()
					.typeReference(new TypeReference<ResponseResult<User>>(){})
					.objectMapper(new ObjectMapper()));
		if(ret.isSuccess()){
			ResponseResult<User> responseResult = ret.getBodyContent();
			
			System.out.println(responseResult.getData());
		}
	}
	
	@Test
	public void testURI() throws Exception{
		
		HttpReturnValue<ResponseResult<User>> ret = httpClientTemplate.execute(new HttpRequestBuilder()
				.get("http://127.0.0.1")
				.path("ytm/file/user")
				.query("name", "张三")
				.query("sex", "男")
				, new JsonObjectResponseHandler<ResponseResult<User>>()
					.typeReference(new TypeReference<ResponseResult<User>>(){})
					.objectMapper(new ObjectMapper()));
		if(ret.isSuccess()){
			ResponseResult<User> responseResult = ret.getBodyContent();
			System.out.println(responseResult.getData());
		}
	}
	
	
	@Test
	public void testCertification() {
		HttpReturnValue<String> ret = httpClientTemplate.execute(new HttpRequestBuilder()
										.post("http://api.id98.cn/api/idcard")
										.form("name", "邬洪阳")
										.form("appkey", "42c4347d9ec9aec26cbd84bfce4a9da8")
										.form("cardno", "220802198709010016")
										, new StringResponseHandler());
		if(ret.isSuccess())
			System.out.println(ret.getBodyContent());
		
	}
	
	@Test
	public void testHttps() throws Exception{
		HttpReturnValue<String> ret = httpClientTemplate.execute(new HttpRequestBuilder()
										.get("https://tpm.onecc.me/tpaas/api/2.0/videocall/1")
										, new StringResponseHandler());
		if(ret.isSuccess())
			System.out.println(ret.getBodyContent());
		else
			throw ret.getException();
	}
	
	
	@Test
	public void testAccept() throws Exception{
		
		HttpReturnValue<String> ret = httpClientTemplate.execute(new HttpRequestBuilder()
				.get()
				.path("/ytm/file/user")
				.header("accept", "application/json")
				, new StringResponseHandler());
		if(ret.isSuccess()){
			String responseResult = ret.getBodyContent();
			System.out.println(responseResult);
		}
	}
	*/

}
