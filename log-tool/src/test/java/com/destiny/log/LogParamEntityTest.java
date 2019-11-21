package com.destiny.log;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linwanrong
 * @Date 2019/9/24 16:39
 */
public class LogParamEntityTest {
    @Test
    public void setKeysTest() {
        Assert.assertEquals(LogParamEntity.setKeys(), null);
        Assert.assertEquals(LogParamEntity.setKeys("1"), "#format#1{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", "2"), "#format#1{}2{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", "2", "3"), "#format#1{}2{}3{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", "2", "3", "4"), "#format#1{}2{}3{}4{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", null, "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", "", "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assert.assertEquals(LogParamEntity.setKeys("1", " ", "3", "4"), "#format#1{}#null#{}3{}4{}");
        Assert.assertEquals(LogParamEntity.setKeys("a1", "a1", "a2"), "#format#a1{}a1{}a2{}");
    }

    @Test
    public void getKeysTest() {
        List<String> keyList = new ArrayList<>();
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys()), null);
        keyList.add("1");
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1")), keyList);
        keyList.add("2");
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "2")), keyList);
        keyList.add("3");
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "2", "3")), keyList);
        keyList.clear();
        keyList.add("1");
        keyList.add("#null#");
        keyList.add("3");
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", null, "3")), keyList);
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", "", "3")), keyList);
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("1", " ", "3")), keyList);
        keyList.clear();
        keyList.add("a1");
        keyList.add("a1");
        keyList.add("a2");
        Assert.assertEquals(LogParamEntity.getKeys(LogParamEntity.setKeys("a1", "a1", "a2")), keyList);
        Assert.assertEquals(LogParamEntity.getKeys("1"), null);
        Assert.assertEquals(LogParamEntity.getKeys("1#format#{}"), null);
        Assert.assertEquals(LogParamEntity.getKeys("#format#{}{}"), new ArrayList<>());
        Assert.assertEquals(LogParamEntity.getKeys("#format#{"), null);
        Assert.assertEquals(LogParamEntity.getKeys("#format#}"), null);
        Assert.assertEquals(LogParamEntity.getKeys("#format#}{"), null);
    }

    @Test
    public void phoneTest() {
        Assert.assertEquals(LogParamEntity.phone("12"), "12");
        Assert.assertEquals(LogParamEntity.phone("1"), "1");
        Assert.assertEquals(LogParamEntity.phone("0"), "0");
        Assert.assertEquals(LogParamEntity.phone("02969806622"), "02969806622");
        Assert.assertEquals(LogParamEntity.phone("96668289238"), "96668289238");
        Assert.assertEquals(LogParamEntity.phone("19012341158"), "190****1158");
        Assert.assertEquals(LogParamEntity.phone(""), "");
        Assert.assertEquals(LogParamEntity.phone(" "), "");
        Assert.assertEquals(LogParamEntity.phone(null), "");
        Assert.assertEquals(LogParamEntity.phone("189585120532"), "189585120532");
        Assert.assertEquals(LogParamEntity.phone("1776411866"), "1776411866");
    }

    @Test
    public void idCardTest() {
        // 18
        Assert.assertEquals(LogParamEntity.idCard("110101199003073490"), "110***********3490");
        // 18-X
        Assert.assertEquals(LogParamEntity.idCard("11010119800307371X"), "110***********371X");
        // 18-x
        Assert.assertEquals(LogParamEntity.idCard("11010119800307371x"), "110***********371x");
        // birthday yyyy
        Assert.assertEquals(LogParamEntity.idCard("110101100003073490"), "110***********3490");
        // birthday mm
        Assert.assertEquals(LogParamEntity.idCard("110101199019073490"), "110101199019073490");
        // birthday dd
        Assert.assertEquals(LogParamEntity.idCard("110101199012403490"), "110101199012403490");
        // 18
        Assert.assertEquals(LogParamEntity.idCard("215317628549432730"), "215317628549432730");
        // 19
        Assert.assertEquals(LogParamEntity.idCard("1101011990030734901"), "1101011990030734901");
        // 17
        Assert.assertEquals(LogParamEntity.idCard("11010119900307349"), "11010119900307349");
        Assert.assertEquals(LogParamEntity.idCard("0"), "0");
        Assert.assertEquals(LogParamEntity.idCard("1"), "1");
        // empty
        Assert.assertEquals(LogParamEntity.idCard(""), "");
        // space
        Assert.assertEquals(LogParamEntity.idCard(" "), "");
        // null
        Assert.assertEquals(LogParamEntity.idCard(null), "");
        Assert.assertEquals(LogParamEntity.idCard("null"), "null");
        Assert.assertEquals(LogParamEntity.idCard("NULL"), "NULL");
    }

    @Test
    public void bankCardTest() {
        // 12
        Assert.assertEquals(LogParamEntity.bankCard("126681828840"), "1266****8840");
        // 13
        Assert.assertEquals(LogParamEntity.bankCard("9203194057956"), "9203*****7956");
        // 14
        Assert.assertEquals(LogParamEntity.bankCard("85707295514568"), "8570******4568");
        // 15
        Assert.assertEquals(LogParamEntity.bankCard("766817939116866"), "7668*******6866");
        // 16
        Assert.assertEquals(LogParamEntity.bankCard("6613251764135068"), "6613********5068");
        // 17
        Assert.assertEquals(LogParamEntity.bankCard("56026093659074868"), "5602*********4868");
        // 18
        Assert.assertEquals(LogParamEntity.bankCard("420625815951766219"), "4206**********6219");
        // 19
        Assert.assertEquals(LogParamEntity.bankCard("3777345555354180853"), "3777***********0853");
        // 20
        Assert.assertEquals(LogParamEntity.bankCard("22932867964545603919"), "2293************3919");
        // head0
        Assert.assertEquals(LogParamEntity.bankCard("092866567524"), "092866567524");
        Assert.assertEquals(LogParamEntity.bankCard("0943189244762"), "0943189244762");
        Assert.assertEquals(LogParamEntity.bankCard("08061986789055"), "08061986789055");
        Assert.assertEquals(LogParamEntity.bankCard("031530405941200"), "031530405941200");
        Assert.assertEquals(LogParamEntity.bankCard("0746556365903113"), "0746556365903113");
        Assert.assertEquals(LogParamEntity.bankCard("08183143109826762"), "08183143109826762");
        Assert.assertEquals(LogParamEntity.bankCard("054192641763751234"), "054192641763751234");
        Assert.assertEquals(LogParamEntity.bankCard("0776227506608426834"), "0776227506608426834");
        Assert.assertEquals(LogParamEntity.bankCard("01657686370409488916"), "01657686370409488916");
        // 21
        Assert.assertEquals(LogParamEntity.bankCard("159767489735565030640"), "159767489735565030640");
        // 11
        Assert.assertEquals(LogParamEntity.bankCard("14507212307"), "14507212307");
        Assert.assertEquals(LogParamEntity.bankCard("0"), "0");
        Assert.assertEquals(LogParamEntity.bankCard("1"), "1");
        // empty
        Assert.assertEquals(LogParamEntity.bankCard(""), "");
        // space
        Assert.assertEquals(LogParamEntity.bankCard(" "), "");
        // null
        Assert.assertEquals(LogParamEntity.bankCard(null), "");
        Assert.assertEquals(LogParamEntity.bankCard("null"), "null");
        Assert.assertEquals(LogParamEntity.bankCard("NULL"), "NULL");
    }

    @Test
    public void cnNameTest() {
        Assert.assertEquals(LogParamEntity.cnName("张三"), "张*");
        Assert.assertEquals(LogParamEntity.cnName("王老五"), "王**");
        Assert.assertEquals(LogParamEntity.cnName("张三李四"), "张***");
        Assert.assertEquals(LogParamEntity.cnName("陈皮狗蛋儿"), "陈****");
        Assert.assertEquals(LogParamEntity.cnName("陈皮狗蛋儿汪"), "陈*****");
        Assert.assertEquals(LogParamEntity.cnName("新_疆-人·吖"), "新******");
        Assert.assertEquals(LogParamEntity.cnName("下_划_线_吖"), "下******");
        Assert.assertEquals(LogParamEntity.cnName("横-杠"), "横**");
        Assert.assertEquals(LogParamEntity.cnName("点·点"), "点**");
        Assert.assertEquals(LogParamEntity.cnName("两字·点"), "两***");
        Assert.assertEquals(LogParamEntity.cnName("两字·两字"), "两****");
        Assert.assertEquals(LogParamEntity.cnName("三个字·两字"), "三*****");
        Assert.assertEquals(LogParamEntity.cnName("张1"), "张1");
        Assert.assertEquals(LogParamEntity.cnName("张@"), "张@");
        Assert.assertEquals(LogParamEntity.cnName("张 "), "张 ");
        Assert.assertEquals(LogParamEntity.cnName("张13"), "张13");
        Assert.assertEquals(LogParamEntity.cnName("张@3"), "张@3");
        Assert.assertEquals(LogParamEntity.cnName("张 特"), "张 特");
        Assert.assertEquals(LogParamEntity.cnName("0"), "0");
        // empty
        Assert.assertEquals(LogParamEntity.cnName(""), "");
        // space
        Assert.assertEquals(LogParamEntity.cnName(" "), "");
        // null
        Assert.assertEquals(LogParamEntity.cnName(null), "");
        Assert.assertEquals(LogParamEntity.cnName("null"), "null");
        Assert.assertEquals(LogParamEntity.cnName("NULL"), "NULL");
    }

    @Test
    public void vCodeNum6Test() {
        // 6
        Assert.assertEquals(LogParamEntity.vCodeNum6("291511"), "2****1");
        // 5
        Assert.assertEquals(LogParamEntity.vCodeNum6("27247"), "27247");
        // 7
        Assert.assertEquals(LogParamEntity.vCodeNum6("3593151"), "3593151");
        // 1
        Assert.assertEquals(LogParamEntity.vCodeNum6("8"), "8");
        Assert.assertEquals(LogParamEntity.vCodeNum6("中文"), "中文");
        // empty
        Assert.assertEquals(LogParamEntity.vCodeNum6(""), "");
        // space
        Assert.assertEquals(LogParamEntity.vCodeNum6(" "), "");
        // null
        Assert.assertEquals(LogParamEntity.vCodeNum6(null), "");
        Assert.assertEquals(LogParamEntity.vCodeNum6("null"), "null");
        Assert.assertEquals(LogParamEntity.vCodeNum6("NULL"), "NULL");
    }

    @Test(priority = 1)
    public void appendTest() {
        //============ no key ==========
        Assert.assertEquals(new LogParamEntity(null).toJSONString(), "");
        Assert.assertEquals(new LogParamEntity("").toJSONString(), "");
        Assert.assertEquals(new LogParamEntity(" ").toJSONString(), " ");
        Assert.assertEquals(new LogParamEntity("I'm a string").toJSONString(), "I'm a string");
        Assert.assertEquals(new LogParamEntity(new User()).toJSONString(), "{\"age\":0}");
        Assert.assertEquals(new LogParamEntity().append(null).toJSONString(), "");
        Assert.assertEquals(new LogParamEntity().append(null).append("").toJSONString(), "{\"param1\":\"\",\"param2\":\"\"}");
        Assert.assertEquals(new LogParamEntity().append(null).append("").append(" ").toJSONString(), "{\"param3\":\" \",\"param1\":\"\",\"param2\":\"\"}");
        Assert.assertEquals(new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(new User()).toJSONString(),
                "{\"param7\":4.4,\"param8\":{\"age\":0},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param4\":1,\"param1\":\"\",\"param2\":\"\"}");

        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User cnName = new User("王老五-哈", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        LogParamEntity paramEntity = new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(jame).append(cnName);
        Assert.assertEquals(paramEntity.toJSONString(), "{\"param7\":4.4,\"param8\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"Jame\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param5\":\"2\",\"param6\":3.3,\"param9\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"王老五-哈\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param3\":\" \",\"param4\":1,\"param1\":\"\",\"param2\":\"\"}");

        //============ has key ==========
        Assert.assertEquals(new LogParamEntity("a1", null).toJSONString(), "{\"a1\":\"\"}");
        Assert.assertEquals(new LogParamEntity("a1", "").toJSONString(), "{\"a1\":\"\"}");
        Assert.assertEquals(new LogParamEntity("a1", " ").toJSONString(), "{\"a1\":\" \"}");
        Assert.assertEquals(new LogParamEntity("a1", "I'm a string").toJSONString(), "{\"a1\":\"I'm a string\"}");
        Assert.assertEquals(new LogParamEntity("a1", new User()).toJSONString(), "{\"a1\":{\"age\":0}}");
        Assert.assertEquals(new LogParamEntity().append("a1", null).toJSONString(), "{\"a1\":\"\"}");
        Assert.assertEquals(new LogParamEntity().append("a1", null).append("").toJSONString(), "{\"a1\":\"\",\"param2\":\"\"}");
        Assert.assertEquals(new LogParamEntity().append("a1", null).append("").append(" ").toJSONString(),
                "{\"a1\":\"\",\"param3\":\" \",\"param2\":\"\"}");
        Assert.assertEquals(new LogParamEntity().append("a1", null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(new User()).toJSONString(),
                "{\"a1\":\"\",\"param7\":4.4,\"param8\":{\"age\":0},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param4\":1,\"param2\":\"\"}");
        LogParamEntity paramEntity2 = new LogParamEntity().append(null).append("").append(" ").append("a1", 1).append("2").append(3.3).append(4.4f).append(jame).append("a2", cnName);
        Assert.assertEquals(paramEntity2.toJSONString(), "{\"a1\":1,\"param7\":4.4,\"param8\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"Jame\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"a2\":{\"age\":12,\"bankCard\":\"6222600260001072444\",\"idCard\":\"11010120100307889X\",\"name\":\"王老五-哈\",\"phone\":\"13112341234\",\"position\":\"programmer\"},\"param5\":\"2\",\"param6\":3.3,\"param3\":\" \",\"param1\":\"\",\"param2\":\"\"}");
    }

    @Test(priority = 2)
    public void toJSONStringTest() {
        Assert.assertEquals(new LogParamEntity().toJSONString(), "");
        Assert.assertEquals(new LogParamEntity().toJSONString(), "");
        Assert.assertEquals(new LogParamEntity(" ").toJSONString(), " ");
        Assert.assertEquals(new LogParamEntity("I'm a string").toJSONString(), "I'm a string");
        Assert.assertEquals(new LogParamEntity(new User()).toJSONString(), "{\"age\":0}");
        Assert.assertEquals(new LogParamEntity("a1", " ").toJSONString(), "{\"a1\":\" \"}");
        Assert.assertEquals(new LogParamEntity("a1", "I'm a string").toJSONString(), "{\"a1\":\"I'm a string\"}");
        Assert.assertEquals(new LogParamEntity("a1", new User()).toJSONString(), "{\"a1\":{\"age\":0}}");
        Assert.assertEquals(new LogParamEntity().append("a1", null).append("a2", 11).toJSONString(), "{\"a1\":\"\",\"a2\":11}");
        Assert.assertEquals(new LogParamEntity().append("aaa").append("a2", 11).toJSONString(), "{\"a2\":11,\"param1\":\"aaa\"}");
        Assert.assertEquals(new LogParamEntity().append(1).append("a2", null).toJSONString(), "{\"a2\":\"\",\"param1\":1}");
    }

    @Test
    public void getKeyTest() {
        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User cnName = new User("王老五-哈", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        LogParamEntity paramEntity = new LogParamEntity().append(null).append("").append(" ").append(1).append("2").append(3.3).append(4.4f).append(jame).append(cnName);
        Assert.assertEquals(paramEntity.getKey(null), "param10");

        Assert.assertEquals(new LogParamEntity().getKey(null), "param1");
        Assert.assertEquals(new LogParamEntity().getKey(""), "param1");
        Assert.assertEquals(new LogParamEntity().getKey(" "), "param1");
        // 重复key情况
        Assert.assertEquals(new LogParamEntity("a", 1).getKey("a"), "param2");
        Assert.assertEquals(new LogParamEntity("a1", 1).getKey("a2"), "a2");
        Assert.assertEquals(new LogParamEntity("a1", 1).getKey("@"), "@");
    }

}
