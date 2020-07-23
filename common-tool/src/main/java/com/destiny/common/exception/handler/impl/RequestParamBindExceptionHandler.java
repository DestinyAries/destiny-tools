package com.destiny.common.exception.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Binding the request beans and provide the Handler
 * to handle the exception message.
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class RequestParamBindExceptionHandler implements ExceptionHandler {

    @Override
    public Boolean supports(Exception e) {
        return e instanceof BindException;
    }

    @Override
    public ResultEntity handle(Exception e) {
        BindingResult bindingResult = ((BindException) e).getBindingResult();
        log.warn(String.format("[Request Params Binding Failure] - params:[%s] - [%s]", bindingResult.getObjectName(),
                getBindErrorInfo(bindingResult)), e);
        return new ResultEntity(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

    /**
     * Get the error info from {@link BindingResult}
     * @param bindingResult
     * @return
     */
    static String getBindErrorInfo(BindingResult bindingResult) {
        JSONObject jsonObject = new JSONObject();
        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                jsonObject.put(fieldError.getObjectName() + "." + fieldError.getField(),
                        new JSONObject(2).fluentPut("tip", error.getDefaultMessage())
                        .fluentPut("rejectedValue", JSON.toJSONString(fieldError.getRejectedValue())));
            } else {
                jsonObject.put(error.getObjectName(), error.getDefaultMessage());
            }
        });
        return jsonObject.toJSONString();
    }
}

