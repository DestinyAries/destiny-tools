package com.destiny.swagger;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;

import java.util.List;

/**
 * API信息构造基类
 * @Author linwanrong
 * @Date 2019/11/20 18:15
 */
public class BaseApiInfoBuilder extends ApiInfoBuilder {
    /**
     * 默认接口文档标题
     */
    private final static String DEFAULT_API_TITLE = "Api 接口文档";

    /**
     * 接口文档标题
     */
    private String title = DEFAULT_API_TITLE;
    /**
     * 接口文档描述
     */
    private String description = "Api 接口文档描述";
    /**
     * 接口文档版本
     */
    private String version = "1.0.0";

    private List<VendorExtension> vendorExtensions = Lists.newArrayList();

    public BaseApiInfoBuilder() {
    }

    public BaseApiInfoBuilder(String projectName, String version, String description) {
        if (StrUtil.isNotEmpty(projectName)) {
            this.title = new StringBuilder(projectName).append(" ").append(DEFAULT_API_TITLE).toString();
        }
        if (StrUtil.isNotEmpty(version)) {
            this.version = version;
        }
        if (StrUtil.isNotEmpty(description)) {
            this.description = description;
        }
    }

    public ApiInfo build() {
        return new ApiInfo(this.title, this.description, this.version, null, null, null, null, this.vendorExtensions);
    }
}
