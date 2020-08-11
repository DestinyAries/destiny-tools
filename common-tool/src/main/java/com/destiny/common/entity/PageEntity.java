package com.destiny.common.entity;

import com.destiny.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页实体
 * @Author Destiny
 * @Version 1.0.0
 */
@ApiModel(value = "分页实体")
@Data
public class PageEntity<T> {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private int pageNum;
    /**
     * 每页显示的条数
     */
    @ApiModelProperty(value = "每页显示的条数")
    private int pageSize;
    /**
     * 当前页的实际条数
     */
    @ApiModelProperty(value = "当前页的实际条数")
    private int size;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int pages;
    /**
     * 下一页
     */
    @ApiModelProperty(value = "下一页")
    private int nextPage;
    /**
     * 上一页
     */
    @ApiModelProperty(value = "上一页")
    private int prePage;
    /**
     * 第一页
     */
    @ApiModelProperty(value = "第一页")
    private int navigateFirstPage;
    /**
     * 最后一页
     */
    @ApiModelProperty(value = "最后一页")
    private int navigateLastPage;
    /**
     * 当前页面第一个元素在数据库中的行号
     */
    @ApiModelProperty(value = "当前页面第一个元素在数据库中的行号")
    private int startRow;
    /**
     * 当前页面最后一个元素在数据库中的行号
     */
    @ApiModelProperty(value = "当前页面最后一个元素在数据库中的行号")
    private int endRow;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("结果列表")
    private List<T> list;

    public PageEntity() {
    }

    public PageEntity(long total, List<T> list) {
        this.pageNum = CommonConstant.DEFAULT_PAGE_NUM;
        this.pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        this.total = total;
        this.list = list;
    }

    public PageEntity(int pageNum, int pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
