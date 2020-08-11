package com.destiny.common.enumeration;

/**
 * 服务码枚举接口
 * @Author Destiny
 * @Version 1.0.0
 */
public interface ServerCode {
    /**
     * 获取Code
     *
     * @return
     */
    String getCode();

    /**
     * 获取Message
     *
     * @return
     */
    String getMessage();

    default String toStr() {
        return "ServerCode{code='" + getCode() + '\'' +
                ", message='" + getMessage() + "\'}";
    }
}
