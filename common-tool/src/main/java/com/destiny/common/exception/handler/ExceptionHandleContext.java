package com.destiny.common.exception.handler;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.exception.handler.impl.DefaultExceptionHandler;
import com.destiny.common.exception.handler.impl.MethodArgumentNotValidExceptionHandler;
import com.destiny.common.exception.handler.impl.RequestParamBindExceptionHandler;
import com.destiny.common.exception.handler.impl.ServiceExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常处理容器，已注册常用异常处理器
 * @Author Destiny
 * @Version 1.0.0
 */
@Component
public class ExceptionHandleContext {
    private List<ExceptionHandler> handlers = new ArrayList<>(3);

    /**
     * 默认异常处理器
     */
    private ExceptionHandler defaultExceptionHandler;

    public ExceptionHandleContext() {
        initDefaultHandler();
    }

    public ExceptionHandleContext(List<ExceptionHandler> handlers) {
        if (handlers != null && !handlers.isEmpty()) {
            this.handlers.addAll(handlers);
        }
        initDefaultHandler();
    }

    private void initDefaultHandler() {
        handlers.add(new ServiceExceptionHandler());
        handlers.add(new RequestParamBindExceptionHandler());
        handlers.add(new MethodArgumentNotValidExceptionHandler());

        this.defaultExceptionHandler = new DefaultExceptionHandler();
    }

    /**
     * 处理异常
     * @param e 异常对象
     * @return ResultEntity
     */
    public ResultEntity handle(Exception e) {
        for (ExceptionHandler handler : handlers) {
            if (handler.supports(e)) {
                return handler.handle(e);
            }
        }
        return defaultExceptionHandler.handle(e);
    }

    /**
     * 添加异常处理器（优先级高）
     * @param handler 处理器
     */
    public ExceptionHandleContext addHandler(ExceptionHandler handler) {
        handlers.add(0, handler);
        return this;
    }

    /**
     * 添加异常处理器
     * @param order 排序
     * @param handler 处理器
     */
    public ExceptionHandleContext addHandler(int order, ExceptionHandler handler) {
        if (order < 0) {
            order = 0;
        } else if (order > handlers.size()) {
            order = handlers.size();
        }
        handlers.add(order, handler);
        return this;
    }
}
