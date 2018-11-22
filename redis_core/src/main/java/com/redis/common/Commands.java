package com.redis.common;

import com.redis.config.Configs;
import com.redis.config.PoolManagement;
import com.redis.config.RedisPools;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-11  下午10:57
 * 声明公共的一些方法
 * 如果使用切面的话，对于日志记录功能的添加会比较方便，但是Spring框架还是没有到非用不可的地步
 * 如果使用Spring，是否是考虑注入Manager那个类
 */
public class Commands {

  public static PoolManagement management = PoolManagement.getInstance();
  // 当前所有的操作都是共享这个参数的： 当前连接池，当前的数据库
  private int db = 0;
  private RedisPools pools;
  private Jedis jedis;

  /**
   * @return 得到当前数据库的连接
   */
  public Jedis getJedis() {
    pools = management.getRedisPool();
    jedis = pools.getJedis();
    jedis.select(db);
    return jedis;
  }

  public String type(String key) {
    return getJedis().type(key);
  }

  /**
   * 修改已经存在的键的存活时间,适用于所有key
   *
   * @param key 键
   * @param second 大于等于0就expire，否则persist
   * @return persist 1:成功 0：key没有设存活时间，或者key不存在。expire 1：成功设置，0：key不存在代表2.1.3以下版本已经设置了存活时间就不能设置（高版本就随意更改）
   */
  public Long expire(String key, int second) {
    if (second >= 0)
    // second 为 0 就是立即删除，小于0也是
    {
      return getJedis().expire(key, second);
    } else {
      return getJedis().persist(key);
    }
  }

  public String getCurrentId() {
    if (management != null) {
      return management.getCurrentPoolId();
    } else {
      return null;
    }
  }

  public int getDb() {
    return db;
  }

  public void setDb(int db) {
    this.db = db;
  }

  public RedisPools getPools() {
    return pools;
  }

  // OK 或 null
  public String flushAll() {
    return getJedis().flushAll();
  }

  // OK 或 null
  public String flushDB(int db) {
    setDb(db);
    return getJedis().flushDB();
  }

  public Map<String, String> getStatus() {
    Map<String, String> map = new HashMap<>();
    Jedis jedis = getJedis();
//        System.out.println(jedis.info());
    String[] lists = Configs.REDIS_INFO;
    for (String key : lists) {
      String[] infos = jedis.info(key).split(Configs.REDIS_INFO_END);
      for (String s : infos) {
        if (!s.startsWith(Configs.REDIS_INFO_TITLE)) {
          String[] temp = s.split(Configs.REDIS_INFO_SEPARATE);
          map.put(temp[0], temp[1]);
        }
      }
    }
    return map;
  }

  /**
   * @param key 键
   * @return 1成功 0失败
   */
  public long deleteKey(String key) {
    return getJedis().del(key);
  }

  public long deleteKey(String... key) {
    return getJedis().del(key);
  }

}
// 开启事务
//Transaction e = jedis.multi();
// e.set("","");
//e.exec();