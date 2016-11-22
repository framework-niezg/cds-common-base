package com.zjcds.common.base.http.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * JSON对象的响应处理
 * 创建日期：2015年9月13日
 * @author nzg
 */
public class  JsonObjectResponseHandler<T> extends AbstractResponseHandler<T>{
	/**对象类型*/
	private TypeReference<T> typeReference;
	/**json处理*/
	private ObjectMapper objectMapper;
	
	public JsonObjectResponseHandler<T> typeReference(TypeReference<T> typeReference){
		this.typeReference = typeReference;
		return this;
	}
	
	
	public JsonObjectResponseHandler<T> objectMapper(ObjectMapper objectMapper){
		this.objectMapper = objectMapper;
		return this;
	}
	
	/**
	 * 验证对象属性完整性
	 * 创建日期：2015年9月15日
	 * 修改说明：
	 * @author Administrator
	 */
	private void validate(){
		if(typeReference == null)
			throw new IllegalArgumentException("要转换的对象不能为空！");
		if(objectMapper == null)
			throw new IllegalArgumentException("json对象反序列化工具类不能为空！");
	}
	


	@Override
	public T handleEntity(HttpEntity entity) throws IOException {
		validate();
		String body = EntityUtils.toString(entity);
		if(StringUtils.isNotBlank(body))
			return objectMapper.readValue(body,typeReference);
		else
			return null;
	}

}
