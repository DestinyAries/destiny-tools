package com.destiny.common.util;

import com.destiny.common.entity.BeanForTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

import java.time.LocalDateTime;

/**
 * Jackson toolkit tester
 * @Author Destiny
 * @Version 1.0.0
 */
public class JacksonUtilTest {

    private final static String BEAN_JSON = "{\"name\":\"张三\",\"age\":10,\"sn\":13,\"amount\":2.307,\"num\":10," +
            "\"snLong\":30,\"price\":13.57900000000000062527760746888816356658935546875,\"dateTime\":\"2020-07-29 18:11:27.623\"," +
            "\"date\":\"2020-07-29\",\"time\":\"18:11:27.623\",\"objList\":null,\"objMap\":null}";

    @Test
    @Order(1)
    public void localDateTimeConfigTest() {
        ObjectMapper defaultObjectMapper = JacksonUtil.defaultObjectMapper();
        LocalDateTime dateTime = LocalDateTime.parse("2020-08-11T15:11:27.623");
        try {
            Assertions.assertEquals("\"2020-08-11 15:11:27.623\"", defaultObjectMapper.writeValueAsString(dateTime),
                    "The LocalDateTime register JavaTimeModule failure");
        } catch (JsonProcessingException e) {
            AssertionErrors.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void toJSONTest() {
        Assertions.assertEquals("null", JacksonUtil.of().toJSON(null), "JacksonUtil toJSON error");
        // String
        Assertions.assertEquals("\"\"", JacksonUtil.of().toJSON(""), "JacksonUtil toJSON error");
        // Bean
        Assertions.assertEquals(BEAN_JSON, JacksonUtil.of().toJSON(new BeanForTest().init()), "JacksonUtil toJSON error");
        // ArrayList
        Assertions.assertEquals("[\"list string\",\"2020-07-29 18:11:27.623\"]", JacksonUtil.of().toJSON(BeanForTest.testObjList()), "JacksonUtil toJSON error");
        // HashMap
        Assertions.assertEquals("{\"string key\":\"string value\",\"10\":\"int key\",\"datetime value\":\"2020-07-29 18:11:27.623\"}",
                JacksonUtil.of().toJSON(BeanForTest.testObjMap()), "JacksonUtil toJSON error");
    }

    @Test
    @Order(3)
    public void toObjectTest() {
        // objectClass is null
        Assertions.assertThrows(NullPointerException.class, () -> JacksonUtil.of().toObject(BEAN_JSON, null), "JacksonUtil toObject error");
        // json is null
        Assertions.assertThrows(NullPointerException.class, () -> JacksonUtil.of().toObject((String)null, BeanForTest.class), "JacksonUtil toObject error");
        // the content of json is not the BeanForTest's json string
        Assertions.assertThrows(RuntimeException.class, () -> JacksonUtil.of().toObject("", BeanForTest.class), "JacksonUtil toObject error");
        // the content of json is the BeanForTest's json string
        Assertions.assertEquals("BeanForTest(name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.57900000000000062527760746888816356658935546875, dateTime=2020-07-29T18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null)",
                JacksonUtil.of().toObject(BEAN_JSON, BeanForTest.class).toString(), "JacksonUtil toObject error");
    }
}
