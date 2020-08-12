package com.destiny.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Jackson Exception
 * used in {@link com.destiny.common.util.JacksonUtil}
 * @Author Destiny
 * @Version 1.0.0
 */
public class JacksonException extends RuntimeException {

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
    }

    public JacksonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JacksonException(ExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum.getMessage(), cause);
    }

    public static JacksonException throwWhenObjectClassNull() {
        return new JacksonException(ExceptionEnum.OBJECT_CLASS_NULL);
    }

    public static JacksonException throwWhenNotAJson() {
        return new JacksonException(ExceptionEnum.NOT_A_JSON);
    }

    public static JacksonException throwWhenProcessToJavaError(JsonProcessingException e) {
        return new JacksonException(ExceptionEnum.PROCESS_TO_JAVA_OBJECT_ERROR, e);
    }

    public static JacksonException throwWhenIOError(IOException e) {
        return new JacksonException(ExceptionEnum.IO_ERROR, e);
    }

    public static JacksonException throwWhenFileIllegal() {
        return new JacksonException(ExceptionEnum.ILLEGAL_FILE);
    }

    public static JacksonException throwWhenClassCastError() {
        return new JacksonException(ExceptionEnum.CLASS_CAST_ERROR);
    }

    enum ExceptionEnum {
        OBJECT_CLASS_NULL("the object class is null"),
        NOT_A_JSON("the string is not a json string"),
        ILLEGAL_FILE("file is not exists, or not a file, or can not read"),
        PROCESS_TO_JAVA_OBJECT_ERROR("processing (parsing, generating) JSON content failure"),
        IO_ERROR("processing JSON content encountered some I/O problems"),
        CLASS_CAST_ERROR("class cast error"),
        ;
        private String message;

        ExceptionEnum(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
