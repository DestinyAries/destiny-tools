package com.destiny.common.exception;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;

/**
 * service exception
 * use to server (handling client requests) when encounter some error that can be expected.
 * @Author Destiny
 * @Version 1.0.0
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * Server Code
     */
    private ServerCode serverCode = GlobalServerCodeEnum.UNKNOWN_EXCEPTION;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }

    public ServiceException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.serverCode = serverCode;
    }

    public ServiceException(ServerCode serverCode, String message) {
        super(message);
        this.serverCode = serverCode;
    }

    public ServiceException(ServerCode serverCode, Throwable t) {
        super(serverCode.getMessage(), t);
        this.serverCode = serverCode;
    }

    public ServerCode getServerCode() {
        return serverCode;
    }
}
