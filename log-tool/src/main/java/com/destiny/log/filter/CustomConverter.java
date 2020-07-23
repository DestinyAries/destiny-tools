package com.destiny.log.filter;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.destiny.log.util.DesensitizationUtil;

/**
 * logback 日志 JSON 格式化 & 数据脱敏 - 转换器(可定制化)
 * 默认设置处理步骤为:
 * 1. 处理注解的脱敏对象
 * 2. LogJSONConverter
 * 3. 数据脱敏
 * @Author linwanrong
 * @Date 2019/9/21
 */
public class CustomConverter extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        try {
            // 扫描并处理标注脱敏的对象
            Object[] arguments = event.getArgumentArray();
            if (null != event && null != arguments) {
                for (Object argument : arguments) {
                    DesensitizationUtil.handleSensitiveDataAnnotation(argument);
                }
            }
            LogJSONConverter jsonConverter = new LogJSONConverter();
            // JSON格式化 -> 数据脱敏
            return DesensitizationUtil.convertContext(jsonConverter.convert(event));
        } catch (Exception e) {
            e.printStackTrace();
            return event.getFormattedMessage();
        }
    }
}
