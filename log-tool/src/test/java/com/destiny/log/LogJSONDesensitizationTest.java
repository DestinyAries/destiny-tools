package com.destiny.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 日志 脱敏+JSON化
 * 适用场景 - ELK - json filter
 * @Author linwanrong
 * @Date 2019/9/21
 */
@Slf4j
public class LogJSONDesensitizationTest {

    @Test
    public void jsonAndDesensitizationTest() {
        User engUser = new User("Jame", 12, "", "", "", "");
        User cnNameUser = new User("王老五", 12, "", null, null, null);
        User errorPhone = new User("手机错", 12, "", null, null, "1");
        User errorId = new User("身份证错", 12, "", "2", null, "1");
        User errorBank = new User("银行卡错", 12, "", "2", "3", "1");
        User rightPhone = new User("手机对", 12, "", null, null, "13412341234");
        User rightId = new User("身份证对", 12, "", "11010120100307889X", null, "1");
        User rightBank = new User("银行卡对", 12, "", "2", "6222600260001072444", "1");
        log.info("main", "脱敏+JSON化测试", engUser, cnNameUser, errorPhone, errorId, errorBank, rightPhone, rightId, rightBank);
    }

}
