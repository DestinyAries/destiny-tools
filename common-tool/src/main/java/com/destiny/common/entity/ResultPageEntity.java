package com.destiny.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页实体
 * @Author linwanrong
 * @Date 2020/7/20 17:58
 */
@Data
public class ResultPageEntity<T> extends ResultListEntity<T> {
    /**
     * 默认分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 100;
    /**
     * 默认页码
     */
    private static final int DEFAULT_PAGE = 1;

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

//    //是否为第一页
//    private boolean isFirstPage = false;
//    //是否为最后一页
//    private boolean isLastPage = false;
//    //是否有前一页
//    private boolean hasPreviousPage = false;
//    //是否有下一页
//    private boolean hasNextPage = false;
//    //导航页码数
//    private int navigatePages;
//    //所有导航页号
//    private int[] navigatepageNums;

    /**
     * 开始记录 从第几条记录开始算
     */
    @ApiModelProperty(value = "开始记录")
    private Integer startIndex = Integer.valueOf(0);

    public ResultPageEntity(long total, List<T> list) {
        super(DEFAULT_PAGE, DEFAULT_PAGE_SIZE, total, list);
    }

    public ResultPageEntity(int pageNum, int pageSize, long total, List<T> list) {
        super(pageNum, pageSize, total, list);
    }

}
