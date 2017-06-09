package com.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午7:07
 * 操作MythProperties的工具类
 */

public class PropertyFile {

    private static Logger logger = LoggerFactory.getLogger(PropertyFile.class);
    /**
     * 得到配置文件对象
     * @param propertyFile 文件名以及路径（完整）
     * @return
     * @throws IOException
     */
    public static MythProperties getProperties(String propertyFile) throws IOException {
        MythProperties props = new MythProperties();
        File file = new File(propertyFile);
        if(!file.exists())
            if(file.createNewFile()){
                logger.info("Create RedisConfigFile Success");
            }else{
                logger.info("Create RedisConfigFile Failed,Check the authority");
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


    // 写入key
    public static void save(String propertyFile, String key, String value) throws IOException {
        Properties props = getProperties(propertyFile);
        OutputStream fos = new FileOutputStream(propertyFile);
        props.setProperty(key, value);

        props.store(fos, "Update '" + key + "' value");

    }
    // 删除key
    public static void delete(String propertyFile, String key) throws IOException {
        Properties props = getProperties(propertyFile);
        OutputStream fos = new FileOutputStream(propertyFile);
        props.remove(key);

        props.store(fos, "Delete '" + key + "' value");

    }
}
