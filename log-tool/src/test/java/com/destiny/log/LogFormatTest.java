package com.destiny.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志规范测试 - JSON化 - DEMO
 * @Author linwanrong
 * @Date 2019/9/18
 */
@Slf4j
public class LogFormatTest {
    @Test
    public void logForDifferLevel() {
        User jame = new User("Jame", 12, "programmer", "11010120100307889X", "6222600260001072444", "13112341234");
        String oderNo = RandomUtil.randomNumbers(10);
        try {
            Integer.parseInt("hello");
        } catch (Exception e) {
            // -------------- 错误日志 --------------
            // 无参数打印情况
            log.error("错误日志描述", e);
            // 有参数打印情况 - 参数不指定名称
            log.error("有参数打印情况 - 参数不指定名称:" + new LogParamEntity(jame).append("testAddParam2").append(19.4).toJSONString(), e);
            log.error(new LogParamEntity("有参数打印情况 - 参数不指定名称").append(jame).append("testAddParam2").append(19.4).toJSONString(), e);
            log.error(StrUtil.format("有参数打印情况 - 参数不指定名称:{}", new LogParamEntity(jame).append("testAddParam2").append(19.4).toJSONString()), e);
            // 有参数打印情况 - 参数指定名称
            log.error(new LogParamEntity("有参数打印情况 - 参数指定名称").append("stringKey", "testAddParam2").toJSONString(), e);
            // 自定义打印情况
            log.error("订单不存在，订单编号：" + oderNo, e);
            log.error(StrUtil.format("订单不存在，订单编号：{}", oderNo), e);
        }

        // -------------- 警告/信息日志 --------------
        // 1. 第一个参数为方法名，必填。当没有其余参数的情况下，此内容将放在desc中。
        // 2. 第二个参数为日志描述desc。若无描述，在有参数需设第二个参数为null，无参数则只需第一个参数即可
        // 3. 第三个参数及其后续参数为params
        // 无描述，无参数情况 - 【注意】开发时做日志记录强烈杜绝这种情况
        log.warn("main");
        // 无描述，有参数情况
        log.warn("main", null, "这是一个参数");
        log.warn("main", null, "这是一个参数", "这是第二个参数");
        log.warn("main", null, "这是一个参数", 2, jame);
        // 有描述，无参数情况
        log.warn("main", "我是一个警告日志的描述");
        // 有描述，有参数情况
        log.warn("main", "我是一个警告日志的描述", jame);
        // 自定义打印情况
        log.warn("main", "订单不存在，订单编号：" + oderNo);
        // INFO 日志打印与 WARN 相同
        log.info("main", null, "这是一个参数", 2, jame);
    }

    @Test
    public void logDemoByDiffModuleInProject() {
        Exception e = new Exception();
        Map<String, Object> params = new HashMap<>();
        String defaultValue = "";
        String now = DateUtil.now();
        String oderNo = RandomUtil.randomNumbers(10);

        // interceptor
        log.info("XXXInterceptor", "IP记录", "113.67.230.71");

        // api
        // 1. 入参校验失败结果记录 - WARN
        log.warn("XXXMethod", "入参校验失败", "name 为空");
        // 2. 入参校验成功的参数内容记录 - INFO
        log.info("XXXMethod", "入参校验成功", params);// params 为对应入参实体

        // service
        // 1. 捕获异常日志记录 - ERROR
        log.error("订单处理异常", e);// service 当前方法功能模块描述
        // 2. 业务逻辑校验不通过 - WARN
        log.warn("XXXMethod", "此订单号已存在，不能新增", oderNo);
        // 3. 参数校验为空，走默认策略且需记录的情况 - WARN
        log.warn("XXXMethod", "xxx.properties不存在，启用默认配置", "默认配置为:xxx");
        // 4. 信息记录 - INFO
        // 默认参数key - "params":"{\"param1\":\"2019-09-23 20:16:14\",\"param2\":\"0768776227\"}"
        log.info("XXXMethod", "订单创建成功", now, oderNo);
        // 自定义参数key - "params":"{\"订单号\":\"6877386475\",\"成功时间\":\"2019-09-23 20:16:14\"}"
        log.info("XXXMethod", "订单创建成功", LogParamEntity.setKeys("成功时间", "订单号"), now, oderNo);

        // component
        // 1. 捕获异常日志记录 - ERROR
        log.error("XXX组件XXX功能异常", e);
        // 2. 入参或业务逻辑校验不通过 - WARN
        log.warn("XXXMethod", "入参校验失败", "XXX为空");
        log.warn("XXXMethod", "XXX不能重复创建", params);
        // 3. 参数校验为空，走默认策略且需记录的情况 - WARN
        log.warn("XXXMethod", "xxx.properties不存在，启用默认配置", "默认配置为:xxx");
        // 4. 信息记录 - INFO
        log.info("XXXMethod", "XXX功能开始执行", now, params);
        log.info("XXXMethod", "XXX功能结束执行", now, params);
        log.info("XXXMethod", "XXX功能开始执行", LogParamEntity.setKeys("执行时间"), now);

        // util
        // 1. 捕获异常日志记录 - ERROR
        log.error("XXX工具XXX功能异常", e);
        // 2. 参数校验为空，走默认策略且需记录的情况 - WARN
        log.warn("XXXMethod", "XXX为空，启用默认配置", defaultValue);
        // 3. 信息记录 - INFO
        log.info("XXXMethod", "XXX处理完成", params);
        log.info("XXXMethod", "XXX处理完成", LogParamEntity.setKeys("自定义参数说明"), params);
    }

    @Test
    public void setKeysToLogParamEntity() {
        String oderNo = RandomUtil.randomNumbers(10);
        String now = DateUtil.now();
        // {"method":"XXXMethod","params":"{\"订单号\":\"2189891733\",\"成功时间\":\"2019-09-23\"}","desc":"订单创建成功"}
        log.info("XXXMethod", "订单创建成功1", LogParamEntity.setKeys("成功时间", "订单号"), now, oderNo);
        log.info("XXXMethod", "订单创建成功2", LogParamEntity.setKeys("成功时间", "订单号"), now, oderNo, 3);
        log.info("XXXMethod", "订单创建成功3", LogParamEntity.setKeys("成功时间", "订单号", "test"), now, oderNo);
        log.info("XXXMethod", "订单创建成功4", LogParamEntity.setKeys(), now, oderNo, 3);
        log.info("XXXMethod", "订单创建成功5", LogParamEntity.setKeys(), now, oderNo);
    }
}
