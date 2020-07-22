package com.destiny.common.entity;

import com.destiny.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求参数基类
 * @Author Destiny
 * @Version 1.0.0
 */
@Data
public class BasePageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", example = CommonConstant.DEFAULT_PAGE_NUM + "")
    private int pageNum = CommonConstant.DEFAULT_PAGE_NUM;
    /**
     * 每页显示的条数
     */
    @ApiModelProperty(value = "每页显示的条数", example = CommonConstant.DEFAULT_PAGE_SIZE + "")
    private int pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
}
