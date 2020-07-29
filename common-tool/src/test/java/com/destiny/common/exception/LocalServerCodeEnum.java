package com.destiny.common.exception;

import com.destiny.common.enumeration.ServerCode;

/**
 * @Author linwanrong
 * @Date 2020/7/29 21:24
 */
public enum LocalServerCodeEnum implements ServerCode {
    OP_CREATE_ERROR("2020", "failure to adding a record"),
    ;

    private String code;

    private String msg;

    LocalServerCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
