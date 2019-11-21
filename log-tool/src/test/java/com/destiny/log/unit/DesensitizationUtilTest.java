package com.destiny.log.unit;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.destiny.log.User;
import com.destiny.log.enums.SensitiveDataTypeEnum;
import com.destiny.log.util.DesensitizationUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author linwanrong
 * @Date 2019/9/24 19:53
 */
public class DesensitizationUtilTest {
    @Test
    public void ridSensitiveDataByEnumForceMatchTest() {
        // RegexUnitTest is test weak match, this method is test force match
        SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.ID_CARD;
        Map<String, String> map = new LinkedHashMap<>();
        map.put("phone-right", StrUtil.format(SensitiveDataTypeEnum.PHONE.getFormatRule(), "13412341234"));
        map.put("idCard-right", StrUtil.format(SensitiveDataTypeEnum.ID_CARD.getFormatRule(), "110101199003073490"));
        map.put("bankCard-right", StrUtil.format(SensitiveDataTypeEnum.BANK_CARD.getFormatRule(), RandomUtil.randomNumbers(19)));
        map.put("cnName-right", StrUtil.format(SensitiveDataTypeEnum.CN_NAME.getFormatRule(), "张三丰"));
        map.put("vCode-right", StrUtil.format(SensitiveDataTypeEnum.V_CODE_NUM_6.getFormatRule(), RandomUtil.randomNumbers(6)));
        map.put("phone-1", "@13412341234@");
        map.put("idCard-1", "@13412341234@");
        map.put("bankCard-1", "@13412341234@");
        map.put("cnName-1", "@王武@");
        map.put("vCode-1", "@123456@");

        Object c = null;
        map.put("phone-null", StrUtil.format(currentEnum.getFormatRule(), c));
        map.put("phone-empty", StrUtil.format(currentEnum.getFormatRule(), ""));
        map.put("phone-space", StrUtil.format(currentEnum.getFormatRule(), " "));

        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@phone:13412341234@", currentEnum, true), "@phone:13412341234@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@idCard:110101199003073490@", currentEnum, true), "110***********3490");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@bankCard:2462722912527805703@", currentEnum, true), "@bankCard:2462722912527805703@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@cnName:张三丰@", currentEnum, true), "@cnName:张三丰@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@VCodeNum6:539659@", currentEnum, true), "@VCodeNum6:539659@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@13412341234@", currentEnum, true), "@13412341234@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@13412341234@", currentEnum, true), "@13412341234@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@13412341234@", currentEnum, true), "@13412341234@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@王武@", currentEnum, true), "@王武@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@123456@", currentEnum, true), "@123456@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@idCard:null@", currentEnum, true), "@idCard:null@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@idCard:@", currentEnum, true), "@idCard:@");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("@idCard: @", currentEnum, true), "@idCard: @");

        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, null, true), null);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("123", null, true), "123");
    }

    @Test
    public void ridSensitiveDataTest() {
        String message = "示例 - 敏感数据日志记录格式要求：\n" +
                " * 1. 手机号 - @phone:13112341234@\n" +
                " * 2. 身份证号 - @idCard:11010120100307889X@\n" +
                " * 3. 银行卡号 - @bankCard:6222600260001072444@\n" +
                " * 4. 姓名 - @cnName:张三@\n" +
                " * 5. 6位数验证码 - @VCodeNum6:143214@\n" +
                " * 11. 手机号 - 13112341234\n" +
                " * 12. 身份证号 - 11010120100307889X\n" +
                " * 13. 银行卡号 - 6222600260001072444\n" +
                " * 14. 姓名 - 李四\n" +
                " * 15. 6位数验证码 - 143214";

        Assert.assertEquals(DesensitizationUtil.ridSensitiveData(message, -1, SensitiveDataTypeEnum.values().length),
                "示例 - 敏感数据日志记录格式要求：\n" +
                " * 1. 手机号 - @phone:13112341234@\n" +
                " * 2. 身份证号 - @idCard:11010120100307889X@\n" +
                " * 3. 银行卡号 - @bankCard:6222600260001072444@\n" +
                " * 4. 姓名 - @cnName:张三@\n" +
                " * 5. 6位数验证码 - @VCodeNum6:143214@\n" +
                " * 11. 手机号 - 13112341234\n" +
                " * 12. 身份证号 - 11010120100307889X\n" +
                " * 13. 银行卡号 - 6222600260001072444\n" +
                " * 14. 姓名 - 李四\n" +
                " * 15. 6位数验证码 - 143214");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveData(message, 0, 10),
                "示例 - 敏感数据日志记录格式要求：\n" +
                " * 1. 手机号 - @phone:13112341234@\n" +
                " * 2. 身份证号 - @idCard:11010120100307889X@\n" +
                " * 3. 银行卡号 - @bankCard:6222600260001072444@\n" +
                " * 4. 姓名 - @cnName:张三@\n" +
                " * 5. 6位数验证码 - @VCodeNum6:143214@\n" +
                " * 11. 手机号 - 13112341234\n" +
                " * 12. 身份证号 - 11010120100307889X\n" +
                " * 13. 银行卡号 - 6222600260001072444\n" +
                " * 14. 姓名 - 李四\n" +
                " * 15. 6位数验证码 - 143214");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveData(message, 0, SensitiveDataTypeEnum.values().length),
                "示例 - 敏感数据日志记录格式要求：\n" +
                " * 1. 手机号 - 131****1234\n" +
                " * 2. 身份证号 - 110***********889X\n" +
                " * 3. 银行卡号 - 6222***********2444\n" +
                " * 4. 姓名 - 张*\n" +
                " * 5. 6位数验证码 - 1****4\n" +
                " * 11. 手机号 - 13112341234\n" +
                " * 12. 身份证号 - 11010120100307889X\n" +
                " * 13. 银行卡号 - 6222600260001072444\n" +
                " * 14. 姓名 - 李四\n" +
                " * 15. 6位数验证码 - 143214");
    }

    @Test
    public void handleSensitiveDataAnnotationTest() {
        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234", "123456");
        User cnName = new User("张三丰", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User nameEmpty = new User("", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User nameSpace = new User(" ", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        User idCardEmpty = new User("", 12, "programmer", "", "6222600260001072444", "13112341234");
        User idCardNull = new User(" ", 12, "programmer", null, "6222600260001072444", "13112341234");
        User idCardSpace = new User(" ", 12, "programmer", " ", "6222600260001072444", "13112341234");
        User bankEmpty = new User("", 12, "programmer", "11010120100307889X", "", "13112341234");
//        User nameSpace = new User(" ", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
//        User nameEmpty = new User("", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
//        User nameSpace = new User(" ", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");


        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(jame)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"code\":\"1****6\",\"idCard\":\"110***********889X\",\"name\":\"Jame\",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(cnName)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"idCard\":\"110***********889X\",\"name\":\"张**\",\"phone\":\"131****1234\",\"position\":\"programmer\"}");

        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(null)), "null");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation("")), "\"\"");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(" ")), "\" \"");

        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(nameEmpty)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"idCard\":\"110***********889X\",\"name\":\"\",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(nameSpace)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"idCard\":\"110***********889X\",\"name\":\" \",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(idCardEmpty)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"idCard\":\"\",\"name\":\"\",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(idCardNull)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"name\":\" \",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(idCardSpace)),
                "{\"age\":12,\"bankCard\":\"6222***********2444\",\"idCard\":\" \",\"name\":\" \",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
        Assert.assertEquals(JSON.toJSONString(DesensitizationUtil.handleSensitiveDataAnnotation(bankEmpty)),
                "{\"age\":12,\"bankCard\":\"\",\"idCard\":\"110***********889X\",\"name\":\"\",\"phone\":\"131****1234\",\"position\":\"programmer\"}");
    }

    @Test
    public void starDataByRuleTest() {
        final String data = "abcdefg";// 7
        // 无保留，全打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, 0), "*******");
        // 保留末位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, 1), "******g");
        // 保留首位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 1, 0), "a******");
        // 保留前6位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 6, 0), "abcdef*");
        // 保留后6位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, 6), "*bcdefg");
        // 保留前7位==无打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 7, 0), data);
        // 保留后7位==无打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, 7), data);
        // 保留前8位，超出字符串长度，原值输出
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 8, 0), data);
        // 保留后8位，超出字符串长度，原值输出
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, 8), data);
        // 无保留，全打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, -1, 0), "*******");
        // 无保留，全打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 0, -1), "*******");
        // 无保留，全打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, -1, -1), "*******");
        // 保留末位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, -1, 1), "******g");
        // 保留首位
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 1, -1), "a******");
        // 保留前6位,后1位==无打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 6, 1), data);
        // 保留前1位,后6位==无打星
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 1, 6), data);
        // 保留前6位,后2位，超出字符串长度，原值输出
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 6, 2), data);
        // 保留前2位,后6位，超出字符串长度，原值输出
        Assert.assertEquals(DesensitizationUtil.starDataByRule(data, 2, 6), data);
        // null
        Assert.assertEquals(DesensitizationUtil.starDataByRule(null, 2, 6), null);
        // empty
        Assert.assertEquals(DesensitizationUtil.starDataByRule("", 2, 6), "");
        // space
        Assert.assertEquals(DesensitizationUtil.starDataByRule(" ", 2, 6), " ");
    }

    @Test
    public void getSensitiveDataByRuleTest() {
        // null
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule(null, SensitiveDataTypeEnum.PHONE), null);
        // empty
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("", SensitiveDataTypeEnum.PHONE), "");
        // space
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule(" ", SensitiveDataTypeEnum.PHONE), " ");

        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@", SensitiveDataTypeEnum.PHONE), "@");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@$@#!", SensitiveDataTypeEnum.PHONE), "@$@#!");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@aaa@", SensitiveDataTypeEnum.PHONE), "@aaa@");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@aaa@", SensitiveDataTypeEnum.PHONE), "@aaa@");

        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:null@", SensitiveDataTypeEnum.PHONE), "null");

        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:@", SensitiveDataTypeEnum.PHONE), "");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone: @", SensitiveDataTypeEnum.PHONE), " ");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:123@", SensitiveDataTypeEnum.PHONE), "123");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:@:@", SensitiveDataTypeEnum.PHONE), "@:");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule(":123", SensitiveDataTypeEnum.PHONE), ":123");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@:123", SensitiveDataTypeEnum.PHONE), "@:123");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@1:2:3@", SensitiveDataTypeEnum.PHONE), "@1:2:3@");

        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:null@", SensitiveDataTypeEnum.PHONE), "null");

        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("phone:null@", SensitiveDataTypeEnum.PHONE), "phone:null@");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@phone:null", SensitiveDataTypeEnum.PHONE), "@phone:null");
        Assert.assertEquals(DesensitizationUtil.getSensitiveDataByRule("@xx:null@", SensitiveDataTypeEnum.PHONE), "@xx:null@");
    }

}
