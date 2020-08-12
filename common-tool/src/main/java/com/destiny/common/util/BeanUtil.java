package com.destiny.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * bean 工具类
 * @Author Destiny
 * @Version 1.0.0
 */
@Slf4j
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

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
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
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
        return sources.stream().map((Function<Object, T>) o -> BeanUtil.copyBean(o, targetClass))
                .collect(Collectors.toList());
    }
}
