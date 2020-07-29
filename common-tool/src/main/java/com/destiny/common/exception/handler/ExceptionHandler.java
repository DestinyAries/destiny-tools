package com.destiny.common.exception.handler;

import com.destiny.common.entity.ResultEntity;

/**
 * 异常处理接口
 * @Author Destiny
 * @Version 1.0.0
 */
public interface ExceptionHandler<T extends Exception> {

    boolean isSupport(Exception exception);

    /**
     * handle the Exception
     * @param exception
     * @return
     */
    ResultEntity handle(T exception);
}
