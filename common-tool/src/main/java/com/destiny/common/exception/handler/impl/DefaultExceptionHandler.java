package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.HttpResponseEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Default Exception Handler for Web Application
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public HttpResponseEntity handle(Exception e) {
        log.error(String.format("[%s]", GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getMessage()), e);
        return HttpResponseEntity.failure(GlobalServerCodeEnum.UNKNOWN_EXCEPTION);
    }
}
