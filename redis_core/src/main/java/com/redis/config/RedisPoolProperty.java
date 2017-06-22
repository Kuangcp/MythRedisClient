package com.redis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by https://github.com/kuangcp on 17-6-9  下午9:09
 * RedisPool 的必要的非必要的所有连接属性
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedisPoolProperty {
    private static Logger logger= LoggerFactory.getLogger(RedisPoolProperty.class);
    // 10个属性,修改记得修改下面的方法，使用反射最省事
    private int maxActive;//最大连接数
    private int maxIdle;//最大闲置数,超过的就会被回收掉
    private int maxWaitMills;//获取连接等待的最长时间，超过就报异常
    private boolean testOnBorrow=true;//获取连接前是否测试，true就保证了获取到的每个连接是可用的，当然不一定获取的到23333
    private int port;
    private int timeout;//读取超时,是否是配置文件中的那个
    private String host;
    private String name;
    private String poolId;
    private String password="";//设定默认值为空字符串而不是null，因为后面的机制是要把null转String的

    /**
     * 前提是对象已经初始化好
     * @return 返回属性对象的键值对map
     */
    public Map<String,Object> getPropertyValueMap(){
        Map<String,Object> map = new HashMap<>();
        map.put(Configs.HOST,getHost());
        map.put(Configs.PORT,getPort());
        map.put(Configs.NAME,getName());
        map.put(Configs.MAX_ACTIVE,getMaxActive());
        map.put(Configs.MAX_IDLE,getMaxIdle());
        map.put(Configs.MAX_WAIT_MILLIS,getMaxWaitMills());
        map.put(Configs.TEST_ON_BORROW,isTestOnBorrow()+"");
        map.put(Configs.TIMEOUT,getTimeout());
        map.put(Configs.POOL_ID,getPoolId());
        map.put(Configs.PASSWORD,getPassword());
        return map;
    }

    /**
     * @return 返回属性对象的所有属性名
     */
    public static List<String> getPropertyList(){
        List<String> list = new ArrayList<>();
        list.add(Configs.HOST);
        list.add(Configs.PORT);
        list.add(Configs.NAME);
        list.add(Configs.MAX_ACTIVE);
        list.add(Configs.MAX_IDLE);
        list.add(Configs.TIMEOUT);
        list.add(Configs.TEST_ON_BORROW);
        list.add(Configs.POOL_ID);
        list.add(Configs.MAX_WAIT_MILLIS);
        list.add(Configs.PASSWORD);

        return list;
    }

    // 根据id从配置文件中加载配置对象
    public RedisPoolProperty initByIdFromFile(String poolId){
        String pre = poolId+ Configs.SEPARATE;
        MythProperties config = null;

        try {
            config = PropertyFile.getProperties(Configs.propertyFile);
            setPoolId(poolId);
            setMaxActive(config.getInt(pre + Configs.MAX_ACTIVE));
            setMaxIdle(config.getInt(pre + Configs.MAX_IDLE));
            setMaxWaitMills(config.getInt(pre + Configs.MAX_WAIT_MILLIS));
            setTimeout(config.getInt(pre + Configs.TIMEOUT));
            setTestOnBorrow(config.getBoolean(pre + Configs.TEST_ON_BORROW));
            setName(config.getString(pre + Configs.NAME));
            setHost(config.getString(pre + Configs.HOST));
            setPort(config.getInt(pre + Configs.PORT));
            setPassword(config.getString(pre+Configs.PASSWORD));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("打开"+Configs.propertyFile+"配置文件失败",e);
        }
        return this;
    }

    @Override
    public String toString() {
        return "RedisPoolProperty{" +
                ", name='" + name + '\'' +
                ", poolId='" + poolId + '\'' +
                ", password='" + password + '\'' +
                "maxActive=" + maxActive +
                ", maxIdle=" + maxIdle +
                ", maxWaitMills=" + maxWaitMills +
                ", testOnBorrow=" + testOnBorrow +
                ", port=" + port +
                ", timeout=" + timeout +
                ", host='" + host + '\'' +

                '}';
    }
}
