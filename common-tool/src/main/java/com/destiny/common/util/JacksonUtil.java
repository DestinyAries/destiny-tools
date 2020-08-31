package com.destiny.common.util;

import cn.hutool.core.util.StrUtil;
import com.destiny.common.constant.SymbolConstant;
import com.destiny.common.jackson.JacksonException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Jackson 工具类
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class JacksonUtil {
    /**
     * ObjectMapper with default configuration
     */
    private final static ObjectMapper DEFAULT_OBJECT_MAPPER = defaultObjectMapper();

    private ObjectMapper objectMapper;

    public JacksonUtil() {
    }

    public JacksonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * use objectMapper bean in Spring if it is exists, otherwise
     * use the default objectMapper.
     * @return
     */
    public static JacksonUtil of() {
        return new JacksonUtil();
    }

    /**
     * use this toolkit by custom ObjectMapper
     * @param objectMapper
     * @return
     */
    public static JacksonUtil of(ObjectMapper objectMapper) {
        return new JacksonUtil(objectMapper);
    }

    /**
     * 获取序列化
     * @return
     */
    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 反序列化时候遇到不匹配的属性并不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时候遇到空对象不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 不使用默认的dateTime进行序列化
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, LocalDateTimeUtil.localDateTimeSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        timeModule.addDeserializer(LocalDateTime.class, LocalDateTimeUtil.localDateTimeDeserializer());
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }

    /**
     * can use to the redis configuration when saving Mybatis cache
     * @return
     */
    public static ObjectMapper getObjectMapperByActivateDefaultTyping() {
        ObjectMapper objectMapper = defaultObjectMapper();
        // 启用反序列化所需的类型信息,在属性中添加@class
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

    /**
     * 是否为JSON字符串，首尾都为大括号或中括号判定为JSON字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     */
    public static boolean isJson(String str) {
        return isJsonObject(str) || isJsonArray(str);
    }

    /**
     * 是否为JSONObject字符串，首尾都为大括号判定为JSONObject字符串
     *
     * @param string 字符串
     * @return 是否为JSON字符串
     */
    public static boolean isJsonObject(String string) {
        return StrUtil.isBlank(string) ? false :
                StrUtil.isWrap(string.trim(), SymbolConstant.OPEN_BRACE_CHAR, SymbolConstant.CLOSE_BRACE_CHAR);
    }

    /**
     * 是否为JSONArray字符串，首尾都为中括号判定为JSONArray字符串
     *
     * @param string 字符串
     * @return 是否为JSON字符串
     */
    public static boolean isJsonArray(String string) {
        return StrUtil.isBlank(string) ? false :
                StrUtil.isWrap(string.trim(), SymbolConstant.OPEN_BRACKET_CHAR, SymbolConstant.CLOSE_BRACKET_CHAR);
    }

    /**
     * Parsing a JSON string to a Java object
     * @param json a JSON string
     * @param objectClass target Java object class
     * @return
     */
    public <T> T toObject(String json, Class<T> objectClass) {
        requireClassNonNull(objectClass);
        requireJsonString(json);
        try {
            return getObjectMapper().readValue(json, objectClass);
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON file to a Java object
     * @param jsonFile a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> T toObject(File jsonFile, Class<T> objectClass) {
        requireClassNonNull(objectClass);
        requireLegalFile(jsonFile);
        try {
            return getObjectMapper().readValue(jsonFile, objectClass);
        } catch (IOException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
            throw JacksonException.throwWhenIOError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java object array
     * @param jsonArray a array json string
     * @return
     */
    public Object[] toArray(String jsonArray) {
        requireJsonArrayString(jsonArray);
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<Object[]>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java array", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java string array
     * @param jsonArray a array json string
     * @return
     */
    public String[] toStringArray(String jsonArray) {
        requireJsonArrayString(jsonArray);
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<String[]>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a string array", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java string array
     * @param jsonArray a array json string
     * @return
     */
    public Integer[] toIntegerArray(String jsonArray) {
        requireJsonArrayString(jsonArray);
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<Integer[]>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a string array", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java map array
     * @param jsonArray a array json string
     * @return
     */
    public Map[] toMapArray(String jsonArray) {
        requireJsonArrayString(jsonArray);
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<Map[]>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a map array", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java object list
     * @param jsonList a list json string
     * @return
     */
    public List<Object> toList(String jsonList) {
        requireJsonArrayString(jsonList);
        try {
            return getObjectMapper().readValue(jsonList, new TypeReference<List>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java list", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON array string to a Java object list
     * It will check the class type strict.
     *
     * @param jsonList a list json string
     * @param objectClass target Java object class
     * @return
     */
    public <T> List<T> toList(String jsonList, Class<T> objectClass) {
        requireClassNonNull(objectClass);
        requireJsonArrayString(jsonList);
        List list;
        try {
            list = getObjectMapper().readValue(jsonList, new TypeReference<List>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java list", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
        // check class type
        if (list != null && list.stream().anyMatch(v -> !objectClass.isInstance(v))) {
            log.error("class cast error. expected:[{}], actual:[{}]", objectClass.getName(),
                    list.stream().map(v -> v.getClass().getName()).distinct().collect(Collectors.joining(SymbolConstant.COMMA)));
            throw JacksonException.throwWhenClassCastError();
        }
        return (List<T>) list;
    }

    /**
     * Parsing a JSON string to a Map
     * @param json a JSON string
     * @return
     */
    public Map<Object, Object> toMap(String json) {
        requireJsonObjectString(json);
        try {
            return getObjectMapper().readValue(json, new TypeReference<Map<Object, Object>>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java Map", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a JSON string to a Map
     * @param json a JSON string
     * @return
     */
    public <T> Map<T, Object> toMap(String json, Class<T> keyClass) {
        requireClassNonNull(keyClass);
        requireJsonObjectString(json);
        Map map;
        try {
            map = getObjectMapper().readValue(json, new TypeReference<Map>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java Map", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
        // check class type
        if (map != null && map.keySet().stream().anyMatch(v -> !keyClass.isInstance(v))) {
            log.error("class cast error. expected:[{}], actual:[{}]", keyClass.getName(),
                    map.keySet().stream().map(v -> v.getClass().getName()).distinct().collect(Collectors.joining(SymbolConstant.COMMA)));
            throw JacksonException.throwWhenClassCastError();
        }
        return (Map<T, Object>) map;
    }

    /**
     * Parsing a Java object to a JSON string
     * @param object
     * @return {@link String}
     */
    public String toJSON(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a Java object to a JSON string", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    /**
     * Parsing a Java object to a Java string
     * @param object
     * @return
     */
    public String toJavaString(Object object) {
        try {
            String jsonString = getObjectMapper().writeValueAsString(object);
            if (StrUtil.isNotEmpty(jsonString) && StrUtil.startWith(jsonString, "\"")
                    && StrUtil.endWith(jsonString, "\"")) {
                return jsonString.substring(1, jsonString.length() - 1);
            }
            return jsonString;
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a Java object to a Java string", e);
            throw JacksonException.throwWhenProcessToJavaError(e);
        }
    }

    private ObjectMapper getObjectMapper() {
        return this.objectMapper == null ? DEFAULT_OBJECT_MAPPER : this.objectMapper;
    }

    /**
     * Checks that the Class object is not {@code null}
     *
     * @param objectClass the Class object to check for nullity
     * @throws JacksonException if {@code obj} is {@code null}
     */
    private void requireClassNonNull(Class objectClass) {
        if (objectClass == null) {
            log.error("require object class not null");
            throw JacksonException.throwWhenObjectClassNull();
        }
    }

    /**
     * Checks that the string is a json string or not.
     * @param json
     */
    private void requireJsonString(String json) {
        if (!isJson(json)) {
            log.error("the parameter is not a json string. parameter[{}]", json);
            throw JacksonException.throwWhenNotAJson();
        }
    }

    /**
     * Checks that the json string content is array or not.
     * @param json
     */
    private void requireJsonArrayString(String json) {
        if (!isJsonArray(json)) {
            log.error("the json string content is not array. parameter[{}]", json);
            throw JacksonException.throwWhenNotAJson();
        }
    }

    /**
     * Checks that the json string content is object or not.
     * @param json
     */
    private void requireJsonObjectString(String json) {
        if (!isJsonObject(json)) {
            log.error("the json string content is not object. parameter[{}]", json);
            throw JacksonException.throwWhenNotAJson();
        }
    }

    /**
     * Checks that the File is not {@code null}, and exists, and
     * is a normal file, and the application can read the file denoted by this abstract pathname.
     * @param jsonFile
     */
    private void requireLegalFile(File jsonFile) {
        if (jsonFile == null || !jsonFile.exists() || !jsonFile.isFile() || !jsonFile.canRead()) {
            log.error("file is not exists, or not a file, or can not read");
            throw JacksonException.throwWhenFileIllegal();
        }
    }
}
