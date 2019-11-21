package com.destiny.log;

import com.destiny.log.enums.SensitiveDataTypeEnum;
import com.destiny.log.util.DesensitizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class RidSensitiveTest {

    @Test
    public void messageDesensitizationTest() {
        String test = " 示例 - 敏感数据日志记录格式要求：\n" +
                " * 1. 手机号 - @phone:13112341234@\n" +
                " * 2. 身份证号 - @idCard:11010120100307889X@\n" +
                " * 3. 银行卡号 - @bankCard:6222600260001072444@\n" +
                " * 4. 姓名 - @cnName:张三@\n" +
                " * 5. 6位数验证码 - @VCodeNum6:143214@\n" +
                " * 11. 手机号 - 13112341234\n" +
                " * 12. 身份证号 - 11010120100307889X\n" +
                " * 13. 银行卡号 - 6222600260001072444\n" +
                " * 14. 姓名 - 李四\n" +
                " * 15. 6位数验证码 - 143214"
                ;
        log.info("converter force match: {}\n==============\n", test);

        // 使用工具进行强匹配
        log.info("tool force match: {}\n==============\n", DesensitizationUtil.convertMessage(test));

        // 使用工具进行单独一个类型数据的弱匹配
        log.info("tool weak match bankCard: \n type:{} convertMsg:{}\n==============\n",
                SensitiveDataTypeEnum.BANK_CARD.getDesc(),
                DesensitizationUtil.convertMessage("6222600260001072444", false, SensitiveDataTypeEnum.BANK_CARD));
        log.info("tool weak match idCard: \n type:{} convertMsg:{}\n==============\n",
                SensitiveDataTypeEnum.ID_CARD.getDesc(),
                DesensitizationUtil.convertMessage("11010120100307889X", false, SensitiveDataTypeEnum.ID_CARD));
        log.info("tool weak match phone: \n type:{} convertMsg:{}\n==============\n",
                SensitiveDataTypeEnum.PHONE.getDesc(),
                DesensitizationUtil.convertMessage("18912341234", false, SensitiveDataTypeEnum.PHONE));
        log.info("tool weak match name:  \n type:{} convertMsg:{}\n==============\n",
                SensitiveDataTypeEnum.CN_NAME.getDesc(),
                DesensitizationUtil.convertMessage("王老五", false, SensitiveDataTypeEnum.CN_NAME));

        // 使用注解标记实体敏感字段，打印日志时脱敏处理
        User user = new User("王老五", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        log.info("annotation handle: {}\n==============\n", user);

        // 单独输入敏感字段
        log.info("phone is :{}", LogParamEntity.phone("18912341234"));
        log.info("BankCard is :{}", LogParamEntity.bankCard("6222600260001072444"));
        log.info("CNName is :{}", LogParamEntity.idCard("王老五"));
        log.info("IdCard is :{}", LogParamEntity.cnName("11010120100307889X"));
    }

}
