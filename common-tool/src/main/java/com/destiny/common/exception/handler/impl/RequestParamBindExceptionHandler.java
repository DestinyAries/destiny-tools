package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.BindErrorInfo;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

/**
 * Binding the request beans and provide the Handler
 * to handle the exception message.
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class RequestParamBindExceptionHandler implements ExceptionHandler<BindException> {

    @Override
    public boolean isSupport(Exception e) {
        return e instanceof BindException;
    }

    @Override
    public ResultEntity handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.warn(String.format("[Request Params Binding Failure] - params:[%s] - [%s]", bindingResult.getObjectName(),
                BindErrorInfo.getInfoByBindingResult(bindingResult)), e);
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

}

