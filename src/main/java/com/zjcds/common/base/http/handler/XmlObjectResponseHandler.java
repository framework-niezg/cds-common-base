package com.zjcds.common.base.http.handler;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

/**
 * 对响应体的xml格式数据进行处理
 * 创建日期：2015年9月16日
 * @author Administrator
 */
public class XmlObjectResponseHandler<T> extends AbstractResponseHandler<T> {
	/**xml编组对象*/
	private Unmarshaller unmarshaller;
	
	public XmlObjectResponseHandler<T> unmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
		return this;
	}
	
	public void validate(){
		if(unmarshaller == null)
			throw new IllegalStateException("xml编组对象未设置！");
	}

	@Override
	public T handleEntity(HttpEntity entity) throws IOException {
		validate();
		String body = EntityUtils.toString(entity);
		return (T) unmarshaller.unmarshal(new StreamSource(new StringReader(body)));
	}
	
}
