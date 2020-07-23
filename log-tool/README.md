# 日志工具
### 日志脱敏
1. 提供注解 `@SensitiveData` 对实体的敏感字段进行敏感数据标记
2. 对 logback 日志数据进行脱敏过滤
### 日志JSON化
1. 对 logback 日志的 message 内容进行自定义JSON格式化输出

# 使用效果
### 日志脱敏使用效果
> 强匹配默认格式 - [@key:value@] - 格式可自定义
```
1. 手机号(phone) - @phone:13112341234@
2. 身份证号(China id card number) - @idCard:11010120100307889X@
3. 银行卡号(back card number) - @bankCard:6222600260001072444@
4. 中文姓名(Chinese Name) - @cnName:王老五@
5. 6位数验证码(VCode Num) - @VCodeNum6:392031@
```
> 脱敏默认规则 - 规则可自定义
1. 手机号(phone) - 保留前三位后四位
2. 身份证号(China id card number) - 保留前三位后四位
3. 银行卡号(back card number) - 保留前四位后四位
4. 姓名(Chinese Name) - 留姓不留名
5. 6位数验证码(VCode Num) - 保留前一位后一位

> 脱敏效果
```
1. 手机号(phone) - 131****1234
2. 身份证号(China id card number) - 110***********889X
3. 银行卡号(back card number) - 6222***********2444
4. 中文姓名(Chinese Name) - 王**
5. 6位数验证码(VCode Num) - 3****1
```

> 【注意】
> 1. 若不符合匹配规则，则会原数据输出，不做脱敏处理
> 2. 已注解的实体字段，若值为null，则不打印

### 日志JSON化使用效果
> JSON化默认规则
```
1. ERROR: {"exception":"XXXException","desc":"错误日志描述"}
2. WARN: {"method":"XXXMethod","params":"参数","desc":"警告日志描述"}
3. INFO: {"method":"XXXMethod","params":"参数","desc":"信息日志描述"}
```
> JSON化效果
```
1. ERROR: {"exception":"java.lang.NumberFormatException","desc":"错误日志描述"}
2. WARN: {"method":"main","params":"{\"param1\":\"这是一个参数\"}","desc":"我是一个警告日志的描述"}
3. INFO: {"method":"main","params":"{\"customKey\":\"这是一个参数\"}","desc":"我是一个信息日志的描述"}
```

# 依赖 | Dependencies
```
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-core</artifactId>
    <version>4.6.4</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.10</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.58</version>
    <scope>provided</scope>
</dependency>

<!-- 用于logback.xml的jsonFactoryDecorator，不需要此功能则同步去掉此依赖及logback对应的配置 -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>6.2</version>
</dependency>
```

# 用法
### Logback 配置
在 configuration 标签下加上 `<conversionRule>`
1. 仅日志脱敏的配置
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.SensitiveDataConverter"/>
```
2. 仅日志 message - JSON化的配置
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.LogJSONConverter"/>
```
3. 日志脱敏+日志 message JSON化的配置
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.CustomConverter"/>
```
* 其中，conversionWord 为对应 pattern 的 msg。
* 注意，需要加在 appender 之前。

4. 日志脱敏 + 日志 message JSON化 + 日志所有内容 JSON 化(For ELK - logstash filter - json filter)
```
<!-- logback.xml -->
<encoder charset="UTF-8" class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
    <jsonFactoryDecorator class="com.destiny.log.decorator.JsonFactoryDecorator"/>
    <providers>
        <pattern>
            <pattern>
                {
                    "timestamp":"%d{yyyy-MM-dd HH:mm:ss.SSS}",
                    "level": "%level",
                    "thread": "%thread",
                    "class": "%logger{60}",
                    "message": "%message",
                    "service_name": "${service_name}",
                    "stack_trace": "%exception{10}"
                }
            </pattern>
        </pattern>
    </providers>
</encoder>
```

### 日志脱敏 demo
1. com.destiny.log.RidSensitiveTest 执行 main()
2. 可在 log/xxx.log 下看结果

### 日志 JSON 化 demo
1. com.destiny.log.LogFormatTest 执行 main()
2. 可在 log/xxx.log 下看结果

### For ELK demo
1. pom.xml
```
<!-- [Only for Test] Match ELK's LogStash filter configuration -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>6.2</version>
    <scope>test</scope>
</dependency>
```
2. logback.xml
```
<!-- logback.xml -->
<encoder charset="UTF-8" class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
    <jsonFactoryDecorator class="com.destiny.log.decorator.JsonFactoryDecorator"/>
    <providers>
        <pattern>
            <pattern>
                {
                    "timestamp":"%d{yyyy-MM-dd HH:mm:ss.SSS}",
                    "level": "%level",
                    "thread": "%thread",
                    "class": "%logger{60}",
                    "message": "%message",
                    "service_name": "${service_name}",
                    "stack_trace": "%exception{10}"
                }
            </pattern>
        </pattern>
    </providers>
</encoder>
```
3. 执行任意日志打印测试类可看效果
