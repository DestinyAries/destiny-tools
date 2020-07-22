package com.destiny.common.enumeration;

/**
 * 全局业务码枚举
 * @Author Destiny
 * @Version 1.0.0
 */
public enum GlobalServerCodeEnum implements ServerCode {
    /**
     * 0000 接口业务处理成功
     */
    SUCCESS("0000", "接口业务处理成功"),

    /**
     * 9999 系统未知错误
     */
    UNKNOWN_EXCEPTION("9999", "系统未知错误"),

    // =================全局状态码=================
    /**
     * 0001 请求参数非法
     */
    REQUEST_PARAM_ILLEGAL("0001", "请求参数非法"),

    /**
     * 0002 请求验签异常
     */
    REQUEST_SIGNED_INVALID("0002", "请求验签异常"),

    /**
     * 0003 请求数据解密异常
     */
    REQUEST_DECRYPT_INVALID("0003", "请求数据解密异常"),

    /**
     * 0004 对象不存在
     */
    OBJECT_NOT_EXIST("0004", "对象不存在"),

    /**
     * 0005 服务不可用
     */
    SERVICE_NOT_AVAILABLE("0005", "服务不可用")
    ;

    private String code;

    private String msg;

    GlobalServerCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
