package com.redis.config;

import com.redis.common.exception.ReadConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
//        System.out.println(propertyFile);
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
    public static void save(String key, String value) throws IOException {
//        System.out.println(propertyFile);
        MythProperties props = getProperties(Configs.propertyFile);
        OutputStream fos = new FileOutputStream(Configs.propertyFile);
        props.setProperty(key, value);
        props.store(fos, "Update '" + key + "' value");

    }
    // 删除key
    public static void delete(String key) throws IOException {
        MythProperties props = getProperties(Configs.propertyFile);
        OutputStream fos = new FileOutputStream(Configs.propertyFile);
        props.remove(key);

        props.store(fos, "Delete '" + key + "' value");

    }
    public static void update(){
//        delete();

    }
    // 获得最大的id，如果最开始没有就要新增
    public static int getMaxId() throws IOException{
        MythProperties props = getProperties(Configs.propertyFile);
//        OutputStream fos = new FileOutputStream(propertyFile);
        String maxId = props.getString("maxId");
        if(maxId==null){
            save("maxId",Configs.START_ID+"");
            // 修改后重新加载文件
            props = getProperties(Configs.propertyFile);
            maxId = props.getString("maxId");

        }
//        if(maxId==null) return 0;
        return Integer.parseInt(maxId);
    }

    // 为了展示面板的数据显示 ,列出配置文件所有连接
    public static Map<String,RedisPoolProperty> getAllPoolConfig() throws ReadConfigException {
        Map<String,RedisPoolProperty> map = new HashMap<>();
        int maxId = 0;
        MythProperties properties = null;
        try {
            maxId = getMaxId();
            properties = getProperties(Configs.propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载配置文件异常");
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
