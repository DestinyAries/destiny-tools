package com.destiny.common.util;

import com.destiny.common.entity.PageEntity;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Objects.requireNonNull(source);
        Objects.requireNonNull(targetClass);
        if (!isBean(targetClass)) {
            log.error("targetClass require be a bean");
            return null;
        }
        try {
            T targetObj = targetClass.newInstance();
            copyProperties(source, targetObj);
            return targetObj;
        } catch (InstantiationException e) {
            log.error("target class can not be instantiated", e);
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
        Objects.requireNonNull(sources);
        Objects.requireNonNull(targetClass);
        if (!isBean(targetClass)) {
            log.error("targetClass require be a bean");
            return null;
        }
        return sources.stream().map((Function<Object, T>) o -> BeanUtil.copyBean(o, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * page 拷贝
     * @param sources
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageEntity<T> copyPage(PageInfo<?> sources, Class<T> targetClass) {
        Objects.requireNonNull(sources);
        Objects.requireNonNull(targetClass);
        if (!isBean(targetClass)) {
            log.error("targetClass require be a bean");
            return null;
        }

        PageEntity pageEntity = new PageEntity();
        copyProperties(sources, pageEntity);
        if (sources.getList() == null || sources.getSize() == 0) {
            return pageEntity;
        }
        pageEntity.setList(copyList(sources.getList(), targetClass));
        return pageEntity;
    }

    /**
     * 输出非空属性内容
     * @param bean
     * @return
     */
    public static String printNonNullProperty(Object bean) {
        Objects.requireNonNull(bean);
        if (!isBean(bean.getClass())) {
            log.error("require be a bean");
            return null;
        }
        Map<String, Object> map = beanToMap(bean, false, true);
        return map == null ? null : bean.getClass().getSimpleName() + map.toString();
    }

}
