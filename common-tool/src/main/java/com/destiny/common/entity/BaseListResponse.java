package com.destiny.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 列表响应实体 - 用于分页响应
 * Use to {@link PageResponse}
 * @Author Destiny
 * @Version 1.0.0
 */
@ApiModel("列表结果")
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class BaseListResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("结果列表")
    private List<T> list;

    public BaseListResponse(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public BaseListResponse(int pageNum, int pageSize, long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
