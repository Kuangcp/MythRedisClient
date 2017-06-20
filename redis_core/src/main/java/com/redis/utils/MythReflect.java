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
        for(Field field:target.getDeclaredFields()){
            field.setAccessible(true);//设置属性可访问
            Object value = field.get(object);
            String name = field.getName();
            map.put(name,value);
        }
        return map;
    }

    /**
     *
     * @param object 要赋值的对象
     * @param maps 属性名-属性值 映射
     * @throws IllegalAccessException 可能没有权限
     */
    public static Object setFieldsValue(Object object,Map<String,Object> maps) throws IllegalAccessException {
        target = object.getClass();
        for(Field field:target.getDeclaredFields()){
            field.setAccessible(true);
            String type = field.getType().getName();
//            System.out.println("type:"+type);
            switch (type){
                case "java.lang.Integer":field.set(object, Integer.parseInt(maps.get(field.getName()).toString()));
                break;
                case "java.lang.String": field.set(object, maps.get(field.getName()));
                break;
                case "boolean":field.set(object, "true".equals(maps.get(field.getName()).toString()));
                break;
            }
        }
        return object;
    }
}
