package com.destiny.common.entity;

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
 * @Date 2018-05-21
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
     * 列表类型
     * 1. 列表 ResultListEntity
     * 2. 分页列表 ResultListEntity
     */
    @ApiModelProperty(value = "响应列表")
    private ResultListEntity<T> list;

    /**
     * 列表类型
     * 1. 列表 ResultListEntity
     * 2. 分页列表 ResultListEntity
     */
    @ApiModelProperty(value = "响应列表")
    private ResultListEntity<T> pageList;

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

    public ResultEntity(ServerCode serverCode, ResultListEntity<T> list) {
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
        this.list = list;
    }

    public ResultEntity(ServerCode serverCode, String message, ResultListEntity<T> list) {
        this.code = serverCode.getCode();
        this.msg = message;
        this.list = list;
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
        list = CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list;
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, new ResultListEntity(list.size(), list));
    }

    public static <T> ResultEntity<T> success(String message, List<T> list) {
        list = CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list;
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, new ResultListEntity(list.size(), list));
    }

    public static <T> ResultEntity success(PageInfo pageInfo) {
        ResultPageEntity pageEntity = BeanUtil.copyBean(pageInfo, ResultPageEntity.class);
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, new ResultListEntity(pageEntity.getTotal(), pageEntity.getList()));
    }

    public static <T> ResultEntity success(String message, PageInfo pageInfo) {
        ResultPageEntity pageEntity = BeanUtil.copyBean(pageInfo, ResultPageEntity.class);
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, new ResultListEntity(pageEntity.getTotal(), pageEntity.getList()));
    }

    /**
     * 判断是否成功
     * @return true or false
     */
    public boolean isSuccess() {
        return GlobalServerCodeEnum.SUCCESS.getCode().equals(this.code);
    }
}
