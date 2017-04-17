package com.zjcds.common.base.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjcds.common.base.domain.page.Paging;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * created date：2017-03-29
 * @author niezhegang
 */
@Setter
@Getter
public class PagingArgumentResolver implements HandlerMethodArgumentResolver {
    private ObjectMapper objectMapper;
    private final static String ENCODING_DEFAULT = "UTF-8";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Paging.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if(request != null) {
            String contentType = request.getContentType();
            if(contentType != null && contentType.toLowerCase().indexOf(MediaType.APPLICATION_JSON_VALUE) >= 0) {
                try {
                    String requestBody = parseRequestBody(request.getInputStream(), contentType, request.getCharacterEncoding());
                    if(StringUtils.isNotBlank(requestBody)){
                        return objectMapper.readValue(requestBody,Paging.class);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private  String parseRequestBody(InputStream requestInputStream, String contentType, String encoding)
            throws IOException {
        byte[] requestBytes = IOUtils.toByteArray(requestInputStream);
        requestInputStream.close();
        for (String item : contentType.split("\\s*[\\,;]\\s*")) {
            if(item.toLowerCase().startsWith("charset")) {
                String tempEncoding = item.substring(item.indexOf("=")+1).trim();
                if(!tempEncoding.isEmpty()) {
                    encoding = tempEncoding;
                }
                break;
            }
        }
        return new String(requestBytes, encoding == null ? ENCODING_DEFAULT : encoding);
    }
}
