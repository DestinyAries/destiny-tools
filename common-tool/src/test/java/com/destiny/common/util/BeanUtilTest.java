package com.destiny.common.util;

import com.destiny.common.entity.BeanForTest;
import com.destiny.common.entity.BeanRespForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtilTest {
    @Test
    public void copyBeanTest() {
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyBean(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyBean(null, Object.class));
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyBean(Object.class, null));

        Map<Object, Object> map = BeanForTest.testObjMap();
        Assertions.assertFalse(BeanUtil.copyBean(map, Map.class) instanceof Map);
        Assertions.assertFalse(BeanUtil.copyBean(map, HashMap.class) instanceof HashMap);
        Assertions.assertFalse(BeanUtil.copyBean(map, ArrayList.class) instanceof ArrayList);
        Assertions.assertTrue(null == BeanUtil.copyBean(map, Map.class));
        Assertions.assertTrue(null == BeanUtil.copyBean(map, HashMap.class));
        Assertions.assertTrue(null == BeanUtil.copyBean(map, ArrayList.class));

        BeanRespForTest sourceNotFitToBean = BeanUtil.copyBean("a string", BeanRespForTest.class);
        Assertions.assertEquals("BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null)",
                sourceNotFitToBean.toString());

        // properties not fit to the bean
        Assertions.assertEquals("BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null)",
                BeanUtil.copyBean(map, BeanRespForTest.class).toString());

        Assertions.assertEquals("BeanRespForTest(name=张三, age=10, snLong=30, price=13.57900000000000062527760746888816356658935546875, " +
                "dateTime=2020-07-29T18:11:27.623, date=2020-07-29, time=18:11:27.623, objList=null, objMap=null)",
                BeanUtil.copyBean(new BeanForTest().init(), BeanRespForTest.class).toString());

        Assertions.assertEquals("BeanForTest(name=李四, age=66, sn=0, amount=0.0, num=null, snLong=78, price=998.1000000000000227373675443232059478759765625, " +
                "dateTime=2020-08-13T18:11:27.623, date=2020-08-13, time=18:11:27.623, objList=null, objMap=null)",
                BeanUtil.copyBean(new BeanRespForTest().init(), BeanForTest.class).toString());

        Assertions.assertEquals("BeanForTest(name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null)",
                BeanUtil.copyBean(new BeanRespForTest(), BeanForTest.class).toString());
    }

    @Test
    public void copyListTest() {
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyList(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyList(null, Object.class));
        List<String> stringList = Arrays.asList(new String[]{"hello", "world"});
        Assertions.assertThrows(NullPointerException.class, () -> BeanUtil.copyList(stringList, null));
        Assertions.assertFalse(BeanUtil.copyList(stringList, Map.class) instanceof Map);
        Assertions.assertFalse(BeanUtil.copyList(stringList, HashMap.class) instanceof HashMap);
        Assertions.assertFalse(BeanUtil.copyList(stringList, ArrayList.class) instanceof ArrayList);
        Assertions.assertTrue(null == BeanUtil.copyList(stringList, Map.class));
        Assertions.assertTrue(null == BeanUtil.copyList(stringList, HashMap.class));
        Assertions.assertTrue(null == BeanUtil.copyList(stringList, ArrayList.class));

        List<BeanRespForTest> sourceNotFitToBeans = BeanUtil.copyList(stringList, BeanRespForTest.class);
        Assertions.assertTrue(sourceNotFitToBeans != null && sourceNotFitToBeans instanceof List && sourceNotFitToBeans.size() == 2);
        Assertions.assertTrue(sourceNotFitToBeans.get(0) instanceof BeanRespForTest);
        Assertions.assertEquals("[BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null), " +
                        "BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null)]",
                sourceNotFitToBeans.toString());

        List<Map<Object, Object>> propertiesNotFitMapList = new ArrayList<>();
        propertiesNotFitMapList.add(BeanForTest.testObjMap());
        propertiesNotFitMapList.add(new HashMap<>());
        List<BeanRespForTest> propertiesNotFitToBeans = BeanUtil.copyList(propertiesNotFitMapList, BeanRespForTest.class);
        Assertions.assertTrue(propertiesNotFitToBeans != null && propertiesNotFitToBeans instanceof List && propertiesNotFitToBeans.size() == 2);
        Assertions.assertTrue(propertiesNotFitToBeans.get(0) instanceof BeanRespForTest);
        Assertions.assertEquals("[BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null), " +
                        "BeanRespForTest(name=null, age=null, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null)]",
                propertiesNotFitToBeans.toString());

        List<BeanForTest> beanForTestList = new ArrayList<>();
        beanForTestList.add(new BeanForTest());
        beanForTestList.add(new BeanForTest().init());
        List<BeanRespForTest> beanRespForTests = BeanUtil.copyList(beanForTestList, BeanRespForTest.class);
        Assertions.assertTrue(beanRespForTests != null && beanRespForTests.size() == 2);
        Assertions.assertEquals("[BeanRespForTest(name=null, age=0, snLong=null, price=null, dateTime=null, date=null, time=null, objList=null, objMap=null), " +
                        "BeanRespForTest(name=张三, age=10, snLong=30, price=13.57900000000000062527760746888816356658935546875, dateTime=2020-07-29T18:11:27.623, " +
                        "date=2020-07-29, time=18:11:27.623, objList=null, objMap=null)]",
                beanRespForTests.toString());

        List<BeanRespForTest> containErrorTypeCopyList = new ArrayList<>();
        containErrorTypeCopyList.add(new BeanRespForTest());
        containErrorTypeCopyList.add(new BeanRespForTest().init());
        Assertions.assertEquals("[BeanForTest(name=null, age=0, sn=0, amount=0.0, num=null, snLong=null, price=null, " +
                        "dateTime=null, date=null, time=null, objList=null, objMap=null), " +
                        "BeanForTest(name=李四, age=66, sn=0, amount=0.0, num=null, snLong=78, price=998.1000000000000227373675443232059478759765625, " +
                        "dateTime=2020-08-13T18:11:27.623, date=2020-08-13, time=18:11:27.623, objList=null, objMap=null)]",
                BeanUtil.copyList(containErrorTypeCopyList, BeanForTest.class).toString());

        List<BeanRespForTest> beanForTests = new ArrayList<>();
        beanForTests.add(new BeanRespForTest().init());
        beanForTests.add(new BeanRespForTest().init().setTestList());
        Assertions.assertTrue(beanForTests != null && beanForTests.size() == 2);
        Assertions.assertEquals("[BeanRespForTest(name=李四, age=66, snLong=78, price=998.1000000000000227373675443232059478759765625, " +
                        "dateTime=2020-08-13T18:11:27.623, date=2020-08-13, time=18:11:27.623, objList=null, objMap=null), " +
                        "BeanRespForTest(name=李四, age=66, snLong=78, price=998.1000000000000227373675443232059478759765625, " +
                        "dateTime=2020-08-13T18:11:27.623, date=2020-08-13, time=18:11:27.623, objList=[list string, 2020-08-13T18:11:27.623], objMap=null)]",
                beanForTests.toString());
    }
}
