package com.destiny.log.enums;

/**
 * 敏感数据类型枚举
 * [1] 敏感数据日志记录格式要求 - 格式可自定义
 * 1. 手机号 - @phone:13112341234@
 * 2. 身份证号 - @idCard:11010120100307889X@
 * 3. 银行卡号 - @bankCard:6222600260001072444@
 * 4. 姓名 - @cnName:张三@
 * 5. 6位纯数字型验证码 - @VCodeNum6:134521@"
 *
 * [2] 脱敏规则
 * 1. 手机号 - 保留前三位后四位
 * 2. 身份证号 - 保留前三位后四位
 * 3. 银行卡号 - 保留前四位后四位
 * 4. 姓名 - 保留第一位(留姓不留名)
 * 5. 6位纯数字型验证码 - 保留前一位后一位
 *
 * [3] 强弱匹配
 * force - 强匹配，按照格式解析敏感数据，建议用于全文检索中使用
 * weak - 弱匹配，建议用于单数据使用
 *
 * @Author linwanrong
 * @Date 2019/9/12
 */
public enum SensitiveDataTypeEnum {
    BANK_CARD(1, "银行卡号", 4, 4, "([1-9]{1})(\\d{11,19})", "@bankCard:{}@"),// 银行卡长度范围[12,20]
    ID_CARD(2, "身份证号", 3, 4, "[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|X|x)", "@idCard:{}@"),
    PHONE(3, "手机号", 3, 4, "(1\\d{10}){1}", "@phone:{}@"),
    CN_NAME(4, "中文姓名", 1, 0, "[\\u4e00-\\u9fa5.·|_|-]{2,20}", "@cnName:{}@"),
    V_CODE_NUM_6(5, "6位纯数字型验证码", 1, 1, "\\d{6}", "@VCodeNum6:{}@"),
    ;
    /**
     * 枚举id
     */
    private int id;
    /**
     * 描述
     */
    private String desc;
    /**
     * 脱敏规则 - 头部保留位数
     */
    private int headKeepLength;
    /**
     * 脱敏规则 - 尾部保留位数
     */
    private int tailKeepLength;
    /**
     * 正则 - 弱匹配
     */
    private String regexWeak;
    /**
     * 强匹配格式
     */
    private String formatRule;

    SensitiveDataTypeEnum(int id, String desc, int headKeepLength, int tailKeepLength, String regexWeak, String formatRule) {
        this.id = id;
        this.desc = desc;
        this.headKeepLength = headKeepLength;
        this.tailKeepLength = tailKeepLength;
        this.regexWeak = regexWeak;
        this.formatRule = formatRule;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public int getHeadKeepLength() {
        return headKeepLength;
    }

    public int getTailKeepLength() {
        return tailKeepLength;
    }

    public String getRegexWeak() {
        return regexWeak;
    }

    public String getFormatRule() {
        return formatRule;
    }
}
