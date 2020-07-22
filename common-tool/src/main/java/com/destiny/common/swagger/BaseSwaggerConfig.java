package com.destiny.common.swagger;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger 配置基类
 * (默认路径 swagger-ui.html)
 * @Author Destiny
 * @Version 1.0.0
 */
public class BaseSwaggerConfig {
    /**
     * 构建 Docket - path包含型
     * @param isSwaggerEnable 是否启用swagger true:是 false:否
     * @param projectName 项目名，建议中文描述
     * @param version 版本号
     * @param description 接口文档描述，如当前版本接口文档变更简述
     * @param includePathRegexArray 包含所需产生接口文档的url路径，可多个
     * @return
     */
    public static Docket buildDocketWithIncludePaths(boolean isSwaggerEnable, String projectName, String version,
                                                     String description, String... includePathRegexArray) {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isSwaggerEnable)
                .apiInfo(new BaseApiInfoBuilder(projectName, version, description).build())
                .select()
                .paths(BasePathSelectors.includePathSelectors(includePathRegexArray))
                .build();
    }

    /**
     * 构建 Docket - path排除型
     * @param projectName 项目名，建议中文描述
     * @param version 版本号
     * @param description 接口文档描述，如当前版本接口文档变更简述
     * @param excludePathRegexArray 排除不需产生接口文档的url路径，可多个
     * @return
     */
    public static Docket buildDocketWithExcludePaths(boolean isSwaggerEnable, String projectName, String version,
                                                     String description, String... excludePathRegexArray) {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isSwaggerEnable)
                .apiInfo(new BaseApiInfoBuilder(projectName, version, description).build())
                .select()
                .paths(BasePathSelectors.excludePathSelectors(excludePathRegexArray))
                .build();
    }

}
