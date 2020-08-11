package com.destiny.common.exception;

import com.destiny.common.entity.BeanForTest;
import com.destiny.common.entity.HttpResponseEntity;
import com.destiny.common.entity.ResultEntity;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.exception.handler.ExceptionHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.test.util.AssertionErrors;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Method;

/**
 * Tester for Exception Handlers
 * @Author Destiny
 * @Version 1.0.0
 */
public class ExceptionHandlerTest {
    private ExceptionHandlerContext exceptionHandlerContext = new ExceptionHandlerContext();

    @Test
    public void requestBindExceptionTest() {
        BindException bindException = new BindException("target", "testObj");
        HttpResponseEntity<ResultEntity> responseEntity = exceptionHandlerContext.handle(bindException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), responseEntity.getBody().getCode(),
                "the result of server code error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), responseEntity.getBody().getMessage(),
                "the result of message error");
    }

    @Test
    public void requestMethodArgNotValidExceptionTest() {
        Method method = null;
        try {
            method = BeanForTest.class.getDeclaredMethod("function", Integer.class, String.class);
        } catch (NoSuchMethodException e) {
            AssertionErrors.fail(e.getClass().getName() + " - " + e.getMessage());
        }
        if (method == null) {
            AssertionErrors.fail("method is null");
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        MethodArgumentNotValidException bindException = new MethodArgumentNotValidException(parameter, new BindException("name", "BeanForTest").getBindingResult());
        HttpResponseEntity<ResultEntity> responseEntity = exceptionHandlerContext.handle(bindException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), responseEntity.getBody().getCode(),
                "the result of server code error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), responseEntity.getBody().getMessage(),
                "the result of message error");
    }

    @Test
    public void requestValidationExceptionTest() {
        ValidationException validationException = new ValidationException("some error in request validation", GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode());
        HttpResponseEntity<ResultEntity> responseEntity = exceptionHandlerContext.handle(validationException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), responseEntity.getBody().getCode(),
                "the result of server code error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), responseEntity.getBody().getMessage(),
                "the result of message error");
    }

    @Test
    public void requestHttpMessageConversionExceptionTest() {
        HttpMessageConversionException exception = new HttpMessageConversionException("some error in HttpMessageConversionException", new NullPointerException());
        HttpResponseEntity<ResultEntity> responseEntity = exceptionHandlerContext.handle(exception);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getCode(), responseEntity.getBody().getCode(),
                "the result of server code error");
        Assertions.assertEquals(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage(), responseEntity.getBody().getMessage(),
                "the result of message error");
    }

    @Test
    public void serviceExceptionTest() {
        HttpResponseEntity<ResultEntity> defaultError = exceptionHandlerContext.handle(new ServiceException("error service"));
        Assertions.assertEquals(HttpStatus.OK, defaultError.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getCode(), defaultError.getBody().getCode(),
                "[default code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getMessage(), defaultError.getBody().getMessage(),
                "[default code] message error");

        HttpResponseEntity<ResultEntity> resultEntity = exceptionHandlerContext.handle(new ServiceException(LocalServerCodeEnum.OP_CREATE_ERROR));
        Assertions.assertEquals(LocalServerCodeEnum.OP_CREATE_ERROR.getCode(), resultEntity.getBody().getCode(),
                "[custom code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(LocalServerCodeEnum.OP_CREATE_ERROR.getMessage(), resultEntity.getBody().getMessage(),
                "[custom code] message error");
    }

    @Test
    public void defaultExceptionTest() {
        HttpResponseEntity<ResultEntity> unknownError = exceptionHandlerContext.handle(new Exception("unknown error"));
        Assertions.assertEquals(HttpStatus.OK, unknownError.getStatusCode(), "Status Code Error");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getCode(), unknownError.getBody().getCode(),
                "[default code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getMessage(), unknownError.getBody().getMessage(),
                "[default code] message error");

        HttpResponseEntity<ResultEntity> resultEntity = exceptionHandlerContext.handle(new NullPointerException());
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getCode(), resultEntity.getBody().getCode(),
                "[custom code] fit to the ServiceExceptionHandler error");
        Assertions.assertEquals(GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getMessage(), resultEntity.getBody().getMessage(),
                "[custom code] message error");
    }

}
