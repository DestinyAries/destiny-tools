package com.destiny.common.exception;

import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandleContext;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.test.util.AssertionErrors;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Method;

/**
 * @Author Destiny
 * @Version 1.0.0
 */
public class ExceptionHandlerTest {
    private ExceptionHandleContext exceptionHandleContext = new ExceptionHandleContext();

    private final static String code = "2020";

    @Test
    public void bindExceptionTest() {
        BindException bindException = new BindException("target", "testObj");
        ResultEntity resultEntity = exceptionHandleContext.handle(bindException);
        System.out.println(resultEntity.toString());
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), resultEntity.getCode(),
                "fit to the RequestParamBindExceptionHandler");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMsg(), resultEntity.getMsg(),
                "message error");
    }

    @Test
    public void requestParamsExceptionTest() {
        Method method = null;
        try {
            method = ResultEntity.class.getDeclaredMethod("success", PageInfo.class);
        } catch (NoSuchMethodException e) {
            AssertionErrors.fail(e.getClass().getName() + " - " + e.getMessage());
        }
        if (method == null) {
            AssertionErrors.fail("method is null");
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        MethodArgumentNotValidException bindException = new MethodArgumentNotValidException(parameter, new BindException("target", "testObj").getBindingResult());
        ResultEntity resultEntity = exceptionHandleContext.handle(bindException);
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), resultEntity.getCode(),
                "fit to the MethodArgumentNotValidExceptionHandler");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMsg(), resultEntity.getMsg(),
                "message error");
    }

    @Test
    public void serviceExceptionTest() {
        ResultEntity defaultError = exceptionHandleContext.handle(new ServiceException("error service"));
        Assertions.assertEquals(GlobalServerCodeEnum.SERVICE_ERROR.getCode(), defaultError.getCode(),
                "[default code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(GlobalServerCodeEnum.SERVICE_ERROR.getMsg(), defaultError.getMsg(),
                "[default code] message error");

        ResultEntity resultEntity = exceptionHandleContext.handle(new ServiceException(LocalServerCodeEnum.OP_CREATE_ERROR));
        Assertions.assertEquals(LocalServerCodeEnum.OP_CREATE_ERROR.getCode(), resultEntity.getCode(),
                "[custom code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(LocalServerCodeEnum.OP_CREATE_ERROR.getMsg(), resultEntity.getMsg(),
                "[custom code] message error");
    }

    @Test
    public void otherExceptionTest() {
        ValidationException validationException = new ValidationException("test error validation", code);
        ResultEntity resultEntity = exceptionHandleContext.handle(validationException);
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getCode(), resultEntity.getCode(),
                "exception handle error when not fit the registered exception handles");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getMsg(), resultEntity.getMsg(),
                "message error");
    }
}
