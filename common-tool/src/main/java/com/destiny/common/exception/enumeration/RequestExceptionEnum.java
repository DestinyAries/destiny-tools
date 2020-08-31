package com.destiny.common.exception.enumeration;

import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Request Exception Enumeration
 * @Author Destiny
 * @Version 1.0.0
 */
public enum RequestExceptionEnum {
    BIND(BindException.class),
    VALIDATION(ValidationException.class),
    ARGUMENT_NOT_VALID(MethodArgumentNotValidException.class),
    HTTP_MESSAGE_CONVERSION(HttpMessageConversionException.class),
    REQUEST_METHOD_NOT_SUPPORTED(HttpRequestMethodNotSupportedException.class),
    REQUEST_PARAMETER_MISSING(MissingServletRequestParameterException.class),
    ;

    private Class<?> type;

    RequestExceptionEnum(Class<?> type) {
        this.type = type;
    }

    public static Optional<RequestExceptionEnum> getType(Exception e) {
        return Arrays.stream(values()).filter((value) -> value.getType().isInstance(e)).findFirst();
    }

    public static boolean isExist(Exception e) {
        return Arrays.stream(values()).anyMatch((value) -> value.getType().isInstance(e));
    }

    public Class<?> getType() {
        return type;
    }
}
