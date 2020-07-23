# Log Tool
Log tool is used to rid sensitive data and format to json.

### Log Desensitization
1. Can use `@SensitiveData` to mark entity field be a sensitive data.
2. To rid sensitive data in log message.
### Log JSON formatted
1. Custom JSON formatted output for message content of logback log
2. Desensitization of English names is not currently supported.

# Converted Result
### Desensitization Result
> Default force match format - [@key:value@] - Format can be customized
```
1. phone - @phone:13112341234@
2. China id card number - @idCard:11010120100307889X@
3. back card number - @bankCard:6222600260001072444@
4. Chinese Name - @cnName:王老五@
```
> Default rules - Rules can be customized
1. phone - keep 3 digits at the head, and 4 digits at the tail
2. China id card number - keep 3 digits at the head, and 4 digits at the tail
3. back card number - keep 4 digits at the head, and 4 digits at the tail
4. Chinese Name - keep family name

> Desensitization Result
```
1. phone - 131****1234
2. China id card number - 110***********889X
3. back card number - 6222***********2444
4. Chinese Name - 王**
```

### JSON Formatted Result
> Default Rules
```
1. ERROR: {"exception":"XXXException","desc":"an error log description"}
2. WARN: {"method":"XXXMethod","params":"params","desc":"a warn log description"}
3. INFO: {"method":"XXXMethod","params":"params","desc":"an info log description"}
```
> JSON Formatted Result
```
1. ERROR: {"exception":"java.lang.NumberFormatException","desc":"This is an error"}
2. WARN: {"method":"main","params":"{\"param1\":\"This is a param\"}","desc":"This is a warn"}
3. INFO: {"method":"main","params":"{\"customKey\":\"This is a param\"}","desc":"This is an info"}
```

# Dependencies
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

<!-- Used in [logback.xml] - [jsonFactoryDecorator], Remove this dependency and logback configuration synchronously if not required -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>6.2</version>
</dependency>
```

# Usage
### Logback Config
Add `<conversionRule>` under the configuration label
1. Desensitization-only configuration
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.SensitiveDataConverter"/>
```
2. JSON-only configuration
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.LogJSONConverter"/>
```
3. Desensitization & JSON configuration
```
<conversionRule conversionWord="msg" converterClass="com.destiny.log.filter.CustomConverter"/>
```
* PS: `conversionWord` is `msg` corresponding to pattern's msg
* Note that you need to add it before `appender`.

4. For ELK - logstash filter - json filter
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

### Desensitization demo
1. com.destiny.log.RidSensitiveTest run the method of main()
2. We can see the results at log/xxx.log

### JSON Formatted demo
1. com.destiny.log.LogFormatTest run the method of main()
2. We can see the results at log/xxx.log

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
3. run any log print test to see the result.