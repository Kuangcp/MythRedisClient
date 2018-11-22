package com.redis.utils;

import com.redis.config.PoolManagement;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by https://github.com/kuangcp on 17-6-29  上午10:58
 * 使用本地，所以性能测出来很快，一般受限于带宽
 *
 * @author Kuangcp
 */
public class RedisBenchMark {

  @Test
  public void checkSet() {
    PoolManagement management = PoolManagement.getInstance();
    management.setCurrentPoolId("1010");
    Jedis jedis = management.getRedisPool().getJedis();

    System.out.println(MythTime.getTime());
    for (int i = 0; i < 30000; i++) {
      jedis.set(i + "", "23232323");
    }
    System.out.println(MythTime.getTime());
  }

}
