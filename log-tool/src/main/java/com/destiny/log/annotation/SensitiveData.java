package com.destiny.log.annotation;

import com.destiny.log.enums.SensitiveDataTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感数据注解 - 标记敏感数据
 * @Author linwanrong
 * @Date 2019/9/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SensitiveData {
    SensitiveDataTypeEnum value();
}
