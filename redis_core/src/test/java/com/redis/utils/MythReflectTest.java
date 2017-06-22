package com.redis.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by https://github.com/kuangcp on 17-6-20  下午4:38
 * 这才是规范的Test使用方式
 */
public class MythReflectTest {

    @Test
    public void testGetFieldsByInstance() throws Exception {
        Target target = new Target();
        List<String> result = MythReflect.getFieldsByInstance(target);
        Assert.assertEquals(Collections.<String>singletonList("name"), result);
    }

    @Test
    public void testGetFieldByClass() throws Exception {
        List<String> result = MythReflect.getFieldByClass(Target.class);
        Assert.assertEquals(Collections.<String>singletonList("name"), result);
    }

    @Test
    public void testGetFieldsValue() throws Exception {
        Target target = new Target();
        target.setName("myth");
        Map<String, Object> result = MythReflect.getFieldsValue(target);
        Assert.assertEquals(new HashMap<String, Object>() {{
            put("name", "myth");
        }}, result);
    }

    // 测试将map注入到对象中去
    @Test
    public void testSetFieldsValue() throws Exception {
        Target target = new Target();
        Target result = (Target) MythReflect.setFieldsValue(target, new HashMap<String, Object>() {{
            put("name", "myth");
        }});
        Assert.assertEquals("myth", result.getName());
    }
}
// 测试辅助类
class Target{
    private String name;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}