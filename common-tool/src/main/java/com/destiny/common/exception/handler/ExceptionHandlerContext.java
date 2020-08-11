package com.destiny.common.exception.handler;

import com.destiny.common.entity.HttpResponseEntity;
import com.destiny.common.exception.RequestValidationException;
import com.destiny.common.exception.enumeration.ExceptionHandlerEnum;
import com.destiny.common.exception.handler.impl.RequestValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Exception Handler Context
 * @Author Destiny
 * @Version 1.0.0
 */
@Component
@Slf4j
public class ExceptionHandlerContext {

    public HttpResponseEntity handle(Exception e) {
        log.debug("context handling... the handling exception is: " + e.getClass().getName());
        if (RequestValidationException.isBelong(e)) {
            return new RequestValidationHandler().handle(new RequestValidationException(e));
        }
        return ExceptionHandlerEnum.getHandler(e).get().handle(e);
    }

}
