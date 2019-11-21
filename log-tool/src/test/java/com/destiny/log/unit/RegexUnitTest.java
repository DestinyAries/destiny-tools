package com.destiny.log.unit;

import cn.hutool.core.util.RandomUtil;
import com.destiny.log.enums.SensitiveDataTypeEnum;
import com.destiny.log.util.DesensitizationUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @Author linwanrong
 * @Date 2019/9/23 18:37
 */
public class RegexUnitTest {
    /**
     * 此类测试依赖 - DesensitizationUtil.starDataByRule
     * 故需要先保证
     */
    @BeforeClass
    public void starDataByRuleTest() {
        DesensitizationUtilTest desensitizationUtilUnitTest = new DesensitizationUtilTest();
        desensitizationUtilUnitTest.starDataByRuleTest();
    }

    /**
     * 正则测试 - 手机号
     * 单元测试 - DesensitizationUtil.ridSensitiveDataByEnum - weak match
     */
    @Test
    public void regexPhoneTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.PHONE;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();

        // 正常脱敏
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("10012341234", currentEnum, false),
                DesensitizationUtil.starDataByRule("10012341234", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("11212341234", currentEnum, false),
                DesensitizationUtil.starDataByRule("11212341234", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("12112341234", currentEnum, false),
                DesensitizationUtil.starDataByRule("12112341234", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("19912341234", currentEnum, false),
                DesensitizationUtil.starDataByRule("19912341234", headKeepLength, tailKeepLength));
        // 位数 + 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("199123412341", currentEnum, false), "199123412341");
        // 位数 - 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1991234123", currentEnum, false), "1991234123");
        // 0
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0", currentEnum, false), "0");
        // 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1", currentEnum, false), "1");
        // empty string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("", currentEnum, false), "");
        // space string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(" ", currentEnum, false), " ");
        // null
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, currentEnum, false), null);
        // null string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("null", currentEnum, false), "null");
        // NULL string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("NULL", currentEnum, false), "NULL");
        // 非1开头
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("01112341234", currentEnum, false), "01112341234");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0011123412341", currentEnum, false), "0011123412341");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0111234123", currentEnum, false), "0111234123");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("91112341234", currentEnum, false), "91112341234");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("9011123412341", currentEnum, false), "9011123412341");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("9111234123", currentEnum, false), "9111234123");
    }

    /**
     * 正则测试 - 身份证号
     * 单元测试 - DesensitizationUtil.ridSensitiveDataByEnum - weak match
     */
    @Test
    public void regexIdCardTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.ID_CARD;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();

        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("110101199003073490", currentEnum, false),
                DesensitizationUtil.starDataByRule("110101199003073490", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("11010119800307371X", currentEnum, false),
                DesensitizationUtil.starDataByRule("11010119800307371X", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("11010119800307371x", currentEnum, false),
                DesensitizationUtil.starDataByRule("11010119800307371x", headKeepLength, tailKeepLength));
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("110101100003073490", currentEnum, false),
                DesensitizationUtil.starDataByRule("110101100003073490", headKeepLength, tailKeepLength));
        // 出生月份不对
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("110101199019073490", currentEnum, false), "110101199019073490");
        // 出生日期不对
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("110101199012403490", currentEnum, false), "110101199012403490");
        // 18位
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("187327189107055352", currentEnum, false), "187***********5352");
        // 19位
        String random19 = RandomUtil.randomNumbers(19);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(random19, currentEnum, false), random19);
        // 17位
        String random17 = RandomUtil.randomNumbers(17);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(random17, currentEnum, false), random17);
        // 0
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0", currentEnum, false), "0");
        // 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1", currentEnum, false), "1");
        // empty string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("", currentEnum, false), "");
        // space string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(" ", currentEnum, false), " ");
        // null
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, currentEnum, false), null);
        // null string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("null", currentEnum, false), "null");
        // NULL string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("NULL", currentEnum, false), "NULL");
    }

    /**
     * 正则测试 - 银行卡号
     * 单元测试 - DesensitizationUtil.ridSensitiveDataByEnum - weak match
     * PS. 测试此方法前需测试 - DesensitizationUtil.starDataByRule
     */
    @Test
    public void regexBankCardTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.BANK_CARD;
        for (int i = 0; i < 10; i++) {
            for (int j = 10; j < 25; j++) {
                String num = i + "" + RandomUtil.randomNumbers(j);
                if (i == 0) {
                    Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(num, currentEnum, false), num);
                } else {
                    // 银行卡长度范围[12,20]
                    if (num.length() >= 12 && num.length() <= 20) {
                        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(num, currentEnum, false),
                                DesensitizationUtil.starDataByRule(num, 4, 4));
                    } else {
                        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(num, currentEnum, false), num);
                    }
                }
            }
        }
        // 0
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0", currentEnum, false), "0");
        // 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1", currentEnum, false), "1");
        // empty string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("", currentEnum, false), "");
        // space string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(" ", currentEnum, false), " ");
        // null
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, currentEnum, false), null);
        // null string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("null", currentEnum, false), "null");
        // NULL string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("NULL", currentEnum, false), "NULL");
    }

    /**
     * 正则测试 - 中文姓名
     * 单元测试 - DesensitizationUtil.ridSensitiveDataByEnum - weak match
     */
    @Test
    public void regexCNNameTest() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.CN_NAME;
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张三", currentEnum, false), "张*");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("王老五", currentEnum, false), "王**");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张三李四", currentEnum, false), "张***");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("陈皮狗蛋儿", currentEnum, false), "陈****");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("陈皮狗蛋儿汪", currentEnum, false), "陈*****");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("新_疆-人·吖", currentEnum, false), "新******");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("下_划_线_吖", currentEnum, false), "下******");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("横-杠", currentEnum, false), "横**");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("点·点", currentEnum, false), "点**");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("两字·点", currentEnum, false), "两***");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("两字·两字", currentEnum, false), "两****");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("三字·两字", currentEnum, false), "三****");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张1", currentEnum, false), "张1");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张023298", currentEnum, false), "张023298");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张@#", currentEnum, false), "张@#");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张 特", currentEnum, false), "张 特");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张 特  @", currentEnum, false), "张 特  @");
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("张 特  ", currentEnum, false), "张 特  ");

        // 0
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0", currentEnum, false), "0");
        // 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1", currentEnum, false), "1");
        // empty string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("", currentEnum, false), "");
        // space string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(" ", currentEnum, false), " ");
        // null
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, currentEnum, false), null);
        // null string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("null", currentEnum, false), "null");
        // NULL string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("NULL", currentEnum, false), "NULL");
    }

    /**
     * 正则测试 - 6位纯数字型验证码
     * 单元测试 - DesensitizationUtil.ridSensitiveDataByEnum - weak match
     * PS. 测试此方法前需测试 - DesensitizationUtil.starDataByRule
     */
    @Test
    public void regexVCodeNum6Test() {
        final SensitiveDataTypeEnum currentEnum = SensitiveDataTypeEnum.V_CODE_NUM_6;
        final int headKeepLength = currentEnum.getHeadKeepLength();
        final int tailKeepLength = currentEnum.getTailKeepLength();
        for (int i = 0; i < 20; i++) {
            String num = RandomUtil.randomNumbers(6);
            Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(num, currentEnum, false),
                    DesensitizationUtil.starDataByRule(num, headKeepLength, tailKeepLength));
        }
        String code5 = RandomUtil.randomNumbers(5);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(code5, currentEnum, false), code5);
        String code7 = RandomUtil.randomNumbers(7);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(code7, currentEnum, false), code7);
        String code1 = RandomUtil.randomNumbers(1);
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(code1, currentEnum, false), code1);

        // 0
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("0", currentEnum, false), "0");
        // 1
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("1", currentEnum, false), "1");
        // empty string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("", currentEnum, false), "");
        // space string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(" ", currentEnum, false), " ");
        // null
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum(null, currentEnum, false), null);
        // null string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("null", currentEnum, false), "null");
        // NULL string
        Assert.assertEquals(DesensitizationUtil.ridSensitiveDataByEnum("NULL", currentEnum, false), "NULL");
    }

}
