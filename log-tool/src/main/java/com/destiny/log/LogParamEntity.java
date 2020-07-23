package com.destiny.log;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.destiny.log.enums.SensitiveDataTypeEnum;
import com.destiny.log.util.DesensitizationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志参数实体类
 * 可用于 ELK - logstash 的 json filter
 * @Author linwanrong
 * @Date 2019/9/18
 */
public class LogParamEntity {
    /**
     * 参数自定义key格式 - #format#key1{}key2{} ...
     */
    public final static String PARAM_FORMAT_PREFIX = "#format#";
    /**
     * 参数自定义key为空的标识
     */
    public final static String PARAM_FORMAT_NULL = "#null#";

    private Map<String, Object> params = new HashMap<>();

    public LogParamEntity() {
    }

    public LogParamEntity(Object value) {
        params.put(getKey(null), getValue(value));
    }

    public LogParamEntity(String key, Object value) {
        params.put(getKey(key), getValue(value));
    }

    /**
     * 自定义params参数内容的key
     * @param keys
     * @return
     */
    public static String setKeys(String... keys) {
        if (null == keys || keys.length < 1) {
            return null;
        }
        StringBuilder builder = new StringBuilder(PARAM_FORMAT_PREFIX);
        for (String key : keys) {
            builder.append(StrUtil.isBlank(key) ? PARAM_FORMAT_NULL : key).append("{}");
        }
        return builder.toString();
    }

    /**
     * 获取自定义key列表
     * @param format
     * @return
     */
    public static List<String> getKeys(String format) {
        if (StrUtil.isBlank(format) || !format.startsWith(LogParamEntity.PARAM_FORMAT_PREFIX))
            return null;

        String keysString = format.substring(LogParamEntity.PARAM_FORMAT_PREFIX.length());
        String[] splitArray = StrUtil.split(keysString, "{}");
        List<String> keyList = null;
        if (splitArray.length > 1) {
            keyList = new ArrayList<>(splitArray.length);
            for (String key : splitArray) {
                if (StrUtil.isNotBlank(key)) {
                    keyList.add(key);
                }
            }
        }
        return keyList;
    }

    /**
     * 手机脱敏标记
     * @param phone
     * @return
     */
    public static String phone(String phone) {
        return StrUtil.isBlank(phone) ? ""
                : DesensitizationUtil.convertValue(phone, SensitiveDataTypeEnum.PHONE);
    }

    /**
     * 银行卡号脱敏标记
     * @param bankCard
     * @return
     */
    public static String bankCard(String bankCard) {
        return StrUtil.isBlank(bankCard) ? ""
                : DesensitizationUtil.convertValue(bankCard, SensitiveDataTypeEnum.BANK_CARD);
    }

    /**
     * 身份证号脱敏标记
     * @param idCard
     * @return
     */
    public static String idCard(String idCard) {
        return StrUtil.isBlank(idCard) ? ""
                : DesensitizationUtil.convertValue(idCard, SensitiveDataTypeEnum.ID_CARD);
    }

    /**
     * 姓名脱敏标记
     * @param cnName
     * @return
     */
    public static String cnName(String cnName) {
        return StrUtil.isBlank(cnName) ? ""
                : DesensitizationUtil.convertValue(cnName, SensitiveDataTypeEnum.CN_NAME);
    }

    /**
     * 6位纯数字型验证码
     * @param code
     * @return
     */
    public static String vCodeNum6(String code) {
        return StrUtil.isBlank(code) ? ""
                : DesensitizationUtil.convertValue(code, SensitiveDataTypeEnum.V_CODE_NUM_6);
    }

    /**
     * 追加参数
     * @param value
     * @return
     */
    public LogParamEntity append(Object value) {
        params.put(getKey(null), getValue(value));
        return this;
    }

    /**
     * 追加参数
     * @param key
     * @param value
     * @return
     */
    public LogParamEntity append(String key, Object value) {
        params.put(getKey(key), getValue(value));
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String toJSONString() {
        if (this.params.size() < 1) {
            return "";
        }
        if (this.params.size() == 1) {
            for (Map.Entry<String, Object> entry : this.params.entrySet()) {
                if (null != entry.getValue() && entry.getKey().startsWith("param")) {
                    return entry.getValue() instanceof String
                            ? (String) entry.getValue() : JSON.toJSONString(entry.getValue());
                }
            }
        }
        return JSON.toJSONString(this.params);
    }

    /**
     * 获取key
     * 处理 key 为空或已存在的情况，提供默认名命名，默认命名规则为: param + currentLocation
     * @param key
     * @return
     */
    protected String getKey(String key) {
        return StrUtil.isBlank(key) || PARAM_FORMAT_NULL.equals(key) || params.containsKey(key)
                ? "param" + (params.size() + 1) : key;
    }

    /**
     * 获取value
     * 处理 value 为 null 的情况，将其处理为空字符串，否则转为JSON字符串时会被弃掉
     * @param value
     * @return
     */
    private Object getValue(Object value) {
        if (null == value) {
            return "";
        }
        return value;
    }
}
