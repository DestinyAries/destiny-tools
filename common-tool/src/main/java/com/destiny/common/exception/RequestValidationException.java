package com.destiny.common.exception;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;
import com.destiny.common.exception.enumeration.RequestExceptionEnum;

/**
 * the client request exception
 * @Author Destiny
 * @Version 1.0.0
 */
public class RequestValidationException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * server code status
     */
    private ServerCode serverCode = GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL;
    private Exception exception;

    public RequestValidationException() {
        super(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage());
    }

    public RequestValidationException(Exception exception) {
        super(GlobalServerCodeEnum.REQUEST_PARAM_ILLEGAL.getMessage());
        this.exception = exception;
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }

    public RequestValidationException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.serverCode = serverCode;
    }

    public RequestValidationException(ServerCode serverCode, Exception exception) {
        super(serverCode.getMessage());
        this.exception = exception;
    }

    public ServerCode getServerCode() {
        return serverCode;
    }

    public Exception getException() {
        return exception;
    }

    public static boolean isBelong(Exception e) {
        return RequestExceptionEnum.isExist(e);
    }
}
