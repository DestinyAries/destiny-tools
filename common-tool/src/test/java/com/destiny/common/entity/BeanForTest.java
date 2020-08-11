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
public class BeanForTest {
    private String name;
    private int age;
    private long sn;
    private double amount;
    private Integer num;
    private Long snLong;
    private BigDecimal price;
    private LocalDateTime dateTime;
    private LocalDate date;
    private LocalTime time;
    private List<Object> objList;
    private Map<Object, Object> objMap;

    public BeanForTest init() {
        name = "张三";
        age = 10;
        sn = 13l;
        amount = 2.307;
        num = new Integer(10);
        snLong = new Long(30);
        price = new BigDecimal(13.579);
        dateTime = LocalDateTime.parse("2020-07-29T18:11:27.623");
        date = LocalDate.parse("2020-07-29");
        time = LocalTime.parse("18:11:27.623");
        return this;
    }

    public BeanForTest setTestList() {
        this.objList = testObjList();
        return this;
    }

    public BeanForTest setTestMap() {
        this.objMap = testObjMap();
        return this;
    }

    public static List<Object> testObjList() {
        List<Object> list = new ArrayList<>();
        list.add("list string");
        list.add(LocalDateTime.parse("2020-07-29T18:11:27.623"));
        return list;
    }

    public static Map<Object, Object> testObjMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put("string key", "string value");
        map.put(10, "int key");
        map.put("datetime value", LocalDateTime.parse("2020-07-29T18:11:27.623"));
        return map;
    }

    public static String function(Integer num, String name) {
        return num + SymbolConstant.HYPHEN_CONTAIN_SPACE + name;
    }
}
