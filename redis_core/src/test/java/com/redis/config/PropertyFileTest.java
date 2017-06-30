package com.redis.config;

import com.redis.common.exception.ReadConfigException;
import com.redis.utils.MythReflect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-21  下午1:50
 * 全部测试成功，虽然还是没有理解Mock注解的意义，但是大致就是自动注入类似
 */
public class PropertyFileTest {
    @Mock
    Logger logger;
    @InjectMocks
    PropertyFile propertyFile;
    String testKey = "test.key";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSave() throws Exception {
        PropertyFile.delete(testKey);
        String result = PropertyFile.save(testKey, "value");
        Assert.assertEquals(null, result);
        PropertyFile.delete(testKey);
    }

    // 成功
    @Test
    public void testDelete() throws Exception {
//        testKey = "test.key5";
        String result = PropertyFile.delete(testKey);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testUpdate() throws Exception {
        PropertyFile.delete(testKey);
        propertyFile.save(testKey,"df");
        String result = PropertyFile.update(testKey, "value");
        PropertyFile.delete(testKey);
        Assert.assertEquals(null, result);

    }
    @Test
    public void testMaxId() throws ReadConfigException {
        int maxId = PropertyFile.getMaxId();
        System.out.println(maxId);
        assert(maxId>=1000);
    }
    // 展示配置文件的所有连接池配置的数据
    @Test
    public void testList() throws Exception {
        Map<String,RedisPoolProperty> map = PropertyFile.getAllPoolConfig();
        for(String key:map.keySet()){
            System.out.println(">> "+key+" <<");
            RedisPoolProperty property = map.get(key);
            Map lists= MythReflect.getFieldsValue(property);
            for(Object keys:lists.keySet()){
                System.out.println(keys.toString()+":"+lists.get(keys));
            }
            System.out.println("--------");
        }
    }
}
