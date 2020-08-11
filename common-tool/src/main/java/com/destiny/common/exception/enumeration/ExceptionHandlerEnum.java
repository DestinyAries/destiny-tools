package com.destiny.common.exception.enumeration;

import com.destiny.common.exception.RequestValidationException;
import com.destiny.common.exception.ServiceException;
import com.destiny.common.exception.handler.ExceptionHandler;
import com.destiny.common.exception.handler.impl.DefaultExceptionHandler;
import com.destiny.common.exception.handler.impl.RequestValidationHandler;
import com.destiny.common.exception.handler.impl.ServiceExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Exception Handler Enumeration
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public enum ExceptionHandlerEnum {
    SERVICE(ServiceException.class, ServiceExceptionHandler::new),
    REQUEST_VALIDATE(RequestValidationException.class, RequestValidationHandler::new),


    DEFAULT(Exception.class, DefaultExceptionHandler::new)
    ;

    private Class<?> exception;
    private Supplier<ExceptionHandler> handler;

    ExceptionHandlerEnum(Class<?> exception, Supplier<ExceptionHandler> handler) {
        this.exception = exception;
        this.handler = handler;
    }

    /**
     * get the fitness handler by exception
     * @param e
     * @return
     */
    public static Supplier<ExceptionHandler> getHandler(Exception e) {
        Optional<ExceptionHandlerEnum> enumOptional = Arrays.stream(values())
                .filter((value) -> value != DEFAULT && value.getException().isInstance(e)).findFirst();
        if (enumOptional.isPresent()) {
            return enumOptional.get().getHandler();
        }
        return DEFAULT.getHandler();
    }

    public Class<?> getException() {
        return exception;
    }

    public Supplier<ExceptionHandler> getHandler() {
        return handler;
    }
}
