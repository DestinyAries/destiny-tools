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
     * 9999 系统未知异常
     */
    UNKNOWN_EXCEPTION("9999", "未知异常"),

    // =================全局状态码=================
    /**
     * 0001 请求参数非法
     */
    REQUEST_PARAM_ILLEGAL("0001", "请求参数非法"),

    /**
     * 0002 请求的签名异常
     */
    REQUEST_SIGNATURE_ERROR("0002", "请求的签名异常"),

    /**
     * 0003 未被授权
     */
    REQUEST_UNAUTHORIZED("0003", "未被授权"),

    /**
     * 0004 接口业务处理异常
     */
    API_HANDLE_EXCEPTION("0004", "接口异常，请联系管理员"),

    /**
     * 0005 服务配置异常
     */
    CONFIGURATION_EXCEPTION("0005", "服务配置异常"),

    /**
     * 0006 对象不存在
     */
    OBJECT_NOT_EXIST("0006", "对象不存在"),

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
    public String getMessage() {
        return msg;
    }

}
