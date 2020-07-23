package com.destiny.log.unit;

import cn.hutool.core.util.RandomUtil;
import com.destiny.log.enums.SensitiveDataTypeEnum;
import com.destiny.log.util.DesensitizationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @Author linwanrong
 * @Date 2019/9/23 18:37
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegexUnitTest {
    /**
     * 此类测试依赖 - DesensitizationUtil.starDataByRule
     * 故需要先保证
     */
    @Test
    @Order(1)
    public void starDataByRuleTest() {
        DesensitizationUtilTest desensitizationUtilUnitTest = new DesensitizationUtilTest();
        desensitizationUtilUnitTest.starDataByRuleTest();
    }

    /**
     * 正则测试 - 手机号
     * 单元测试 - DesensitizationUtil.convertValue - weak match
     */
    @Test
    public void regexPhoneTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.PHONE;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();

        // 正常脱敏
        Assertions.assertEquals(DesensitizationUtil.convertValue("10012341234", currentEnum),
                DesensitizationUtil.starDataByRule("10012341234", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("11212341234", currentEnum),
                DesensitizationUtil.starDataByRule("11212341234", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("12112341234", currentEnum),
                DesensitizationUtil.starDataByRule("12112341234", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("19912341234", currentEnum),
                DesensitizationUtil.starDataByRule("19912341234", headKeepLength, tailKeepLength));
        // 位数 + 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("199123412341", currentEnum), "199123412341");
        // 位数 - 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1991234123", currentEnum), "1991234123");
        // 0
        Assertions.assertEquals(DesensitizationUtil.convertValue("0", currentEnum), "0");
        // 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1", currentEnum), "1");
        // empty string
        Assertions.assertEquals(DesensitizationUtil.convertValue("", currentEnum), "");
        // space string
        Assertions.assertEquals(DesensitizationUtil.convertValue(" ", currentEnum), " ");
        // null
        Assertions.assertEquals(DesensitizationUtil.convertValue(null, currentEnum), null);
        // null string
        Assertions.assertEquals(DesensitizationUtil.convertValue("null", currentEnum), "null");
        // NULL string
        Assertions.assertEquals(DesensitizationUtil.convertValue("NULL", currentEnum), "NULL");
        // 非1开头
        Assertions.assertEquals(DesensitizationUtil.convertValue("01112341234", currentEnum), "01112341234");
        Assertions.assertEquals(DesensitizationUtil.convertValue("0011123412341", currentEnum), "0011123412341");
        Assertions.assertEquals(DesensitizationUtil.convertValue("0111234123", currentEnum), "0111234123");
        Assertions.assertEquals(DesensitizationUtil.convertValue("91112341234", currentEnum), "91112341234");
        Assertions.assertEquals(DesensitizationUtil.convertValue("9011123412341", currentEnum), "9011123412341");
        Assertions.assertEquals(DesensitizationUtil.convertValue("9111234123", currentEnum), "9111234123");
    }

    /**
     * 正则测试 - 身份证号
     * 单元测试 - DesensitizationUtil.convertValue - weak match
     */
    @Test
    public void regexIdCardTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.ID_CARD;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();

        Assertions.assertEquals(DesensitizationUtil.convertValue("110101199003073490", currentEnum),
                DesensitizationUtil.starDataByRule("110101199003073490", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("11010119800307371X", currentEnum),
                DesensitizationUtil.starDataByRule("11010119800307371X", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("11010119800307371x", currentEnum),
                DesensitizationUtil.starDataByRule("11010119800307371x", headKeepLength, tailKeepLength));
        Assertions.assertEquals(DesensitizationUtil.convertValue("110101100003073490", currentEnum),
                DesensitizationUtil.starDataByRule("110101100003073490", headKeepLength, tailKeepLength));
        // 出生月份不对
        Assertions.assertEquals(DesensitizationUtil.convertValue("110101199019073490", currentEnum), "110101199019073490");
        // 出生日期不对
        Assertions.assertEquals(DesensitizationUtil.convertValue("110101199012403490", currentEnum), "110101199012403490");
        // 18位
        Assertions.assertEquals(DesensitizationUtil.convertValue("187327189107055352", currentEnum), "187***********5352");
        // 19位
        String random19 = RandomUtil.randomNumbers(19);
        Assertions.assertEquals(DesensitizationUtil.convertValue(random19, currentEnum), random19);
        // 17位
        String random17 = RandomUtil.randomNumbers(17);
        Assertions.assertEquals(DesensitizationUtil.convertValue(random17, currentEnum), random17);
        // 0
        Assertions.assertEquals(DesensitizationUtil.convertValue("0", currentEnum), "0");
        // 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1", currentEnum), "1");
        // empty string
        Assertions.assertEquals(DesensitizationUtil.convertValue("", currentEnum), "");
        // space string
        Assertions.assertEquals(DesensitizationUtil.convertValue(" ", currentEnum), " ");
        // null
        Assertions.assertEquals(DesensitizationUtil.convertValue(null, currentEnum), null);
        // null string
        Assertions.assertEquals(DesensitizationUtil.convertValue("null", currentEnum), "null");
        // NULL string
        Assertions.assertEquals(DesensitizationUtil.convertValue("NULL", currentEnum), "NULL");
    }

    /**
     * 正则测试 - 银行卡号
     * 单元测试 - DesensitizationUtil.convertValue - weak match
     * PS. 测试此方法前需测试 - DesensitizationUtil.starDataByRule
     */
    @Test
    public void regexBankCardTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.BANK_CARD;
        for (int i = 0; i < 10; i++) {
            for (int j = 10; j < 25; j++) {
                String num = i + "" + RandomUtil.randomNumbers(j);
                if (i == 0) {
                    Assertions.assertEquals(DesensitizationUtil.convertValue(num, currentEnum), num);
                } else {
                    // 银行卡长度范围[12,20]
                    if (num.length() >= 12 && num.length() <= 20) {
                        Assertions.assertEquals(DesensitizationUtil.convertValue(num, currentEnum),
                                DesensitizationUtil.starDataByRule(num, 4, 4));
                    } else {
                        Assertions.assertEquals(DesensitizationUtil.convertValue(num, currentEnum), num);
                    }
                }
            }
        }
        // 0
        Assertions.assertEquals(DesensitizationUtil.convertValue("0", currentEnum), "0");
        // 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1", currentEnum), "1");
        // empty string
        Assertions.assertEquals(DesensitizationUtil.convertValue("", currentEnum), "");
        // space string
        Assertions.assertEquals(DesensitizationUtil.convertValue(" ", currentEnum), " ");
        // null
        Assertions.assertEquals(DesensitizationUtil.convertValue(null, currentEnum), null);
        // null string
        Assertions.assertEquals(DesensitizationUtil.convertValue("null", currentEnum), "null");
        // NULL string
        Assertions.assertEquals(DesensitizationUtil.convertValue("NULL", currentEnum), "NULL");
    }

    /**
     * 正则测试 - 中文姓名
     * 单元测试 - DesensitizationUtil.convertValue - weak match
     */
    @Test
    public void regexCNNameTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.CN_NAME;
        Assertions.assertEquals(DesensitizationUtil.convertValue("张三", currentEnum), "张*");
        Assertions.assertEquals(DesensitizationUtil.convertValue("王老五", currentEnum), "王**");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张三李四", currentEnum), "张***");
        Assertions.assertEquals(DesensitizationUtil.convertValue("陈皮狗蛋儿", currentEnum), "陈****");
        Assertions.assertEquals(DesensitizationUtil.convertValue("陈皮狗蛋儿汪", currentEnum), "陈*****");
        Assertions.assertEquals(DesensitizationUtil.convertValue("新_疆-人·吖", currentEnum), "新******");
        Assertions.assertEquals(DesensitizationUtil.convertValue("下_划_线_吖", currentEnum), "下******");
        Assertions.assertEquals(DesensitizationUtil.convertValue("横-杠", currentEnum), "横**");
        Assertions.assertEquals(DesensitizationUtil.convertValue("点·点", currentEnum), "点**");
        Assertions.assertEquals(DesensitizationUtil.convertValue("两字·点", currentEnum), "两***");
        Assertions.assertEquals(DesensitizationUtil.convertValue("两字·两字", currentEnum), "两****");
        Assertions.assertEquals(DesensitizationUtil.convertValue("三字·两字", currentEnum), "三****");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张1", currentEnum), "张1");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张023298", currentEnum), "张023298");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张@#", currentEnum), "张@#");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张 特", currentEnum), "张 特");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张 特  @", currentEnum), "张 特  @");
        Assertions.assertEquals(DesensitizationUtil.convertValue("张 特  ", currentEnum), "张 特  ");

        // 0
        Assertions.assertEquals(DesensitizationUtil.convertValue("0", currentEnum), "0");
        // 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1", currentEnum), "1");
        // empty string
        Assertions.assertEquals(DesensitizationUtil.convertValue("", currentEnum), "");
        // space string
        Assertions.assertEquals(DesensitizationUtil.convertValue(" ", currentEnum), " ");
        // null
        Assertions.assertEquals(DesensitizationUtil.convertValue(null, currentEnum), null);
        // null string
        Assertions.assertEquals(DesensitizationUtil.convertValue("null", currentEnum), "null");
        // NULL string
        Assertions.assertEquals(DesensitizationUtil.convertValue("NULL", currentEnum), "NULL");
    }

    /**
     * 正则测试 - 6位纯数字型验证码
     * 单元测试 - DesensitizationUtil.convertValue - weak match
     * PS. 测试此方法前需测试 - DesensitizationUtil.starDataByRule
     */
    @Test
    public void regexVCodeNum6Test() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.V_CODE_NUM_6;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();
        for (int i = 0; i < 20; i++) {
            String num = RandomUtil.randomNumbers(6);
            Assertions.assertEquals(DesensitizationUtil.convertValue(num, currentEnum),
                    DesensitizationUtil.starDataByRule(num, headKeepLength, tailKeepLength));
        }
        String code5 = RandomUtil.randomNumbers(5);
        Assertions.assertEquals(DesensitizationUtil.convertValue(code5, currentEnum), code5);
        String code7 = RandomUtil.randomNumbers(7);
        Assertions.assertEquals(DesensitizationUtil.convertValue(code7, currentEnum), code7);
        String code1 = RandomUtil.randomNumbers(1);
        Assertions.assertEquals(DesensitizationUtil.convertValue(code1, currentEnum), code1);

        // 0
        Assertions.assertEquals(DesensitizationUtil.convertValue("0", currentEnum), "0");
        // 1
        Assertions.assertEquals(DesensitizationUtil.convertValue("1", currentEnum), "1");
        // empty string
        Assertions.assertEquals(DesensitizationUtil.convertValue("", currentEnum), "");
        // space string
        Assertions.assertEquals(DesensitizationUtil.convertValue(" ", currentEnum), " ");
        // null
        Assertions.assertEquals(DesensitizationUtil.convertValue(null, currentEnum), null);
        // null string
        Assertions.assertEquals(DesensitizationUtil.convertValue("null", currentEnum), "null");
        // NULL string
        Assertions.assertEquals(DesensitizationUtil.convertValue("NULL", currentEnum), "NULL");
    }

}
