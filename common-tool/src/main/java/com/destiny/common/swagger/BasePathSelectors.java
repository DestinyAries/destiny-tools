package com.destiny.common.swagger;

import com.google.common.base.Predicate;

/**
 * 路径选择器基类
 * @Author Destiny
 * @Version 1.0.0
 */
public class BasePathSelectors {
    /**
     * 包含型选择器
     * @param includeRegexArray 所需包含的正则 url path
     * @return
     */
    public static Predicate<String> includePathSelectors(final String... includeRegexArray) {
        return new Predicate<String>() {
            public boolean apply(String input) {
                return isMatchesRegex(input, includeRegexArray) ? true : false;
            }
        };
    }

    /**
     * 排除型选择器
     * @param excludeRegexArray 所需排除的正则 url path
     * @return
     */
    public static Predicate<String> excludePathSelectors(final String... excludeRegexArray) {
        return new Predicate<String>() {
            public boolean apply(String input) {
                return isMatchesRegex(input, excludeRegexArray) ? false : true;
            }
        };
    }

    /**
     * 正则匹配
     * @param input
     * @param regexList
     * @return
     */
    private static boolean isMatchesRegex(String input, String[] regexList) {
        for (String regex : regexList) {
            if (input.matches(regex)) {
                return true;
            }
        }
        return false;
    }
}
