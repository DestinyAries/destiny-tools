package com.destiny.common.exception.handler.impl;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.BindErrorInfo;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Exception to be thrown when validation on an argument annotated with {@code @Valid} fails.
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class MethodArgumentNotValidExceptionHandler implements ExceptionHandler<MethodArgumentNotValidException> {

    @Override
    public boolean isSupport(Exception e) {
        return e instanceof MethodArgumentNotValidException;
    }

    @Override
    public ResultEntity handle(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        log.warn("[Validation Error] - [{}] - [{}]",
                ex.getParameter().getDeclaringClass().getName() + "." + ex.getParameter().getExecutable().getName(),
                BindErrorInfo.getInfoByBindingResult(bindingResult));
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

}
