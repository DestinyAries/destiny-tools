package com.destiny.common.enumeration;

/**
 * 开关枚举
 * @Author Destiny
 * @Version 1.0.0
 */
public enum OnOffEnum {

    ON(1, "turn on or enable or active something"),
    OFF(0, "turn off or disable or make something inactive"),
    ;

    private int value;
    private String description;

    OnOffEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
