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
    UNKNOWN_EXCEPTION("9999", "未知系统异常，请联系管理员"),

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
     * 0004 HTTP请求方法不支持
     */
    REQUEST_METHOD_NOT_SUPPORT("0004", "HTTP请求方法不支持"),

    /**
     * 0005 请求超时
     */
    REQUEST_TIME_OUT("0005", "请求超时"),

    /**
     * 0006 请求的URL非法
     */
    REQUEST_URL_ILLEGAL("0006", "请求的URL非法"),

    /**
     * 0006 请求的URL连接失败
     */
    REQUEST_URL_CONNECT_FAILURE("0007", "请求的URL连接失败，请检查URL是否可连接"),

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

    public static boolean isRequestParamIllegal(String code) {
        return REQUEST_PARAM_ILLEGAL.getCode().equals(code);
    }

    public static boolean isRequestSignatureError(String code) {
        return REQUEST_SIGNATURE_ERROR.getCode().equals(code);
    }
}
