package com.destiny.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(clazz);
    }
}
