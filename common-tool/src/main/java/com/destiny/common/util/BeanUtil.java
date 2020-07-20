package com.destiny.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * bean 工具类
 * @Author Destiny
 * @Date 2018-05-21
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * Bean 与 Bean 之间的 GETTER,SETTER 属性值拷贝
     * @param source 源 Bean
     * @param targetClass 目标 Bean 类
     * @param <T> 目标 Bean
     * @return
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        try {
            T targetObj = targetClass.newInstance();
            copyProperties(source, targetObj);
            return targetObj;
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * list 拷贝
     * @param sources
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> sources, Class<T> targetClass) {
        List<T> targets = new ArrayList<>(sources.size());
        for (Object source : sources) {
            targets.add(copyBean(source, targetClass));
        }
        return targets;
    }
}
