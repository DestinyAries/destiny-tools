package com.destiny.log.filter;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.destiny.log.util.DesensitizationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * logback 日志脱敏转换器
 * @Author linwanrong
 * @Date 2019/9/12
 */
public class SensitiveDataConverter extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        Object[] arguments = event.getArgumentArray();
        List<Object> newArguments = new ArrayList<>(arguments.length);
        if (null != event && null != arguments) {
            for (Object argument : arguments) {
                DesensitizationUtil.handleSensitiveDataAnnotation(argument);
                newArguments.add(JSON.toJSONString(argument));
            }
        }
        // 强匹配模式日志数据脱敏
        return DesensitizationUtil.convertMessage(StrUtil.format(event.getMessage(), newArguments));
    }
}
