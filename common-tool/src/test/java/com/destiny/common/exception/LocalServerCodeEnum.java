package com.destiny.common.exception;

import com.destiny.common.enumeration.ServerCode;

/**
 * LocalServerCodeEnum for Test
 * @Author Destiny
 * @Version 1.0.0
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
    public String getMessage() {
        return this.msg;
    }
}
