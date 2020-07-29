package com.destiny.common.exception.handler;

import com.destiny.common.entity.ResultEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * Global REST API response handler
 * to package result by {@link ResultEntity} into response body
 * @Author Destiny
 * @Version 1.0.0
 */
public abstract class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> clazz) {
        return clazz.isAssignableFrom(MappingJackson2HttpMessageConverter.class)
                || clazz.isAssignableFrom(StringHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter returnType, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (serverHttpResponse instanceof ServletServerHttpResponse) {
            // 设置响应头 Content-Type: application/json;charset=UTF-8
            serverHttpResponse.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        }

        if (returnValue == null) {
            return ResultEntity.success();
        }
        // 是ResultEntity则直接返回
        if (returnValue instanceof ResultEntity) {
            return returnValue;
        }
        // 返回字符串特定处理
        if (returnValue instanceof String) {
            return ResultEntity.success(returnValue).toString();
        }
        // list
        if (returnValue instanceof List) {
            return ResultEntity.success((List<?>) returnValue);
        }
        return ResultEntity.success(returnValue);
    }
}
