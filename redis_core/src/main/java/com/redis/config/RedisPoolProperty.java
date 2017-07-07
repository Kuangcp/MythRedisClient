package com.redis.config;

import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.NoticeInfo;
import com.redis.common.exception.ReadConfigException;
import com.redis.utils.MythReflect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    // 最好不要在配置属性类中添加logger对象
    // 10个属性,修改记得修改下面的方法，使用反射最省事
    private Integer maxActive;//最大连接数
    private Integer maxIdle;//最大闲置数,超过的就会被回收掉
    private Integer maxWaitMills;//获取连接等待的最长时间，超过就报异常
    private boolean testOnBorrow=true;//获取连接前是否测试，true就保证了获取到的每个连接是可用的，当然不一定获取的到23333
    private Integer port;
    private Integer timeout;//读取超时,是否是配置文件中的那个
    private String host;
    private String name;
    private String poolId;
    private String password="";//设定默认值为空字符串而不是null，因为后面的机制是要把null转String的
    private static Logger logger = LoggerFactory.getLogger(RedisPoolProperty.class);

//    /**
//     * 根据id从配置文件中加载配置对象
//     * @param poolId id
//     * @return 返回从配置文件中装载好数据的property对象
//     */
//    public RedisPoolProperty initByIdFromFile(String poolId) throws ReadConfigException {
//        String pre = poolId+ Configs.SEPARATE;
//        MythProperties config = null;
//
//        try {
//            config = PropertyFile.getProperties(Configs.PROPERTY_FILE);
//            setPoolId(poolId);
//            setMaxActive(config.getInt(pre + Configs.MAX_ACTIVE));
//            setMaxIdle(config.getInt(pre + Configs.MAX_IDLE));
//            setMaxWaitMills(config.getInt(pre + Configs.MAX_WAIT_MILLIS));
//            setTimeout(config.getInt(pre + Configs.TIMEOUT));
//            setTestOnBorrow(config.getBoolean(pre + Configs.TEST_ON_BORROW));
//            setName(config.getString(pre + Configs.NAME));
//            setHost(config.getString(pre + Configs.HOST));
//            setPort(config.getInt(pre + Configs.PORT));
//            setPassword(config.getString(pre+Configs.PASSWORD));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ReadConfigException("打开"+Configs.PROPERTY_FILE+"配置文件失败",e,RedisPoolProperty.class);
//        }
//        return this;
//    }

    /**
     * 从配置文件中加载一个配置对象
     * @param id id
     * @return 配置对象
     */
    public static RedisPoolProperty initByIdFromConfig(String id){
        RedisPoolProperty property = new RedisPoolProperty();
        String pre = id+Configs.SEPARATE;
        List<String> lists = MythReflect.getFieldByClass(RedisPoolProperty.class);
        Map<String ,Object> map = new HashMap<>();
        MythProperties config;
        try {
            config = PropertyFile.getProperties(Configs.PROPERTY_FILE);
            for (String field:lists){
                map.put(field,config.getString(pre+field));
            }
            property = (RedisPoolProperty) MythReflect.setFieldsValue(property,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

    /**
     * 根据传入的对象更新配置文件
     * @param property 原有配置对象
     */
    public static void updateConfigFile(RedisPoolProperty property){
        try {
            Map<String,Object> maps = MythReflect.getFieldsValue(property);
            for(String key:maps.keySet()){
                System.out.println(key+"-------"+maps.get(key));
                if("poolId".equals(key)){
                    continue;
                }
                PropertyFile.update(property.getPoolId()+Configs.SEPARATE+key,maps.get(key).toString());
                System.out.println(property.getPoolId()+Configs.SEPARATE+key);
                System.out.println(maps.get(key).toString());
            }
        } catch (IllegalAccessException | ReadConfigException e) {
            logger.info(ExceptionInfo.UPDATE_CONFIG_KEY_FAILED);
            logger.debug(NoticeInfo.ERROR_INFO,e,RedisPoolProperty.class);
        }

    }

    @Override
    public String toString() {
        return "RedisPoolProperty{" +
                "name='" + name + '\'' +
                ", poolId='" + poolId + '\'' +
                ", password='" + password + '\'' +
                ", maxActive=" + maxActive +
                ", maxIdle=" + maxIdle +
                ", maxWaitMills=" + maxWaitMills +
                ", testOnBorrow=" + testOnBorrow +
                ", port=" + port +
                ", timeout=" + timeout +
                ", host='" + host + '\'' +
                '}';
    }
}
