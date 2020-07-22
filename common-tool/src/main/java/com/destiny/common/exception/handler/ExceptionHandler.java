package com.destiny.common.exception.handler;

import com.destiny.common.entity.ResultEntity;

/**
 * 异常处理接口
 * @Author Destiny
 * @Version 1.0.0
 */
public interface ExceptionHandler {

    Boolean supports(Exception e);

    ResultEntity handle(Exception e);
}
