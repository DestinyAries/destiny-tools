package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Exception to be thrown when validation on an argument annotated with {@code @Valid} fails.
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
@Component
@Order(4)
public class MethodArgumentNotValidExceptionHandler implements ExceptionHandler {

    @Override
    public Boolean supports(Exception e) {
        return e instanceof MethodArgumentNotValidException;
    }

    @Override
    public ResultEntity handle(Exception e) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
        BindingResult bindingResult = ex.getBindingResult();
        String methodName = ex.getParameter().getDeclaringClass().getName() + ex.getParameter().getExecutable().getName();
        log.warn("[Validation Error] - [{}] - [{}]", methodName, RequestParamBindExceptionHandler.getBindErrorInfo(bindingResult));
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

}
