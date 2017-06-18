package com.redis.assemble.key;

import com.redis.common.Commands;
import com.redis.common.domain.Elements;
import com.redis.common.domain.ElementsType;
import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.TypeErrorException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:45
 * 不需要对当前数据库id进行时刻的监控，前端操作切换了数据库就把db换掉即可，共享db
 */
@Component
public class RedisKey extends Commands{

    // TODO 判断类型，并不是所有的类型都可以用这个
    /**
     *
     * @param key 键
     * @return 有就返回，没有就返回null
     */
    public String get(String key){
        return getJedis().get(key);
    }

    public String set (String key,String value){
        return getJedis().set(key,value);
    }

    /**
     * @param key 键
     * @return  1成功 0失败
     */
    public long deleteKey(String key){
        return getJedis().del(key);
    }
    //转化成utf 码？
    public byte[] dump(String key){
        return getJedis().dump(key);
    }

    public long expire(String key,int second){
        if(second != -1)
            return getJedis().expire(key, second);
        else
            return getJedis().persist(key);
    }
    public String setExpire(String key,int second,String value){
        return getJedis().setex(key,second,value);
    }

    /**
     * 使用了异常来作为流程控制，虽然说不符合正统的思想，但是很方便，而且在控制范围内
     * @param key 键
     * @return 返回增加后的值
     * @throws TypeErrorException 如果值不是数值就抛异常
     */
    public long increaseKey(String key) throws TypeErrorException {
        checkIntValue(key);
        return getJedis().incr(key);
    }
    public long decreaseKey(String key) throws TypeErrorException {
        checkIntValue(key);
        return getJedis().decr(key);
    }
    // 试试能不能强转
    private void checkIntValue(String key) throws TypeErrorException {
        try {
            Integer.parseInt(getJedis().get(key));
        }catch (Exception e){
            throw new TypeErrorException(ExceptionInfo.VALUE_NOT_INT,e,RedisKey.class);
        }
    }

    /**
     *
     * @param key 如果键存在就追加值，不存在就新建
     * @param value 追加的内容
     * @return 返回追加后值的长度
     */
    public long append(String key,String value){
        return getJedis().append(key,value);
    }
    /**
     * 获取多个key
     * @param keys 多个key
     * @return list
     */
    public List<String> getMultiKeys(String... keys){
        return getJedis().mget(keys);
    }

    /**
     * set多个key
     * @param keys key-value 顺序，偶数个参数
     * @return Status code reply Basically +OK as MSET can't fail
     */
    public String setMultiKey(String... keys){
        return getJedis().mset(keys);
    }
    public String getEncoding(String key){
        return getJedis().objectEncoding(key);
    }
    //列出当前数据库所有key
    public Set<Elements> listKeys(){
        Set<Elements> elementsSet = new TreeSet<>();
        Set<String> keys = getJedis().keys("*");
        for (String key : keys) {
            ElementsType nodeType = getValueType(key);
            Elements node = new Elements(getDb(), getCurrentId(), key, nodeType);
            elementsSet.add(node);
        }
        return elementsSet;
    }

}
