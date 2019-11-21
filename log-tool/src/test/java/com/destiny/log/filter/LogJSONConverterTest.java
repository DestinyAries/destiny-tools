package com.destiny.log.filter;

import com.destiny.log.LogParamEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Author linwanrong
 * @Date 2019/9/24 16:58
 */
public class LogJSONConverterTest {
    @Test
    public void getParamsTest() {
        LogJSONConverter converter = new LogJSONConverter();
        //============ not remove first arg ===========
        Object[] argumentArray1 = new Object[]{"1#format#{}", 11.1, 22.2, 33.3, 44.4};
        Assert.assertEquals(converter.getParams(argumentArray1, false), "{\"param5\":44.4,\"param3\":22.2,\"param4\":33.3,\"param1\":\"1#format#{}\",\"param2\":11.1}");
        Object[] argumentArray2 = new Object[]{LogParamEntity.setKeys("1", "", "3"), 11.1, 22.2};
        Assert.assertEquals(converter.getParams(argumentArray2, false), "{\"1\":11.1,\"param2\":22.2}");
        Object[] argumentArray3 = new Object[]{LogParamEntity.setKeys("1", " ", "3"), 11.1, 22.2};
        Assert.assertEquals(converter.getParams(argumentArray3, false), "{\"1\":11.1,\"param2\":22.2}");
        // 自定义key重复
        Object[] argumentArray4 = new Object[]{LogParamEntity.setKeys("a1", "a1", "a2", "a3"), 11.1, 22.2, 33, 4};
        Assert.assertEquals(converter.getParams(argumentArray4, false), "{\"a1\":11.1,\"a2\":33,\"a3\":4,\"param2\":22.2}");

        //============ remove first arg ===========
        Assert.assertEquals(converter.getParams(argumentArray1, true), "{\"param3\":33.3,\"param4\":44.4,\"param1\":11.1,\"param2\":22.2}");
        Assert.assertEquals(converter.getParams(new Object[]{"test", null, 2}, true), "{\"param1\":\"\",\"param2\":2}");
        Assert.assertEquals(converter.getParams(new Object[]{"test", 2, null}, true), "{\"param1\":2,\"param2\":\"\"}");
        Assert.assertEquals(converter.getParams(new Object[]{"test", null}, true), "");
        Assert.assertEquals(converter.getParams(new Object[]{"test", ""}, true), "");
        Assert.assertEquals(converter.getParams(new Object[]{"test", " "}, true), " ");
    }
}
