package com.destiny.common.entity;

import com.destiny.common.constant.SymbolConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BeanRespForTest {
    private String name;
    private Integer age;
    private Long snLong;
    private BigDecimal price;
    private LocalDateTime dateTime;
    private LocalDate date;
    private LocalTime time;
    private List<Object> objList;
    private Map<Object, Object> objMap;

    public BeanRespForTest init() {
        name = "李四";
        age = new Integer(66);
        snLong = new Long(78);
        price = new BigDecimal(998.1);
        dateTime = LocalDateTime.parse("2020-08-13T18:11:27.623");
        date = LocalDate.parse("2020-08-13");
        time = LocalTime.parse("18:11:27.623");
        return this;
    }

    public BeanRespForTest setTestList() {
        this.objList = testObjList();
        return this;
    }

    public BeanRespForTest setTestMap() {
        this.objMap = testObjMap();
        return this;
    }

    public static List<Object> testObjList() {
        List<Object> list = new ArrayList<>();
        list.add("list string");
        list.add(LocalDateTime.parse("2020-08-13T18:11:27.623"));
        return list;
    }

    public static Map<Object, Object> testObjMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put("string key", "string value");
        map.put(10, "int key");
        map.put("datetime value", LocalDateTime.parse("2020-08-13T18:11:27.623"));
        return map;
    }

    public static String function(Integer num, String name) {
        return num + SymbolConstant.HYPHEN_CONTAIN_SPACE + name;
    }
}
