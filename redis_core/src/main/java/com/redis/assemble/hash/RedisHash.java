package com.redis.assemble.hash;

import com.redis.common.Commands;
import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.TypeErrorException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-23  上午9:24
 */
public class RedisHash extends Commands {

  private static RedisHash redisHash;

  private RedisHash() {
  }

  public synchronized static RedisHash getInstance() {
    if (redisHash == null) {
      synchronized (RedisHash.class) {
        if (redisHash == null) {
          redisHash = new RedisHash();
        }
      }
    }
    return redisHash;
  }

  /**
   * 保存，键已存在就更新
   *
   * @param member 键
   * @param value 值
   * @return 1/0 成功失败，创建了就返回1 键已存在就更新或失败就返回0
   */
  public Long save(String key, String member, String value) {
    return getJedis().hset(key, member, value);
  }

  /**
   * 保存一个map对象进去 ， 更新式的保存
   */
  public String save(String key, Map<String, String> map) {
    return getJedis().hmset(key, map);
  }

  /**
   * 键不存在才保存
   *
   * @param member 键
   * @param value 值
   * @return 1/0 成功失败 键已存在就失败，返回0不执行保存
   */
  public Long saveWhenNotExist(String key, String member, String value) {
    return getJedis().hsetnx(key, member, value);
  }

  public String get(String key, String member) {
    return getJedis().hget(key, member);
  }

  public List<String> get(String key, String... member) {
    return getJedis().hmget(key, member);
  }

  /**
   * 得到所有的 键 Set集合
   */
  public Set<String> getMembers(String key) {
    return getJedis().hkeys(key);
  }

  /**
   * 得到所有的 值 List集合
   */
  public List<String> getValues(String key) {
    return getJedis().hvals(key);
  }

  /**
   * 得到所有的 键值对 map
   */
  public Map<String, String> getAllMap(String key) {
    return getJedis().hgetAll(key);
  }

  /**
   * 对 hash中 键的值增加一个整数
   *
   * @param member 键
   * @param value 值
   * @return 返回计算结果
   * @throws TypeErrorException 原有的值不是整型就会抛出异常
   */
  public Long increase(String key, String member, long value) throws TypeErrorException {
    Long result;
    try {
      result = getJedis().hincrBy(key, member, value);
    } catch (Exception e) {
      throw new TypeErrorException(ExceptionInfo.TYPE_ERROR, e, RedisHash.class);
    }
    return result;
  }

  /**
   * @param member 键
   * @param value 值
   * @return 运算结果
   * @throws TypeErrorException 原有值类型不是数值型
   */
  public Double increaseByFloat(String key, String member, double value) throws TypeErrorException {
    Double result;
    try {
      result = getJedis().hincrByFloat(key, member, value);
    } catch (Exception e) {
      throw new TypeErrorException(ExceptionInfo.TYPE_ERROR, e, RedisHash.class);
    }
    return result;
  }

  public Long length(String key) {
    return getJedis().hlen(key);
  }

  public Long remove(String key, String... members) {
    return getJedis().hdel(key, members);
  }

}
