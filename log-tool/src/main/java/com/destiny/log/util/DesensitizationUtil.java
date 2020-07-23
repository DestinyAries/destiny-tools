package com.destiny.log.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.destiny.log.annotation.SensitiveData;
import com.destiny.log.enums.SensitiveDataTypeEnum;

import java.lang.reflect.Field;

/**
 * 敏感数据脱敏工具
 * @Author linwanrong
 * @Date 2019/9/12
 */
public class DesensitizationUtil {
    /**
     * 屏蔽符 - 星号
     */
    private static final char SHIELD_SYMBOL =  '*';
    /**
     * SensitiveDataTypeEnum.formatRule's '{'
     */
    private static final char DATA_FORMAT_RULE_MATCH_LEFT =  '{';
    /**
     * SensitiveDataTypeEnum.formatRule's '}'
     */
    private static final char DATA_FORMAT_RULE_MATCH_RIGHT =  '}';

    /**
     * 全文数据脱敏 - 强匹配
     * @param text
     * @return
     */
    public static String convertContext(final String text) {
        if (StrUtil.isBlank(text))
            return text;

        String handledText = text;
        for (SensitiveDataTypeEnum typeEnum : SensitiveDataTypeEnum.values()) {
            handledText = convertText(handledText, typeEnum);
        }
        return handledText;
    }

    /**
     * 指定枚举进行单类型数据 - 值脱敏
     * @param originMsg
     * @param typeEnum
     * @return
     */
    public static String convertValue(final String originMsg, final SensitiveDataTypeEnum typeEnum) {
        return StrUtil.isBlank(originMsg) || null == typeEnum ? originMsg :
                ReUtil.replaceAll(originMsg, typeEnum.getRegexRule(), matcher -> {
                    if (originMsg.length() > matcher.group().length())
                        return matcher.group();
                    return DesensitizationUtil.starDataByRule(matcher.group(), typeEnum.getHeadKeepLength(),
                            typeEnum.getTailKeepLength()
                    );
                });
    }

    /**
     * 指定枚举进行单类型数据 - 文本脱敏
     * @param originMsg
     * @param typeEnum
     * @return
     */
    public static String convertText(String originMsg, SensitiveDataTypeEnum typeEnum) {
        return StrUtil.isBlank(originMsg) || null == typeEnum ? originMsg :
                ReUtil.replaceAll(originMsg,
                        StrUtil.format(typeEnum.getFormatRule(), typeEnum.getRegexRule()),
                        matcher -> DesensitizationUtil.starDataByRule(
                                getSensitiveDataByRule(matcher.group(), typeEnum),
                                typeEnum.getHeadKeepLength(),
                                typeEnum.getTailKeepLength()
                        )
                );
    }

    /**
     * 处理声明了敏感数据的Object
     * @param value
     * @return
     */
    public static Object handleSensitiveDataAnnotation(Object value) {
        if (null == value)
            return null;

        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (null != field && field.isAnnotationPresent(SensitiveData.class)
                    && null != field.getAnnotation(SensitiveData.class)
                    && null != field.getAnnotation(SensitiveData.class).value()) {
                // enter: 这是一个敏感数据
                SensitiveDataTypeEnum currentTypeEnum = field.getAnnotation(SensitiveData.class).value();
                Object originValue = ReflectUtil.getFieldValue(value, field);
                // 格式化敏感数据
                if (originValue instanceof String) {
                    String originValueStr = (String) originValue;
                    String result = convertValue(originValueStr, currentTypeEnum);
                    if (!originValueStr.equals(result)) {
                        ReflectUtil.setFieldValue(value, field, result);
                    }
                }
            }
        }
        return value;
    }

    /**
     * 根据规则进行数据脱敏
     * @param data 待脱敏数据
     * @param headKeepLength 头部保留位数
     * @param tailKeepLength 尾部保留位数
     * @return
     */
    public static String starDataByRule(String data, int headKeepLength, int tailKeepLength) {
        if (StrUtil.isEmpty(data) || headKeepLength + tailKeepLength >= data.length())
            return data;

        headKeepLength = headKeepLength < 0 ? 0 : headKeepLength;
        tailKeepLength = tailKeepLength < 0 ? 0 : tailKeepLength;
        StringBuilder builder = new StringBuilder(StrUtil.subWithLength(data, 0, headKeepLength));
        for (int i = 0; i < data.length() - headKeepLength - tailKeepLength; i++) {
            builder.append(SHIELD_SYMBOL);
        }
        return builder.append(StrUtil.subSuf(data, data.length() - tailKeepLength)).toString();
    }

    /**
     * 从强匹配规则中获取敏感数据
     * 默认强匹配格式 @key:value@, 切割后获取敏感数据 value
     * @param sensitiveData
     * @param currentTypeEnum
     * @return
     */
    public static String getSensitiveDataByRule(String sensitiveData, final SensitiveDataTypeEnum currentTypeEnum) {
        if (StrUtil.isBlank(sensitiveData) || null == currentTypeEnum || StrUtil.isBlank(currentTypeEnum.getFormatRule()))
            return sensitiveData;

        int headMatch = StrUtil.indexOf(currentTypeEnum.getFormatRule(), DATA_FORMAT_RULE_MATCH_LEFT);
        int tailMatch = StrUtil.indexOf(currentTypeEnum.getFormatRule(), DATA_FORMAT_RULE_MATCH_RIGHT);
        if (headMatch <= 0 || tailMatch <= 0)
            return sensitiveData;

        String prefix = currentTypeEnum.getFormatRule().substring(0, headMatch);
        String suffix = currentTypeEnum.getFormatRule().substring(tailMatch + 1);
        if (!sensitiveData.startsWith(prefix) || !sensitiveData.endsWith(suffix)) {
            return sensitiveData;
        }

        String ridPrefixStr = StrUtil.sub(sensitiveData, prefix.length(), sensitiveData.length());
        return StrUtil.sub(ridPrefixStr, 0, ridPrefixStr.length() - suffix.length());
    }
}
