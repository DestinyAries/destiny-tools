package com.destiny.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linwanrong
 * @Date 2019/9/24 16:39
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogParamEntityTest {
    @Test
    @Order(1)
    public void setKeysTest() {
        Assertions.assertEquals(LogParamEntity.setKeys(), null);
        Assertions.assertEquals(LogParamEntity.setKeys("1"), "#format#1{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", "2"), "#format#1{}2{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", "2", "3"), "#format#1{}2{}3{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", "2", "3", "4"), "#format#1{}2{}3{}4{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", null, "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", "", "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assertions.assertEquals(LogParamEntity.setKeys("1", " ", "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assertions.assertEquals(LogParamEntity.setKeys("a1", "a1", "a2"), "#format#a1{}a1{}a2{}");
    }

    @Test
    @Order(2)
    public void getKeysTest() {
        List<String> keyList = new ArrayList<>();
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys()), null);
        keyList.add("1");
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1")), keyList);
        keyList.add("2");
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "2")), keyList);
        keyList.add("3");
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "2", "3")), keyList);
        keyList.clear();
        keyList.add("1");
        keyList.add("#null#");
        keyList.add("3");
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", null, "3")), keyList);
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "", "3")), keyList);
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", " ", "3")), keyList);
        keyList.clear();
        keyList.add("a1");
        keyList.add("a1");
        keyList.add("a2");
        Assertions.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("a1", "a1", "a2")), keyList);
        Assertions.assertEquals(LogParamEntity.getKeys("1"), null);
        Assertions.assertEquals(LogParamEntity.getKeys("1#format#{}"), null);
        Assertions.assertEquals(LogParamEntity.getKeys("#format#{}{}"), new ArrayList<>());
        Assertions.assertEquals(LogParamEntity.getKeys("#format#{"), null);
        Assertions.assertEquals(LogParamEntity.getKeys("#format#}"), null);
        Assertions.assertEquals(LogParamEntity.getKeys("#format#}{"), null);
    }

    @Test
    @Order(3)
    public void phoneTest() {
        Assertions.assertEquals(LogParamEntity.phone("12"), "12");
        Assertions.assertEquals(LogParamEntity.phone("1"), "1");
        Assertions.assertEquals(LogParamEntity.phone("0"), "0");
        Assertions.assertEquals(LogParamEntity.phone("02969806622"), "02969806622");
        Assertions.assertEquals(LogParamEntity.phone("96668289238"), "96668289238");
        Assertions.assertEquals(LogParamEntity.phone("19012341158"), "190****1158");
        Assertions.assertEquals(LogParamEntity.phone(""), "");
        Assertions.assertEquals(LogParamEntity.phone(" "), "");
        Assertions.assertEquals(LogParamEntity.phone(null), "");
        Assertions.assertEquals(LogParamEntity.phone("189585120532"), "189585120532");
        Assertions.assertEquals(LogParamEntity.phone("1776411866"), "1776411866");
    }

    @Test
    @Order(4)
    public void idCardTest() {
        // 18
        Assertions.assertEquals(LogParamEntity.idCard("110101199003073490"), "110***********3490");
        // 18-X
        Assertions.assertEquals(LogParamEntity.idCard("11010119800307371X"), "110***********371X");
        // 18-x
        Assertions.assertEquals(LogParamEntity.idCard("11010119800307371x"), "110***********371x");
        // birthday yyyy
        Assertions.assertEquals(LogParamEntity.idCard("110101100003073490"), "110***********3490");
        // birthday mm
        Assertions.assertEquals(LogParamEntity.idCard("110101199019073490"), "110101199019073490");
        // birthday dd
        Assertions.assertEquals(LogParamEntity.idCard("110101199012403490"), "110101199012403490");
        // 18
        Assertions.assertEquals(LogParamEntity.idCard("215317628549432730"), "215317628549432730");
        // 19
        Assertions.assertEquals(LogParamEntity.idCard("1101011990030734901"), "1101011990030734901");
        // 17
        Assertions.assertEquals(LogParamEntity.idCard("11010119900307349"), "11010119900307349");
        Assertions.assertEquals(LogParamEntity.idCard("0"), "0");
        Assertions.assertEquals(LogParamEntity.idCard("1"), "1");
        // empty
        Assertions.assertEquals(LogParamEntity.idCard(""), "");
        // space
        Assertions.assertEquals(LogParamEntity.idCard(" "), "");
        // null
        Assertions.assertEquals(LogParamEntity.idCard(null), "");
        Assertions.assertEquals(LogParamEntity.idCard("null"), "null");
        Assertions.assertEquals(LogParamEntity.idCard("NULL"), "NULL");
    }

    @Test
    @Order(5)
    public void bankCardTest() {
        // 12
        Assertions.assertEquals(LogParamEntity.bankCard("126681828840"), "1266****8840");
        // 13
        Assertions.assertEquals(LogParamEntity.bankCard("9203194057956"), "9203*****7956");
        // 14
        Assertions.assertEquals(LogParamEntity.bankCard("85707295514568"), "8570******4568");
        // 15
        Assertions.assertEquals(LogParamEntity.bankCard("766817939116866"), "7668*******6866");
        // 16
        Assertions.assertEquals(LogParamEntity.bankCard("6613251764135068"), "6613********5068");
        // 17
        Assertions.assertEquals(LogParamEntity.bankCard("56026093659074868"), "5602*********4868");
        // 18
        Assertions.assertEquals(LogParamEntity.bankCard("420625815951766219"), "4206**********6219");
        // 19
        Assertions.assertEquals(LogParamEntity.bankCard("3777345555354180853"), "3777***********0853");
        // 20
        Assertions.assertEquals(LogParamEntity.bankCard("22932867964545603919"), "2293************3919");
        // head0
        Assertions.assertEquals(LogParamEntity.bankCard("092866567524"), "092866567524");
        Assertions.assertEquals(LogParamEntity.bankCard("0943189244762"), "0943189244762");
        Assertions.assertEquals(LogParamEntity.bankCard("08061986789055"), "08061986789055");
        Assertions.assertEquals(LogParamEntity.bankCard("031530405941200"), "031530405941200");
        Assertions.assertEquals(LogParamEntity.bankCard("0746556365903113"), "0746556365903113");
        Assertions.assertEquals(LogParamEntity.bankCard("08183143109826762"), "08183143109826762");
        Assertions.assertEquals(LogParamEntity.bankCard("054192641763751234"), "054192641763751234");
        Assertions.assertEquals(LogParamEntity.bankCard("0776227506608426834"), "0776227506608426834");
        Assertions.assertEquals(LogParamEntity.bankCard("01657686370409488916"), "01657686370409488916");
        // 21
        Assertions.assertEquals(LogParamEntity.bankCard("159767489735565030640"), "159767489735565030640");
        // 11
        Assertions.assertEquals(LogParamEntity.bankCard("14507212307"), "14507212307");
        Assertions.assertEquals(LogParamEntity.bankCard("0"), "0");
        Assertions.assertEquals(LogParamEntity.bankCard("1"), "1");
        // empty
        Assertions.assertEquals(LogParamEntity.bankCard(""), "");
        // space
        Assertions.assertEquals(LogParamEntity.bankCard(" "), "");
        // null
        Assertions.assertEquals(LogParamEntity.bankCard(null), "");
        Assertions.assertEquals(LogParamEntity.bankCard("null"), "null");
        Assertions.assertEquals(LogParamEntity.bankCard("NULL"), "NULL");
    }

    @Test
    @Order(6)
    public void cnNameTest() {
        Assertions.assertEquals(LogParamEntity.cnName("张三"), "张*");
        Assertions.assertEquals(LogParamEntity.cnName("王老五"), "王**");
        Assertions.assertEquals(LogParamEntity.cnName("张三李四"), "张***");
        Assertions.assertEquals(LogParamEntity.cnName("陈皮狗蛋儿"), "陈****");
        Assertions.assertEquals(LogParamEntity.cnName("陈皮狗蛋儿汪"), "陈*****");
        Assertions.assertEquals(LogParamEntity.cnName("新_疆-人·吖"), "新******");
        Assertions.assertEquals(LogParamEntity.cnName("下_划_线_吖"), "下******");
        Assertions.assertEquals(LogParamEntity.cnName("横-杠"), "横**");
        Assertions.assertEquals(LogParamEntity.cnName("点·点"), "点**");
        Assertions.assertEquals(LogParamEntity.cnName("两字·点"), "两***");
        Assertions.assertEquals(LogParamEntity.cnName("两字·两字"), "两****");
        Assertions.assertEquals(LogParamEntity.cnName("三个字·两字"), "三*****");
        Assertions.assertEquals(LogParamEntity.cnName("张1"), "张1");
        Assertions.assertEquals(LogParamEntity.cnName("张@"), "张@");
        Assertions.assertEquals(LogParamEntity.cnName("张 "), "张 ");
        Assertions.assertEquals(LogParamEntity.cnName("张13"), "张13");
        Assertions.assertEquals(LogParamEntity.cnName("张@3"), "张@3");
        Assertions.assertEquals(LogParamEntity.cnName("张 特"), "张 特");
        Assertions.assertEquals(LogParamEntity.cnName("0"), "0");
        // empty
        Assertions.assertEquals(LogParamEntity.cnName(""), "");
        // space
        Assertions.assertEquals(LogParamEntity.cnName(" "), "");
        // null
        Assertions.assertEquals(LogParamEntity.cnName(null), "");
        Assertions.assertEquals(LogParamEntity.cnName("null"), "null");
        Assertions.assertEquals(LogParamEntity.cnName("NULL"), "NULL");
    }

    @Test
    @Order(7)
    public void vCodeNum6Test() {
        // 6
        Assertions.assertEquals(LogParamEntity.vCodeNum6("291511"), "2****1");
        // 5
        Assertions.assertEquals(LogParamEntity.vCodeNum6("27247"), "27247");
        // 7
        Assertions.assertEquals(LogParamEntity.vCodeNum6("3593151"), "3593151");
        // 1
        Assertions.assertEquals(LogParamEntity.vCodeNum6("8"), "8");
        Assertions.assertEquals(LogParamEntity.vCodeNum6("中文"), "中文");
        // empty
        Assertions.assertEquals(LogParamEntity.vCodeNum6(""), "");
        // space
        Assertions.assertEquals(LogParamEntity.vCodeNum6(" "), "");
        // null
        Assertions.assertEquals(LogParamEntity.vCodeNum6(null), "");
        Assertions.assertEquals(LogParamEntity.vCodeNum6("null"), "null");
        Assertions.assertEquals(LogParamEntity.vCodeNum6("NULL"), "NULL");
    }

    @Test
    @Order(8)
    public void appendTest() {
        //============ no key ==========
        Assertions.assertEquals(new LogParamEntity(null).toJSONString(), "");
        Assertions.assertEquals(new LogParamEntity("").toJSONString(), "");
        Assertions.assertEquals(new LogParamEntity(" ").toJSONString(), " ");
        Assertions.assertEquals(new LogParamEntity("I'm a string").toJSONString(), "I'm a string");
        Assertions.assertEquals(new LogParamEntity(new User()).toJSONString(), "{\"age\":0}");
        Assertions.assertEquals(new LogParamEntity().append(null).toJSONString(), "");
        Assertions.assertEquals(new LogParamEntity().append(null).append("").toJSONString(), "{\"param1\":\"\",\"param2\":\"\"}");
        Assertions.assertEquals(new LogParamEntity().append(null).append("").append(" ").toJSONString(), "{\"param3\":\" \",\"param1\":\"\",\"param2\":\"\"}");
        Assertions.assertEquals(new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(new User()).toJSONString(),
                "{\"param7\":4.4,\"param8\":{\"age\":0},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param4\":1,\"param1\":\"\",\"param2\":\"\"}");

        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User cnName = new User("王老五-哈", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        LogParamEntity paramEntity = new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(jame).append(cnName);
        Assertions.assertEquals(paramEntity.toJSONString(), "{\"param7\":4.4,\"param8\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"Jame\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param5\":\"2\",\"param6\":3.3,\"param9\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"王老五-哈\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param3\":\" \",\"param4\":1,\"param1\":\"\",\"param2\":\"\"}");

        //============ has key ==========
        Assertions.assertEquals(new LogParamEntity("a1", null).toJSONString(), "{\"a1\":\"\"}");
        Assertions.assertEquals(new LogParamEntity("a1", "").toJSONString(), "{\"a1\":\"\"}");
        Assertions.assertEquals(new LogParamEntity("a1", " ").toJSONString(), "{\"a1\":\" \"}");
        Assertions.assertEquals(new LogParamEntity("a1", "I'm a string").toJSONString(), "{\"a1\":\"I'm a string\"}");
        Assertions.assertEquals(new LogParamEntity("a1", new User()).toJSONString(), "{\"a1\":{\"age\":0}}");
        Assertions.assertEquals(new LogParamEntity().append("a1", null).toJSONString(), "{\"a1\":\"\"}");
        Assertions.assertEquals(new LogParamEntity().append("a1", null).append("").toJSONString(), "{\"a1\":\"\",\"param2\":\"\"}");
        Assertions.assertEquals(new LogParamEntity().append("a1", null).append("").append(" ").toJSONString(),
                "{\"a1\":\"\",\"param3\":\" \",\"param2\":\"\"}");
        Assertions.assertEquals(new LogParamEntity().append("a1", null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(new User()).toJSONString(),
                "{\"a1\":\"\",\"param7\":4.4,\"param8\":{\"age\":0},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param4\":1,\"param2\":\"\"}");
        LogParamEntity paramEntity2 = new LogParamEntity().append(null).append("").append(" ").append("a1", 1).append("2").append(3.3).append(4.4f).append(jame).append("a2", cnName);
        Assertions.assertEquals(paramEntity2.toJSONString(), "{\"a1\":1,\"param7\":4.4,\"param8\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"Jame\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"a2\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"王老五-哈\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param1\":\"\",\"param2\":\"\"}");
    }

    @Test
    @Order(9)
    public void toJSONStringTest() {
        Assertions.assertEquals(new LogParamEntity().toJSONString(), "");
        Assertions.assertEquals(new LogParamEntity().toJSONString(), "");
        Assertions.assertEquals(new LogParamEntity(" ").toJSONString(), " ");
        Assertions.assertEquals(new LogParamEntity("I'm a string").toJSONString(), "I'm a string");
        Assertions.assertEquals(new LogParamEntity(new User()).toJSONString(), "{\"age\":0}");
        Assertions.assertEquals(new LogParamEntity("a1", " ").toJSONString(), "{\"a1\":\" \"}");
        Assertions.assertEquals(new LogParamEntity("a1", "I'm a string").toJSONString(), "{\"a1\":\"I'm a string\"}");
        Assertions.assertEquals(new LogParamEntity("a1", new User()).toJSONString(), "{\"a1\":{\"age\":0}}");
        Assertions.assertEquals(new LogParamEntity().append("a1", null).append("a2", 11).toJSONString(), "{\"a1\":\"\",\"a2\":11}");
        Assertions.assertEquals(new LogParamEntity().append("aaa").append("a2", 11).toJSONString(), "{\"a2\":11,\"param1\":\"aaa\"}");
        Assertions.assertEquals(new LogParamEntity().append(1).append("a2", null).toJSONString(), "{\"a2\":\"\",\"param1\":1}");
    }

    @Test
    @Order(10)
    public void getKeyTest() {
        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User cnName = new User("王老五-哈", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        LogParamEntity paramEntity = new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(jame).append(cnName);
        Assertions.assertEquals(paramEntity.getKey(null), "param10");

        Assertions.assertEquals(new LogParamEntity().getKey(null), "param1");
        Assertions.assertEquals(new LogParamEntity().getKey(""), "param1");
        Assertions.assertEquals(new LogParamEntity().getKey(" "), "param1");
        // 重复key情况
        Assertions.assertEquals(new LogParamEntity("a", 1).getKey("a"), "param2");
        Assertions.assertEquals(new LogParamEntity("a1", 1).getKey("a2"), "a2");
        Assertions.assertEquals(new LogParamEntity("a1", 1).getKey("@"), "@");
    }

}
