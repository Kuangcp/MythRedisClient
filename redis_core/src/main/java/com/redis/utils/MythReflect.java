package com.redis.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-20  上午8:20
 * 反射的工具类
 */
public class MythReflect {
    private static Class target;

    /**
     *
     * @param object 根据对象来得到class再获取属性集合
     * @return list 属性集合
     * @throws IllegalAccessException 没有权限访问
     */
    public static List<String> getFieldsByInstance(Object object) throws IllegalAccessException {
        target = object.getClass();
        return getFieldByClass(target);
    }

    /**
     *
     * @param targets 根据class直接获取属性集合
     * @return list属性集合
     */
    public static List<String> getFieldByClass(Class targets){
        target = targets;
        List< String> list = new ArrayList<>();
        Field[] fields = target.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);//设置属性可访问
//            String type = field.getType().toString();
            String name = field.getName();
            list.add(name);
        }
        return list;
    }

    /**
     *
     * @param object 对象
     * @return 返回 对象的所有 属性-值 map
     * @throws IllegalAccessException 没有权限访问
     */
    public static Map<String,Object> getFieldsValue(Object object) throws IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        target = object.getClass();
        Field[] fields = target.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);//设置属性可访问
            Object value = field.get(object);
            String name = field.getName();
            map.put(name,value);
        }
        return map;
    }
}
