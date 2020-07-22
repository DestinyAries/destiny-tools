package com.destiny.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;
import com.destiny.common.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 接口响应实体
 * @Author Destiny
 * @Version 1.0.0
 */
@ApiModel(description = "响应实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "状态码描述")
    private String msg;

    @ApiModelProperty(value = "响应实体")
    private T data;

    /**
     * 单纯列表
     * success(List<T> list) / success(String message, List<T> list)
     */
    @ApiModelProperty(value = "响应列表")
    private List<T> list;

    /**
     * 需要分页的列表
     * success(PageInfo pageInfo) / success(String message, PageInfo pageInfo)
     */
    @ApiModelProperty(value = "响应列表")
    private ResultPageEntity<T> pageList;

    public ResultEntity() {
    }

    public ResultEntity(ServerCode serverCode) {
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
    }

    public ResultEntity(ServerCode serverCode, String message) {
        this.code = serverCode.getCode();
        this.msg = message;
    }

    public ResultEntity(ServerCode serverCode, T result) {
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
        this.data = result;
    }

    public ResultEntity(ServerCode serverCode, String message, T result) {
        this.code = serverCode.getCode();
        this.msg = message;
        this.data = result;
    }

    public ResultEntity(ServerCode serverCode, List<T> list) {
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
        this.list = list;
    }

    public ResultEntity(ServerCode serverCode, String message, List<T> list) {
        this.code = serverCode.getCode();
        this.msg = message;
        this.list = list;
    }

    public ResultEntity(ServerCode serverCode, ResultPageEntity<T> pageList) {
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
        this.pageList = pageList;
    }

    public ResultEntity(ServerCode serverCode, String message, ResultPageEntity<T> pageList) {
        this.code = serverCode.getCode();
        this.msg = message;
        this.pageList = pageList;
    }

    public static ResultEntity success() {
        return new ResultEntity(GlobalServerCodeEnum.SUCCESS);
    }

    public static ResultEntity failure() {
        return new ResultEntity(GlobalServerCodeEnum.UNKNOWN_EXCEPTION);
    }

    public static ResultEntity failure(ServerCode serverCode) {
        return new ResultEntity(serverCode);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, data);
    }

    public static <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, data);
    }

    public static <T> ResultEntity<T> success(List<T> list) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list);
    }

    public static <T> ResultEntity<T> success(String message, List<T> list) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list);
    }

    public static <T> ResultEntity success(PageInfo pageInfo) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, BeanUtil.copyBean(pageInfo, ResultPageEntity.class));
    }

    public static <T> ResultEntity success(String message, PageInfo<T> pageInfo) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, BeanUtil.copyBean(pageInfo, ResultPageEntity.class));
    }

    /**
     * 判断是否成功
     * @return true or false
     */
    public boolean isSuccess() {
        return GlobalServerCodeEnum.SUCCESS.getCode().equals(this.code);
    }

    public String toJSONString() {
        return JSONObject.toJSONString(this);
    }
}
