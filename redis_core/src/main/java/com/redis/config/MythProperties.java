package com.redis.config;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午7:34
 * Properties的改良类
 */
public class MythProperties extends Properties{

    public int getInt(String key){
        return Integer.parseInt(this.getProperty(key));
    }
    public String getString(String key){
        String result = RunStatus.PROPERTY_IS_NULL;
        try {
            String temp = this.getProperty(key);
            if(temp!=null){
                result = new String(temp.getBytes("utf-8"), "utf-8");
            }else{
                // 如果没有这个key就返回null
                return result;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(key);
    }
}
