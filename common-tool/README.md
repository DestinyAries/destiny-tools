# common-tool
## web tool

## swagger tool

### 所需依赖
```
<dependency>
    <groupId>com.destimy</groupId>
    <artifactId>swagger-tool</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 用法
##### [1] application 属性文件设置开关
生产环境不启用，应为 false
```
swagger.enable=true
```
##### [2]  新建配置类
buildDocketWithIncludePaths 参数说明：
* isSwaggerEnable : 是否启用swagger。读取配置文件的设置
* projectName: 项目名称，中文项目名称简述
* version: 版本号
* description: 版本描述，中文简述该版本接口的变动
* includePathRegexArray: 包含所需产生接口文档的url路径，可单个也可多个
```
import com.destiny.swagger.BaseSwaggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean isSwaggerEnable = false;

    @Bean
    public Docket buildSwagger() {
        return BaseSwaggerConfig.buildDocketWithIncludePaths(isSwaggerEnable, "测试项目",
                "1.1.0", "这个版本的变动是：xxxxxx", "/api/.*");
    }
}
```

### url path 匹配说明
##### [1] 包含型
```
例如 url:
1. /api/xxx
2. /ext/xxx
3. /demo/xxx

BaseSwaggerConfig.buildDocketWithIncludePaths(isSwaggerEnable,"测试项目",
                "1.1.0", "这个版本的变动是：xxxxxx", "/api/.*");
                
则，包含型的只会生成 /api/xxx 下的接口文档
```

##### [2] 排除型
```
例如 url:
1. /api/xxx
2. /ext/xxx
3. /demo/xxx

BaseSwaggerConfig.buildDocketWithExcludePaths(isSwaggerEnable,"测试项目",
                "1.1.0", "这个版本的变动是：xxxxxx", "/api/.*");
                
则，排除型的生成的是 /ext/xxx 和 /demo/xxx 下的接口文档
```
