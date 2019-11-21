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
     * 全文数据脱敏 - 强匹配
     * @param message
     * @return
     */
    public static String convertMessage(String message) {
        return StrUtil.isBlank(message) ? message
                : ridSensitiveData(message, 0, SensitiveDataTypeEnum.values().length);
    }

    /**
     * 单类型数据脱敏 - 指定枚举(默认强匹配)
     * @param message
     * @param typeEnum
     * @return
     */
    public static String convertMessage(String message, SensitiveDataTypeEnum typeEnum) {
        return StrUtil.isBlank(message) || null == typeEnum ? message
                : ridSensitiveDataByEnum(message, typeEnum, true);
    }

    /**
     * 单类型数据脱敏 - 指定枚举、匹配强度
     * @param message
     * @param isNumberForceMatch true: 强匹配; false: 弱匹配
     * @param typeEnum
     * @return
     */
    public static String convertMessage(String message, boolean isNumberForceMatch, SensitiveDataTypeEnum typeEnum) {
        return StrUtil.isBlank(message) || null == typeEnum ? message
                : ridSensitiveDataByEnum(message, typeEnum, isNumberForceMatch);
    }

    /**
     * 全文数据脱敏 - 默认强匹配数据（因为弱匹配在全文脱敏无意义）
     * 1. 手机号 - @phone:13112341234@
     * 2. 身份证号 - @idCard:11010120100307889X@
     * 3. 银行卡号 - @bankCard:6222600260001072444@
     * 4. 姓名 - @cnName:张三@
     * @param originMsg
     * @param currentIndex
     * @param enumSize
     * @return
     */
    public static String ridSensitiveData(String originMsg, int currentIndex, int enumSize) {
        if (currentIndex < 0 || SensitiveDataTypeEnum.values().length < enumSize)
            return originMsg;

        return currentIndex > enumSize - 1 ? originMsg
                : ridSensitiveData(
                        ridSensitiveDataByEnum(
                                originMsg, SensitiveDataTypeEnum.values()[currentIndex], true),
                currentIndex + 1, enumSize);
    }

    /**
     * 单类型数据脱敏 - 指定枚举、匹配强度
     * @param originMsg
     * @param currentTypeEnum
     * @param isNumberForceMatch
     * @return
     */
    public static String ridSensitiveDataByEnum(String originMsg, SensitiveDataTypeEnum currentTypeEnum, boolean isNumberForceMatch) {
        if (StrUtil.isBlank(originMsg) || null == currentTypeEnum)
            return originMsg;

        String regex = isNumberForceMatch
                ? StrUtil.format(currentTypeEnum.getFormatRule(), currentTypeEnum.getRegexWeak())
                : currentTypeEnum.getRegexWeak();

        return ReUtil.replaceAll(originMsg, regex, matcher -> {
            if (!isNumberForceMatch && originMsg.length() > matcher.group().length())
                return matcher.group();

            return starDataByRule(
                    isNumberForceMatch ? getSensitiveDataByRule(matcher.group(), currentTypeEnum) : matcher.group(),
                    currentTypeEnum.getHeadKeepLength(),
                    currentTypeEnum.getTailKeepLength()
            );
        });
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
                    String result = DesensitizationUtil.convertMessage(originValueStr, false, currentTypeEnum);
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
            builder.append("*");
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

        int headMatch = StrUtil.indexOf(currentTypeEnum.getFormatRule(), '{');
        int tailMatch = StrUtil.indexOf(currentTypeEnum.getFormatRule(), '}');
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
