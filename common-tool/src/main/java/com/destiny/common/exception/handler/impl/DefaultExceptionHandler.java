package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * A Default Exception Handler for Web Application
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public boolean isSupport(Exception e) {
        return true;
    }

    @Override
    public ResultEntity handle(Exception e) {
        log.error("[Unknown Error]", e);
        return new ResultEntity(GlobalServerCodeEnum.UNKNOWN_EXCEPTION);
    }
}
