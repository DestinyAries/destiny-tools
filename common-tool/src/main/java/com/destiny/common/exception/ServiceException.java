package com.destiny.common.exception;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;

/**
 * 业务异常
 * @Author Destiny
 * @Version 1.0.0
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 异常业务码
     */
    ServerCode serverCode = GlobalServerCodeEnum.SERVICE_ERROR;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }

    public ServiceException(ServerCode serverCode) {
        super(serverCode.getMsg());
        this.serverCode = serverCode;
    }

    public ServiceException(ServerCode serverCode, String message) {
        super(message);
        this.serverCode = serverCode;
    }

    public ServiceException(ServerCode serverCode, Throwable t) {
        super(serverCode.getMsg(), t);
        this.serverCode = serverCode;
    }

    public ServerCode getServerCode() {
        return serverCode;
    }
}
