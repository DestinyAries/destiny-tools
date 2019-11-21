package com.destiny.log.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSON;
import com.destiny.log.LogParamEntity;

import java.util.List;

/**
 * logback 日志JSON格式化转换器
 * 默认格式:
 * 1. ERROR - {"exception":"XXXException","desc":"formattedMessage"}
 * 2. WARN - {"method":"XXXMethod","params":"params JSON string","desc":"formattedMessage"}
 * 3. INFO - {"method":"XXXMethod","params":"params JSON string","desc":"formattedMessage"}
 * @Author linwanrong
 * @Date 2019/9/12
 */
public class LogJSONConverter extends MessageConverter {
    /**
     * 日志描述
     */
    private final static String DESC_KEY = "desc";
    /**
     * ERROR 日志的所属异常名
     */
    private final static String EXCEPTION_KEY = "exception";
    /**
     * 参数内容
     */
    private final static String PARAMS_KEY = "params";
    /**
     * 日志所在的方法名
     */
    private final static String METHOD_KEY = "method";

    @Override
    public String convert(ILoggingEvent event) {
        if (null != event && null != event.getLevel()) {
            switch (event.getLevel().levelInt) {
                case Level.ERROR_INT:
                    String exception = (null == event.getThrowableProxy() || null == event.getThrowableProxy().getClassName())
                            ? null : event.getThrowableProxy().getClassName();
                    return new LogParamEntity(EXCEPTION_KEY, exception)
                            .append(DESC_KEY, event.getFormattedMessage()).toJSONString();
                case Level.WARN_INT:
                case Level.INFO_INT:
                    return handleInfoLog(event.getMessage(), event.getArgumentArray());
            }
        }
        return event.getFormattedMessage();
    }

    /**
     * 获取参数
     * @param argumentArray
     * @param isRemoveFirstArg 是否将第一个参数移出参数列表 true:是  false:否
     * @return
     */
    protected String getParams(Object[] argumentArray, boolean isRemoveFirstArg) {
        if (null == argumentArray)
            return null;

        LogParamEntity paramEntity = new LogParamEntity();
        int i = isRemoveFirstArg ? 1 : 0;
        if (i > argumentArray.length - 1)
            return null;

        Object firstParam = argumentArray[i];
        List<String> keyList = null;
        if (null != firstParam && firstParam instanceof String) {
            keyList = LogParamEntity.getKeys((String) firstParam);
            if (null != keyList && keyList.size() > 0) {
                i++;
            }
        }
        for (int j = 0; i < argumentArray.length; i++,j++) {
            if (null != keyList) {
                paramEntity.append(j > keyList.size() - 1 ? null : keyList.get(j), argumentArray[i]);
            } else {
                paramEntity.append(argumentArray[i]);
            }
        }
        return paramEntity.getParams().size() > 0 ? paramEntity.toJSONString() : null;
    }

    /**
     * 处理信息日志
     * @param method
     * @param argumentArray
     * @return
     */
    private String handleInfoLog(String method, Object[] argumentArray) {
        if (null == argumentArray || argumentArray.length < 1)
            return new LogParamEntity(METHOD_KEY, null).append(DESC_KEY, method).append(PARAMS_KEY, null).toJSONString();

        String desc = (null != argumentArray[0] && argumentArray[0] instanceof String)
                ? (String) argumentArray[0] :
                (null == argumentArray[0] ? null : JSON.toJSONString(argumentArray[0]));
        return new LogParamEntity(METHOD_KEY, method).append(DESC_KEY, desc)
                .append(PARAMS_KEY, getParams(argumentArray, true)).toJSONString();
    }

}
