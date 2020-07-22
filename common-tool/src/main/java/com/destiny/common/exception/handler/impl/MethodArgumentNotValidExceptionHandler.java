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
 * 参数校验异常处理器
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
        log.warn(String.format("%s: 请求参数校验失败: %s", methodName, RequestExceptionHandler.getBindResultErrorJson(bindingResult)), ex);
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }
}
