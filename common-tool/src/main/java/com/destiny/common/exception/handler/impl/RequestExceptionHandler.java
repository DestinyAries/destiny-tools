package com.destiny.common.exception.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 请求参数绑定异常处理器
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class RequestExceptionHandler implements ExceptionHandler {

    @Override
    public Boolean supports(Exception e) {
        return e instanceof BindException;
    }

    @Override
    public ResultEntity handle(Exception e) {
        BindingResult bindingResult = ((BindException) e).getBindingResult();
        log.warn(String.format("请求参数[%s]绑定失败: %s", bindingResult.getObjectName(), getBindResultErrorJson(bindingResult)), e);
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

    static String getBindResultErrorJson(BindingResult bindingResult) {
        JSONObject jsonObject = new JSONObject();
        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                jsonObject.put(fieldError.getObjectName() + "." + fieldError.getField(), error.getDefaultMessage());
            } else {
                jsonObject.put(error.getObjectName(), error.getDefaultMessage());
            }
        });
        return jsonObject.toJSONString();
    }
}

