package com.destiny.common.entity;

import com.destiny.common.enumeration.GlobalServerCodeEnum;
import com.destiny.common.enumeration.ServerCode;
import com.destiny.common.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * a result entity
 *
 * example:
 * 1) Api -(package the result)-> ResultEntity -> HttpResponseEntity -> Response
 * 2) Handler -(package the result)-> ResultEntity -> service
 *
 * @Author Destiny
 * @Version 1.0.0
 */
@ApiModel(value = "ResultEntity", description = "the result entity of service handling")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "server code")
    @JsonIgnore
    private ServerCode serverCode;
    @ApiModelProperty(value = "the result status of handling")
    private String code;

    @ApiModelProperty(value = "the result of handling")
    private String message;

    @ApiModelProperty(value = "the data is handled and to be output")
    private T data;

    /**
     * the list is handled and to be output
     * success(List<T> list) / success(String message, List<T> list)
     */
    @ApiModelProperty(value = "业务结果内容列表")
    private List<T> list;

    /**
     * the page info is handled and to be output
     * success(PageInfo pageInfo) / success(String message, PageInfo pageInfo)
     */
    @ApiModelProperty(value = "业务结果内容分页列表")
    private PageEntity<T> pageList;

    public ResultEntity() {
    }

    public ResultEntity(ServerCode serverCode) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
    }

    public ResultEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntity(ServerCode serverCode, String message) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = message;
    }

    public ResultEntity(ServerCode serverCode, T result) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
        this.data = result;
    }

    public ResultEntity(ServerCode serverCode, String message, T result) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = message;
        this.data = result;
    }

    public ResultEntity(ServerCode serverCode, List<T> list) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
        this.list = list;
    }

    public ResultEntity(ServerCode serverCode, String message, List<T> list) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = message;
        this.list = list;
    }

    public ResultEntity(ServerCode serverCode, PageEntity<T> pageList) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
        this.pageList = pageList;
    }

    public ResultEntity(ServerCode serverCode, String message, PageEntity<T> pageList) {
        this.serverCode = serverCode;
        this.code = serverCode.getCode();
        this.message = message;
        this.pageList = pageList;
    }

    public static ResultEntity success() {
        return new ResultEntity(GlobalServerCodeEnum.SUCCESS);
    }

    public static ResultEntity failure() {
        return new ResultEntity(GlobalServerCodeEnum.UNKNOWN_EXCEPTION);
    }

    public static ResultEntity failure(ServerCode serverCode) {
        return new ResultEntity(GlobalServerCodeEnum.SUCCESS == serverCode ? GlobalServerCodeEnum.UNKNOWN_EXCEPTION : serverCode);
    }

    public static ResultEntity failure(ServerCode serverCode, String message) {
        return new ResultEntity(GlobalServerCodeEnum.SUCCESS == serverCode ? GlobalServerCodeEnum.UNKNOWN_EXCEPTION : serverCode, message);
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

    public static <T> ResultEntity success(PageInfo pageInfo, Class<T> targetClass) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, BeanUtil.copyPage(pageInfo, targetClass));
    }

    public static <T> ResultEntity success(String message, PageInfo pageInfo, Class<T> targetClass) {
        return new ResultEntity<>(GlobalServerCodeEnum.SUCCESS, message, BeanUtil.copyPage(pageInfo, targetClass));
    }

    /**
     * check the result is success or not
     * @return true-success; false-failure
     */
    @JsonIgnore
    public boolean isSuccess() {
        return GlobalServerCodeEnum.SUCCESS == this.serverCode;
    }
}
