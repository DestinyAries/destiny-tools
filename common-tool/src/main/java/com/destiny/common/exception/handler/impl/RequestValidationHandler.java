package com.destiny.common.exception.handler.impl;

import com.destiny.common.constant.SymbolConstant;
import com.destiny.common.entity.HttpResponseEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.BindErrorInfo;
import com.destiny.common.exception.handler.ExceptionHandler;
import com.destiny.common.exception.enumeration.RequestExceptionEnum;
import com.destiny.common.exception.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

/**
 * client request validation handler for web application
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class RequestValidationHandler implements ExceptionHandler<RequestValidationException> {
    @Override
    public HttpResponseEntity handle(RequestValidationException e) {
        log.debug("enter handle, the exception is: " + e.getException().getClass().getName());
        Exception requestException = e.getException();
        Optional<RequestExceptionEnum> enumOptional = RequestExceptionEnum.getType(requestException);
        if (!enumOptional.isPresent()) {
            log.warn("the exception not in the enum of RequestExceptionEnum, exception: " + requestException.getClass().getName());
            return HttpResponseEntity.clientError(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
        }
        switch (enumOptional.get()) {
            case BIND:{
                BindException bindException = (BindException) requestException;
                BindingResult bindingResult = bindException.getBindingResult();
                log.warn(String.format("[%s] - Request Params Binding Failure - ObjectName[%s] - detail[%s]",
                        GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), bindingResult.getObjectName(),
                        BindErrorInfo.getInfoByBindingResult(bindingResult)), bindException);
                break;
            }
            case ARGUMENT_NOT_VALID: {
                MethodArgumentNotValidException argumentException = (MethodArgumentNotValidException) requestException;
                MethodParameter methodParameter = argumentException.getParameter();
                log.warn("[{}] - Method Argument Not Valid - Method[{}] - [{}]", GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(),
                        methodParameter.getDeclaringClass().getName() + SymbolConstant.POINT_CHAR + methodParameter.getExecutable().getName(),
                        BindErrorInfo.getInfoByBindingResult(argumentException.getBindingResult()));
                break;
            }
            default:
                log.warn("[{}] - {}", GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), requestException.getMessage());
        }
        return HttpResponseEntity.clientError(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL);
    }

}
