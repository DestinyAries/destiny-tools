package com.destiny.common.exception;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    String code = GlobalServerCodeEnum.UNKNOWN_EXCEPTION.getCode();

    /**
     * 异常类型，用于区分打印日志级别,默认为warn
     */
    ExceptionTypeEnum type = ExceptionTypeEnum.WARN;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public BusinessException(ServerCode serverCode) {
        super(serverCode.getMsg());
        this.code = serverCode.getCode();
    }

    public BusinessException(ServerCode serverCode, Throwable t) {
        super(serverCode.getMsg(), t);
        this.code = serverCode.getCode();
    }

    public BusinessException(String message, ExceptionTypeEnum type) {
        super(message);
        this.type = type;
    }

    public BusinessException(String code, String message, ExceptionTypeEnum type) {
        super(message);
        this.code = code;
        this.type = type;
    }

    public BusinessException(String code, String message, Throwable t, ExceptionTypeEnum type) {
        super(message, t);
        this.code = code;
        this.type = type;
    }

    public BusinessException(ServerCode serverCode, ExceptionTypeEnum type) {
        super(serverCode.getMsg());
        this.code = serverCode.getCode();
        this.type = type;
    }

    public BusinessException(ServerCode serverCode, Throwable t, ExceptionTypeEnum type) {
        super(serverCode.getMsg(), t);
        this.code = serverCode.getCode();
        this.type = type;
    }


    public static enum ExceptionTypeEnum {
        ERROR,
        WARN
    }


    public String getCode() {
        return code;
    }
}
