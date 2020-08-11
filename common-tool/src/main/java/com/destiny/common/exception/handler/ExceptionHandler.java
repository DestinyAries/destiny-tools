package com.destiny.common.exception.handler;

import com.destiny.common.entity.HttpResponseEntity;

/**
 * Exception Handler Interface
 * @Author Destiny
 * @Version 1.0.0
 */
public interface ExceptionHandler<T extends Exception> {

    /**
     * handle the Exception
     * @param exception
     * @return
     */
    HttpResponseEntity handle(T exception);
}
