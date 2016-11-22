package com.zjcds.common.base.http;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * http操作的模板对象
 * 创建日期：2015年9月11日
 * @author nzg
 */
public class HttpClientTemplate implements HttpClientOperation{
	
	/**客户端配置项*/
	private ClientOptions clientOptions = new ClientOptions();
	/**http客户端操作对象*/
	private CloseableHttpClient httpClient;
	/**初始化标志*/
	private boolean bInit = false;
	
	/**
	 * 构造方法
	 * 创建日期：2015年9月16日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpClientTemplate(){
	}
	
	/**
	 * 构造方法
	 * @param clientOptions
	 * 创建日期：2015年9月16日
	 * 修改说明：
	 * @author Administrator
	 */
	public HttpClientTemplate(ClientOptions clientOptions) {
		this.clientOptions = clientOptions;
	}
	
	public synchronized void init(){
		if(bInit){
			throw new IllegalStateException("HttpClientTemplate对象已经被初始化过，不能再次初始化！");
		}
		HttpClientConnectionManager cm = createHttpClientConnectionManager();
				
				httpClient = HttpClients.custom()
								.setConnectionManager(cm)
								.setDefaultRequestConfig(RequestConfig.custom()
															.setConnectionRequestTimeout(clientOptions.getMaxWaitTime())
															.setConnectTimeout(clientOptions.getConnectionTimeout())
															.build())
								.build();
		bInit = true;
	}
	
	/**
	 * 辅助方法，验证对象状态是否正确
	 * 创建日期：2015年9月16日
	 * 修改说明：
	 * @author Administrator
	 */
	private void vaildate(){
		if(!bInit)
			throw new IllegalStateException("HttpClientTemplate对象还未初始化，不能使用！");
	}
	
	/**
	 * @see com.ytm.common.http.HttpClientOperation#execute(com.ytm.common.http.HttpRequestBuilder, org.apache.http.client.ResponseHandler)
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	@Override
	public <T> HttpReturnValue<T> execute(HttpRequestBuilder httpRequestBuilder,ResponseHandler<T> responseHandler) {
		vaildate();
		HttpReturnValue<T> ret = new HttpReturnValue<T>();
		HttpClientContext context = new HttpClientContext();
		T result ;
		try {
			result = httpClient.execute(httpRequestBuilder.bulid(clientOptions),responseHandler,context);
			ret.setBodyContent(result);
			ret.setSuccess(true);
		} catch (Exception e) {
			ret.setException(e);
			ret.setSuccess(false);
		}
		ret.setResponse(context.getResponse());
		return ret;
	}
	
	
	
	/**
	 * 创建HttpClientConnectionManager
	 * @return
	 * 创建日期：2015年9月16日
	 * 修改说明：
	 * @author Administrator
	 */
	private HttpClientConnectionManager createHttpClientConnectionManager(){
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
												.build();
		SocketConfig socketConfig = SocketConfig.custom()
												.setSoTimeout(clientOptions.getSo_timeout())
												.build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", createDefaultSSLConnectionSocketFactory())
                .build());
		cm.setDefaultConnectionConfig(connectionConfig);
		cm.setDefaultSocketConfig(socketConfig);
		cm.setDefaultMaxPerRoute(clientOptions.getMaxConnectionsPerRoute());
		cm.setMaxTotal(clientOptions.getMaxConnections());
		return cm;
	}

	/**
	 * 辅助方法，创建可跳过可企业私有ssl认证的SSLConnectionSocketFactory
	 * @return
	 * @throws Exception
	 * 创建日期：2015年9月12日
	 * 修改说明：
	 * @author nzg
	 */
	private SSLConnectionSocketFactory createDefaultSSLConnectionSocketFactory() {
		try{
			//设置SSL
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new AnyTrustStrategy()).build();
			return new SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE);
		}
		catch(Exception e){
			throw new IllegalStateException("createDefaultSSLConnectionSocketFactory失败！",e);
		}
	}

	public ClientOptions getClientOptions() {
		return clientOptions;
	}

	public void setClientOptions(ClientOptions clientOptions) {
		this.clientOptions = clientOptions;
	}
	
	private class AnyTrustStrategy implements TrustStrategy{
		@Override
		public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			return true;
		}
		
	}
}
