package com.destiny.common.util;

import cn.hutool.core.util.StrUtil;
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
import java.util.Objects;
import java.util.Optional;

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
     * Parsing a JSON string to a Java object
     * @param json a JSON string
     * @param objectClass target Java object class
     * @return
     */
    public <T> T toObject(String json, Class<T> objectClass) {
        Objects.requireNonNull(objectClass);
        Objects.requireNonNull(json);
        try {
            return getObjectMapper().readValue(json, objectClass);
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
            throw new RuntimeException("fail to parsed a JSON string to a Java object", e);
        }
    }

    /**
     * Parsing a JSON file to a Java object
     * @param jsonFile a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> T toObject(File jsonFile, Class<T> objectClass) {
        if (jsonFile == null || !jsonFile.exists()) {
            return null;
        } else if (objectClass == null) {
            throw new IllegalArgumentException("the objectClass is null");
        }
        try {
            return getObjectMapper().readValue(jsonFile, objectClass);
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        } catch (IOException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return null;
    }

    /**
     * Parsing a JSON array string to a Java object array
     * @param jsonArray a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> T[] toArray(String jsonArray, Class<T> objectClass) {
        if (StrUtil.isEmpty(jsonArray)) {
            return null;
        } else if (objectClass == null) {
            throw new IllegalArgumentException("the objectClass is null");
        }
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<T[]>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return null;
    }

    /**
     * Parsing a JSON array string to a Java object list
     * @param jsonArray a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> List<T> toList(String jsonArray, Class<T> objectClass) {
        if (StrUtil.isEmpty(jsonArray)) {
            return null;
        } else if (objectClass == null) {
            throw new IllegalArgumentException("the objectClass is null");
        }
        try {
            return getObjectMapper().readValue(jsonArray, new TypeReference<List<T>>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return null;
    }

    /**
     * Parsing a JSON string to a Map
     * @param json a JSON string
     * @return
     */
    public Map<String, Object> toMap(String json) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(json, new TypeReference<Map<String, Object>>(){});
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return null;
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
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return "";
    }

    /**
     * Parsing a Java object to a Java string
     * @param object
     * @return
     */
    public String toJavaString(Object object) {
        try {
            String jsonString = getObjectMapper().writeValueAsString(object);
            return jsonString.substring(1, jsonString.length() - 1);
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return "";
    }

    /**
     * Parsing a JSON string to a Optional that contain the Java object
     * @param json a JSON string
     * @param objectClass target Java object class
     * @return
     */
    public <T> Optional<T> toOptionalObject(String json, Class<T> objectClass) {
        return Optional.ofNullable(toObject(json, objectClass));
    }

    /**
     * Parsing a JSON file to a Optional that contain the Java object
     * @param jsonFile a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> Optional<T> toOptionalObject(File jsonFile, Class<T> objectClass) {
        return Optional.ofNullable(toObject(jsonFile, objectClass));
    }

    /**
     * Parsing a JSON array string to a Optional that contain the Java object array
     * @param jsonArray a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> Optional<T[]> toOptionalArray(String jsonArray, Class<T> objectClass) {
        return Optional.ofNullable(toArray(jsonArray, objectClass));
    }

    /**
     * Parsing a JSON array string to a Optional that contain the Java object list
     * @param jsonArray a File read from xxx.json
     * @param objectClass target Java object class
     * @return
     */
    public <T> Optional<List<T>> toOptionalList(String jsonArray, Class<T> objectClass) {
        return Optional.ofNullable(toList(jsonArray, objectClass));
    }

    /**
     * Parsing a JSON string to a Optional that contain the Map
     * @param json a JSON string
     * @return
     */
    public Optional<Map<String, Object>> toOptionalMap(String json) {
        return Optional.ofNullable(toMap(json));
    }

    /**
     * Parsing a Java object to a JSON string
     * @param object
     * @return {@link Optional}
     */
    public Optional<String> toOptionalJSON(Object object) {
        try {
            return Optional.ofNullable(getObjectMapper().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("fail to parsed a JSON string to a Java object", e);
        }
        return Optional.empty();
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapperBean = SpringBeanUtil.getBean("objectMapper", ObjectMapper.class);
        return objectMapperBean == null ? DEFAULT_OBJECT_MAPPER : objectMapperBean;
    }

}
