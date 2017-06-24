package com.redis.config;

import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.ReadConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午7:07
 * 操作MythProperties 主配置文件 的工具类
 */

public class PropertyFile {

    private static Logger logger = LoggerFactory.getLogger(PropertyFile.class);
    /**
     * 得到配置文件对象
     * @param propertyFile 文件名以及路径（完整）
     * @return 配置文件的对象
     * @throws IOException 文件异常
     */
    public static MythProperties getProperties(String propertyFile) throws IOException {
        MythProperties props = new MythProperties();
        File file = new File(propertyFile);
        if(!file.exists()) {
            if (file.createNewFile()) {
                logger.info(ExceptionInfo.CREATE_CONFIG_SUCCESS);
            } else {
                logger.info(ExceptionInfo.CREATE_CONFIG_FAILED);
            }
        }
        InputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(propertyFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
        props.load(is);
        is.close();
        return props;
    }
    /**
     * 保存属性,如果属性已经存在就直接覆盖，其实也可以用作修改使用，但是会污染数据
     * @param key 属性
     * @param value 值
     * @throws ReadConfigException 文件异常
     * @return 旧值
     */
    public static String  save(String key, String value) throws ReadConfigException {
        String result;
        try {
            MythProperties props = getProperties(Configs.PROPERTY_FILE);
            OutputStream fos = new FileOutputStream(Configs.PROPERTY_FILE);
            result = (String)props.setProperty(key, value);
            props.store(fos, "Update '" + key + "' value");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadConfigException(ExceptionInfo.SAVE_CONFIG__KEY_FAILED,e,PropertyFile.class);
        }
        return result;
    }
    /**
     * 删除指定属性
     * @param key 属性
     * @throws ReadConfigException 文件异常
     * @return 返回旧值，没有就返回null
     */
    public static String delete(String key) throws ReadConfigException {
        String result;
        try {
            MythProperties props = getProperties(Configs.PROPERTY_FILE);
            OutputStream fos = new FileOutputStream(Configs.PROPERTY_FILE);
            result = (String) props.remove(key);
            props.store(fos, "Delete '" + key + "' value");
        }catch (Exception e){
            e.printStackTrace();
            throw new ReadConfigException(ExceptionInfo.DELETE_CONFIG_KEY_FAILED,e,PropertyFile.class);
        }
        return result;

    }

    /**
     * 修改指定属性，如果不存在就返回null
     * @param key 属性
     * @param value 值
     * @throws ReadConfigException 文件异常
     * @return 返回的是旧值，如果没有该属性就返回null
     */
    public static String update(String key,String value) throws ReadConfigException {
        String result;
        try {
            MythProperties props = getProperties(Configs.PROPERTY_FILE);
            OutputStream fos = new FileOutputStream(Configs.PROPERTY_FILE);
            result = (String)props.replace(key,value);
            props.store(fos, "Update '" + key + "' value");
        }catch (Exception e){
            e.printStackTrace();
            throw new ReadConfigException(ExceptionInfo.UPDATE_CONFIG_KEY_FAILED,e,PropertyFile.class);
        }
        return result;


    }
    /**
     * 获得最大的id，如果最开始没有就要新增
     * @return Integer 返回最大的id（已用） 为了方便自增长
     * @throws ReadConfigException 文件异常
     */
    public static int getMaxId() throws  ReadConfigException {
        String maxId = null;
        try {
            MythProperties props = getProperties(Configs.PROPERTY_FILE);
            maxId = props.getString(Configs.MAX_POOL_ID);
            if (maxId == null) {
                save(Configs.MAX_POOL_ID, Configs.START_ID + "");
                props = getProperties(Configs.PROPERTY_FILE);// 修改后重新加载文件
                maxId = props.getString(Configs.MAX_POOL_ID);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ReadConfigException(ExceptionInfo.OPEN_CONFIG_FAILED+"-获取maxId",e,PropertyFile.class);
        }
        return Integer.parseInt(maxId);
    }
    /**
     * 为了展示面板的数据显示 ,列出配置文件所有连接
     * @return Map 数据
     * @throws ReadConfigException 文件异常
     */
    public static Map<String,RedisPoolProperty> getAllPoolConfig() throws ReadConfigException {
        Map<String,RedisPoolProperty> map = new HashMap<>();
        int maxId = 0;
        MythProperties properties;
        try {
            maxId = getMaxId();
            properties = getProperties(Configs.PROPERTY_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(ExceptionInfo.OPEN_CONFIG_FAILED,e);
            return map;
        }
        for(int i=Configs.START_ID;i<=maxId;i++){
            String poolId = properties.getString(i+Configs.SEPARATE+Configs.POOL_ID);
            if(poolId==null) continue;
            RedisPoolProperty property = RedisPoolProperty.initByIdFromConfig(poolId);
            map.put(poolId,property);
        }
        return map;
    }
}
