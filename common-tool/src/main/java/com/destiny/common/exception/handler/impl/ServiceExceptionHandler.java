package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.HttpResponseEntity;
import com.destiny.common.exception.ServiceException;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Exception Handler for web application
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class ServiceExceptionHandler implements ExceptionHandler<ServiceException> {
    @Override
    public HttpResponseEntity handle(ServiceException e) {
        log.warn("[Server Service Error] - {}", e.getServerCode().toStr());
        return HttpResponseEntity.failure(e.getServerCode());
    }
}
