package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.exception.ServiceException;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常处理器
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class ServiceExceptionHandler implements ExceptionHandler {

    @Override
    public Boolean supports(Exception e) {
        return e instanceof ServiceException;
    }

    @Override
    public ResultEntity handle(Exception e) {
        ServiceException serviceException = (ServiceException) e;
        log.warn("[Logical Service Error] - [{}]", serviceException.getServerCode().toStr());
        return new ResultEntity(serviceException.getServerCode());
    }
}
