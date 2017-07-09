package com.redis.config;

import com.redis.common.exception.ReadConfigException;
import com.redis.utils.MythReflect;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-21  下午1:50
 * 2017-07-08 21:38:57 ： mock就是模拟一个虚拟的环境，但是因为我使用的是正确的配置，所以就没有必要使用Mock了
 */
public class PropertyFileTest {
    private String testKey = "test.key";

    @Test
    public void testSave() throws Exception {
        PropertyFile.delete(testKey);
        String result = PropertyFile.save(testKey, "value");
        PropertyFile.delete(testKey);
        Assert.assertEquals(null, result);
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
        PropertyFile.save(testKey,"origin");
        String result = PropertyFile.update(testKey, "value");
        PropertyFile.delete(testKey);
        Assert.assertEquals("origin", result);

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
            System.out.println("----------------------------------------------------");
        }
    }
}
