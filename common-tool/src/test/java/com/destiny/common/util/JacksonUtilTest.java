package com.destiny.common.util;

import com.destiny.common.entity.BeanForTest;
import com.destiny.common.jackson.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public void isJsonTest() {
        String notJsonString = "{\"str";
        String jsonObjString = "{\"string key\":\"string value\",\"datetime value\":\"2020-07-29 18:11:27.623\"}";
        String jsonArrayString = "[\"list string\",\"2020-07-29 18:11:27.623\"]";

        String notCheckThisObjJson = "{\"string k-07-29 18:11:27.623\"}";
        String notCheckThisArrayJson = "[\"list string,]";

        // isJsonObject
        Assertions.assertFalse(JacksonUtil.of().isJsonObject(null), "JacksonUtil isJsonObject error");
        Assertions.assertFalse(JacksonUtil.of().isJsonObject(""), "JacksonUtil isJsonObject error");
        Assertions.assertFalse(JacksonUtil.of().isJsonObject(notJsonString), "JacksonUtil isJsonObject error");
        Assertions.assertTrue(JacksonUtil.of().isJsonObject(jsonObjString), "JacksonUtil isJsonObject error");
        Assertions.assertFalse(JacksonUtil.of().isJsonObject(jsonArrayString), "JacksonUtil isJsonObject error");
        Assertions.assertTrue(JacksonUtil.of().isJsonObject(notCheckThisObjJson), "JacksonUtil isJsonObject error");
        Assertions.assertFalse(JacksonUtil.of().isJsonObject(notCheckThisArrayJson), "JacksonUtil isJsonObject error");

        // isJsonArray
        Assertions.assertFalse(JacksonUtil.of().isJsonArray(null), "JacksonUtil isJsonArray error");
        Assertions.assertFalse(JacksonUtil.of().isJsonArray(""), "JacksonUtil isJsonArray error");
        Assertions.assertFalse(JacksonUtil.of().isJsonArray(notJsonString), "JacksonUtil isJsonArray error");
        Assertions.assertFalse(JacksonUtil.of().isJsonArray(jsonObjString), "JacksonUtil isJsonArray error");
        Assertions.assertTrue(JacksonUtil.of().isJsonArray(jsonArrayString), "JacksonUtil isJsonArray error");
        Assertions.assertFalse(JacksonUtil.of().isJsonArray(notCheckThisObjJson), "JacksonUtil isJsonArray error");
        Assertions.assertTrue(JacksonUtil.of().isJsonArray(notCheckThisArrayJson), "JacksonUtil isJsonArray error");

        // isJson
        Assertions.assertFalse(JacksonUtil.of().isJson(null), "JacksonUtil isJson error");
        Assertions.assertFalse(JacksonUtil.of().isJson(""), "JacksonUtil isJson error");
        Assertions.assertFalse(JacksonUtil.of().isJson(notJsonString), "JacksonUtil isJson error");
        Assertions.assertTrue(JacksonUtil.of().isJson(jsonObjString), "JacksonUtil isJson error");
        Assertions.assertTrue(JacksonUtil.of().isJson(jsonArrayString), "JacksonUtil isJson error");
        Assertions.assertTrue(JacksonUtil.of().isJson(notCheckThisObjJson), "JacksonUtil isJson error");
        Assertions.assertTrue(JacksonUtil.of().isJson(notCheckThisArrayJson), "JacksonUtil isJson error");
    }

    @Test
    @Order(5)
    public void jsonToObjectTest() {
        // objectClass is null
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject(BEAN_JSON, null), "JacksonUtil toObject error");
        // json is null
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject((String)null, BeanForTest.class), "JacksonUtil toObject error");
        // the content of json is not the BeanForTest's json string
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject("", BeanForTest.class), "JacksonUtil toObject error");
        // the content of json is not the BeanForTest's json string
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject("{\"string key\":\"}", BeanForTest.class), "JacksonUtil toObject error");
        // the content of json is the BeanForTest's json string
        Assertions.assertEquals("BeanForTest(name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.57900000000000062527760746888816356658935546875, dateTime=2020-07-29T18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null)",
                JacksonUtil.of().toObject(BEAN_JSON, BeanForTest.class).toString(), "JacksonUtil toObject error");
    }

    @Test
    @Order(5)
    public void fileToObjectTest() {
        // objectClass is null
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject(new File(""), null), "JacksonUtil toObject error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject((File) null, BeanForTest.class), "JacksonUtil toObject error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject(new File("notExistsFile"), BeanForTest.class), "JacksonUtil toObject error");
        // file is legal, but the content is not a json string
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toObject(new File("src/test/resources/files/emptyContent.text"), BeanForTest.class), "JacksonUtil toObject error");
        // file is legal and the content is a json string
        Assertions.assertEquals("BeanForTest(name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.57900000000000062527760746888816356658935546875, dateTime=2020-07-29T18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null)",
                JacksonUtil.of().toObject(new File("src/test/resources/files/jsonObjTest.json"), BeanForTest.class).toString(), "JacksonUtil toObject error");
        // file is legal and the content is a json string
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                JacksonUtil.of().toObject(new File("src/test/resources/files/jsonObjTest.json"), Map.class).toString(), "JacksonUtil toObject error");
        // file is legal and the content is a json string
        Assertions.assertEquals("[list string, 2020-07-29 18:11:27.623]",
                JacksonUtil.of().toObject(new File("src/test/resources/files/jsonArrayTest.json"), List.class).toString(), "JacksonUtil toObject error");
    }

    @Test
    @Order(5)
    public void toArrayTest() {
        String listIllegalTypeJson = "[hello, world]";
        String listStringJson = "[\"list string\",\"2020-07-29 18:11:27.623\"]";
        String listIntegerJson = "[1, 3, 5]";
        // bean list length = 2
        String listBeanJson = "[{\"name\":\"张三\",\"age\":10,\"sn\":13,\"amount\":2.307,\"num\":10,\"snLong\":30," +
                "\"price\":13.57900000000000062527760746888816356658935546875,\"dateTime\":\"2020-07-29 18:11:27.623\"," +
                "\"date\":\"2020-07-29\",\"time\":\"18:11:27.623\",\"objList\":null,\"objMap\":null}," +
                "{\"name\":null,\"age\":0,\"sn\":0,\"amount\":0.0,\"num\":null,\"snLong\":null,\"price\":null,\"dateTime\":null," +
                "\"date\":null,\"time\":null,\"objList\":null,\"objMap\":null}]";

        // toArray
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray(null), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray(""), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray("{}"), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray("[{\"name\":\"}"), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray("{,\"objMap\":null}]"), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray("[,objMap\":null}]"), "JacksonUtil toArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toArray(listIllegalTypeJson), "JacksonUtil toArray error");
        Object[] emptyObjArray = JacksonUtil.of().toArray("[]");
        Assertions.assertTrue(emptyObjArray instanceof Object, "JacksonUtil toArray error");
        Assertions.assertTrue(emptyObjArray != null, "JacksonUtil toArray error");
        Assertions.assertTrue(emptyObjArray.length == 0, "JacksonUtil toArray error");
        // bean list
        Object[] objArray = JacksonUtil.of().toArray(listBeanJson);
        Assertions.assertTrue(objArray instanceof Object && objArray != null, "JacksonUtil toArray error");
        Assertions.assertTrue(objArray.length == 2, "JacksonUtil toArray error");
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                objArray[0].toString(), "JacksonUtil toArray error");
        Assertions.assertEquals("{name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null}",
                objArray[1].toString(), "JacksonUtil toArray error");
        // integer list
        Object[] intObjects = JacksonUtil.of().toArray(listIntegerJson);
        Assertions.assertTrue(intObjects != null && intObjects.length == 3, "JacksonUtil toArray error");
        Assertions.assertTrue(intObjects[0] instanceof Integer && ((Integer) intObjects[0]).intValue() == 1, "JacksonUtil toArray error");
        Assertions.assertTrue(intObjects[1] instanceof Integer && ((Integer) intObjects[1]).intValue() == 3, "JacksonUtil toArray error");
        Assertions.assertTrue(intObjects[2] instanceof Integer && ((Integer) intObjects[2]).intValue() == 5, "JacksonUtil toArray error");
        // string list
        Object[] stringObjects = JacksonUtil.of().toArray(listStringJson);
        Assertions.assertTrue(stringObjects != null && stringObjects.length == 2, "JacksonUtil toArray error");
        Assertions.assertTrue(stringObjects[0] instanceof String && stringObjects[1] instanceof String, "JacksonUtil toArray error");
        Assertions.assertEquals("list string", stringObjects[0], "JacksonUtil toArray error");
        Assertions.assertEquals("2020-07-29 18:11:27.623", stringObjects[1], "JacksonUtil toArray error");

        // toStringArray
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray(null), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray(""), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray("{}"), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray("[{\"name\":\"}"), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray("{,\"objMap\":null}]"), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray("[,objMap\":null}]"), "JacksonUtil toStringArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray(listIllegalTypeJson), "JacksonUtil toStringArray error");
        String[] emptyStringArray = JacksonUtil.of().toStringArray("[]");
        Assertions.assertTrue(emptyStringArray != null && emptyStringArray.length == 0, "JacksonUtil toStringArray error");
        // bean list
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toStringArray(listBeanJson), "JacksonUtil toStringArray error");
        // integer list
        String[] intStrings = JacksonUtil.of().toStringArray(listIntegerJson);
        Assertions.assertTrue(intStrings != null && intStrings.length == 3, "JacksonUtil toIntegerArray error");
        Assertions.assertTrue(intStrings[0] instanceof String && intStrings[1] instanceof String && intStrings[2] instanceof String, "JacksonUtil toIntegerArray error");
        Assertions.assertTrue("1".equals(intStrings[0]) && "3".equals(intStrings[1]) && "5".equals(intStrings[2]), "JacksonUtil toIntegerArray error");
        // string list
        String[] strings = JacksonUtil.of().toStringArray(listStringJson);
        Assertions.assertTrue(strings != null && strings.length == 2, "JacksonUtil toStringArray error");
        Assertions.assertEquals("list string", strings[0], "JacksonUtil toStringArray error");
        Assertions.assertEquals("2020-07-29 18:11:27.623", strings[1], "JacksonUtil toStringArray error");

        // toIntegerArray
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray(null), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray(""), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray("{}"), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray("[{\"name\":\"}"), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray("{,\"objMap\":null}]"), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray("[,objMap\":null}]"), "JacksonUtil toIntegerArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray(listIllegalTypeJson), "JacksonUtil toIntegerArray error");
        Integer[] emptyIntegerArray = JacksonUtil.of().toIntegerArray("[]");
        Assertions.assertTrue(emptyIntegerArray != null && emptyIntegerArray.length == 0, "JacksonUtil toIntegerArray error");
        // bean list
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray(listBeanJson), "JacksonUtil toIntegerArray error");
        // integer list
        Integer[] integers = JacksonUtil.of().toIntegerArray(listIntegerJson);
        Assertions.assertTrue(integers != null && integers.length == 3, "JacksonUtil toIntegerArray error");
        Assertions.assertTrue(integers[0].intValue() == 1 && integers[1].intValue() == 3 && integers[2].intValue() == 5, "JacksonUtil toIntegerArray error");
        // string list
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toIntegerArray(listStringJson), "JacksonUtil toIntegerArray error");

        // toMapArray
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray(null), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray(""), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray("{}"), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray("[{\"name\":\"}"), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray("{,\"objMap\":null}]"), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray("[,objMap\":null}]"), "JacksonUtil toMapArray error");
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray(listIllegalTypeJson), "JacksonUtil toMapArray error");
        Map[] emptyMapArray = JacksonUtil.of().toMapArray("[]");
        Assertions.assertTrue(emptyMapArray != null && emptyMapArray.length == 0, "JacksonUtil toMapArray error");
        // bean list
        Map[] beanMaps = JacksonUtil.of().toMapArray(listBeanJson);
        Assertions.assertTrue(beanMaps != null && beanMaps.length == 2, "JacksonUtil toMapArray error");
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                beanMaps[0].toString(), "JacksonUtil toMapArray error");
        Assertions.assertEquals("{name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null}",
                beanMaps[1].toString(), "JacksonUtil toMapArray error");
        // integer list
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray(listIntegerJson), "JacksonUtil toMapArray error");
        // string list
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMapArray(listStringJson), "JacksonUtil toMapArray error");
    }

    @Test
    @Order(5)
    public void toListReturnObjectListTest() {
        String errorMsg = "JacksonUtil toList error";
        String listIllegalTypeJson = "[hello, world]";
        String listStringJson = "[\"list string\",\"2020-07-29 18:11:27.623\"]";
        String listIntegerJson = "[1, 3, 5]";
        // bean list length = 2
        String listBeanJson = "[{\"name\":\"张三\",\"age\":10,\"sn\":13,\"amount\":2.307,\"num\":10,\"snLong\":30," +
                "\"price\":13.57900000000000062527760746888816356658935546875,\"dateTime\":\"2020-07-29 18:11:27.623\"," +
                "\"date\":\"2020-07-29\",\"time\":\"18:11:27.623\",\"objList\":null,\"objMap\":null}," +
                "{\"name\":null,\"age\":0,\"sn\":0,\"amount\":0.0,\"num\":null,\"snLong\":null,\"price\":null,\"dateTime\":null," +
                "\"date\":null,\"time\":null,\"objList\":null,\"objMap\":null}]";

        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(null), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(""), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("{}"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("[{\"name\":\"}"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("{,\"objMap\":null}]"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("[,objMap\":null}]"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listIllegalTypeJson), errorMsg);
        List<Object> emptyObjList = JacksonUtil.of().toList("[]");
        Assertions.assertTrue(emptyObjList != null && emptyObjList.size() == 0, errorMsg);
        // bean list
        List<Object> objList = JacksonUtil.of().toList(listBeanJson);
        Assertions.assertTrue(objList != null && objList.size() == 2, errorMsg);
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                objList.get(0).toString(), errorMsg);
        Assertions.assertEquals("{name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null}",
                objList.get(1).toString(), errorMsg);
        // integer list
        List<Object> intObjects = JacksonUtil.of().toList(listIntegerJson);
        Assertions.assertTrue(intObjects != null && intObjects.size() == 3, errorMsg);
        Assertions.assertTrue(intObjects.get(0) instanceof Integer && ((Integer) intObjects.get(0)).intValue() == 1, errorMsg);
        Assertions.assertTrue(intObjects.get(1) instanceof Integer && ((Integer) intObjects.get(1)).intValue() == 3, errorMsg);
        Assertions.assertTrue(intObjects.get(2) instanceof Integer && ((Integer) intObjects.get(2)).intValue() == 5, errorMsg);
        // string list
        List<Object> stringObjects = JacksonUtil.of().toList(listStringJson);
        Assertions.assertTrue(stringObjects != null && stringObjects.size() == 2, errorMsg);
        Assertions.assertTrue(stringObjects.get(0) instanceof String && stringObjects.get(1) instanceof String, errorMsg);
        Assertions.assertEquals("list string", stringObjects.get(0), errorMsg);
        Assertions.assertEquals("2020-07-29 18:11:27.623", stringObjects.get(1), errorMsg);
    }

    @Test
    @Order(5)
    public void toListReturnTListTest() {
        String errorMsg = "JacksonUtil toList error";
        String listIllegalTypeJson = "[hello, world]";
        String listStringJson = "[\"list string\",\"2020-07-29 18:11:27.623\"]";
        String listIntegerJson = "[1, 3, 5]";
        // bean list length = 2
        String listBeanJson = "[{\"name\":\"张三\",\"age\":10,\"sn\":13,\"amount\":2.307,\"num\":10,\"snLong\":30," +
                "\"price\":13.57900000000000062527760746888816356658935546875,\"dateTime\":\"2020-07-29 18:11:27.623\"," +
                "\"date\":\"2020-07-29\",\"time\":\"18:11:27.623\",\"objList\":null,\"objMap\":null}," +
                "{\"name\":null,\"age\":0,\"sn\":0,\"amount\":0.0,\"num\":null,\"snLong\":null,\"price\":null,\"dateTime\":null," +
                "\"date\":null,\"time\":null,\"objList\":null,\"objMap\":null}]";

        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(null, null), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(null, String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("", String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("{}", String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("[{\"name\":\"}", String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("{,\"objMap\":null}]", String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList("[,objMap\":null}]", String.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listIllegalTypeJson, String.class), errorMsg);
        List<Object> emptyTObjList = JacksonUtil.of().toList("[]", Object.class);
        Assertions.assertTrue(emptyTObjList != null && emptyTObjList.size() == 0, errorMsg);

        // --- json content is bean list ---
        // ClassType is not fit to the json data type
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listBeanJson, BeanForTest.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listBeanJson, String.class), errorMsg);
        // beanListJsonToObjectList
        List<Object> beanListJsonToObjectList = JacksonUtil.of().toList(listBeanJson, Object.class);
        Assertions.assertTrue(beanListJsonToObjectList != null && beanListJsonToObjectList.size() == 2, errorMsg);
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                beanListJsonToObjectList.get(0).toString(), errorMsg);
        Assertions.assertEquals("{name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null}",
                beanListJsonToObjectList.get(1).toString(), errorMsg);
        // beanListJsonToMapList
        List<Map> beanListJsonToMapList = JacksonUtil.of().toList(listBeanJson, Map.class);
        Assertions.assertTrue(beanListJsonToMapList != null && beanListJsonToMapList.size() == 2, errorMsg);
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                beanListJsonToMapList.get(0).toString(), errorMsg);
        Assertions.assertEquals("{name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null}",
                beanListJsonToMapList.get(1).toString(), errorMsg);

        // --- json content is integer list ---
        // ClassType is not fit to the json data type
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listIntegerJson, BeanForTest.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listIntegerJson, Map.class), errorMsg);
        // intListJsonToObjectList
        List<Object> intListJsonToObjectList = JacksonUtil.of().toList(listIntegerJson, Object.class);
        Assertions.assertTrue(intListJsonToObjectList != null && intListJsonToObjectList.size() == 3, errorMsg);
        Assertions.assertTrue(intListJsonToObjectList.get(0) instanceof Integer && ((Integer) intListJsonToObjectList.get(0)).intValue() == 1, errorMsg);
        Assertions.assertTrue(intListJsonToObjectList.get(1) instanceof Integer && ((Integer) intListJsonToObjectList.get(1)).intValue() == 3, errorMsg);
        Assertions.assertTrue(intListJsonToObjectList.get(2) instanceof Integer && ((Integer) intListJsonToObjectList.get(2)).intValue() == 5, errorMsg);
        // intListJsonToIntList
        List<Integer> intListJsonToIntList = JacksonUtil.of().toList(listIntegerJson, Integer.class);
        Assertions.assertTrue(intListJsonToIntList != null && intListJsonToIntList.size() == 3, errorMsg);
        Assertions.assertTrue(intListJsonToIntList.get(0).intValue() == 1 && intListJsonToIntList.get(1).intValue() == 3 && intListJsonToIntList.get(2).intValue() == 5, errorMsg);

        // --- json content is String list ---
        // ClassType is not fit to the json data type
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listStringJson, BeanForTest.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listStringJson, Map.class), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toList(listStringJson, Integer.class), errorMsg);
        // strListJsonToObjectList
        List<Object> strListJsonToObjectList = JacksonUtil.of().toList(listStringJson, Object.class);
        Assertions.assertTrue(strListJsonToObjectList != null && strListJsonToObjectList.size() == 2, errorMsg);
        Assertions.assertTrue(strListJsonToObjectList.get(0) instanceof String && strListJsonToObjectList.get(1) instanceof String, errorMsg);
        Assertions.assertEquals("list string", strListJsonToObjectList.get(0), errorMsg);
        Assertions.assertEquals("2020-07-29 18:11:27.623", strListJsonToObjectList.get(1), errorMsg);
        // strListJsonToStringList
        List<String> strListJsonToStringList = JacksonUtil.of().toList(listStringJson, String.class);
        Assertions.assertTrue(strListJsonToStringList != null && strListJsonToStringList.size() == 2, errorMsg);
        Assertions.assertEquals("list string", strListJsonToStringList.get(0), errorMsg);
        Assertions.assertEquals("2020-07-29 18:11:27.623", strListJsonToStringList.get(1), errorMsg);
    }

    @Test
    @Order(5)
    public void toMapTest() {
        String errorMsg = "JacksonUtil toMap error";
        String illegalTypeJson = "{hello, world}";
        String listStringJson = "{\"list string\",\"2020-07-29 18:11:27.623\"}";
        String listIntegerJson = "{1, 3, 5}";
        // bean list length = 2
        String mapBeanJson = "{\"name\":\"张三\",\"age\":10,\"sn\":13,\"amount\":2.307,\"num\":10,\"snLong\":30," +
                "\"price\":13.57900000000000062527760746888816356658935546875,\"dateTime\":\"2020-07-29 18:11:27.623\"," +
                "\"date\":\"2020-07-29\",\"time\":\"18:11:27.623\",\"objList\":null,\"objMap\":null}";

        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(null), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(null), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(""), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap("[]"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap("[{\"name\":\"}"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap("{,\"objMap\":null}]"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap("[,objMap\":null}]"), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(illegalTypeJson), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(listStringJson), errorMsg);
        Assertions.assertThrows(JacksonException.class, () -> JacksonUtil.of().toMap(listIntegerJson), errorMsg);
        Map<Object, Object> emptyMap = JacksonUtil.of().toMap("{}");
        Assertions.assertTrue(emptyMap != null && emptyMap.isEmpty(), errorMsg);

        // --- json content is map ---
        Map<Object, Object> map = JacksonUtil.of().toMap(mapBeanJson);
        Assertions.assertTrue(map != null && !map.isEmpty(), errorMsg);
        // bean list - jackson parse it to a LinkedHashMap
        Assertions.assertEquals("{name=张三, age=10, sn=13, amount=2.307, num=10, snLong=30, price=13.579, dateTime=2020-07-29 18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null}",
                map.toString(), errorMsg);
        Assertions.assertTrue(map.containsKey("name"), errorMsg);
        Assertions.assertEquals("张三", map.get("name").toString(), errorMsg);
    }

}
