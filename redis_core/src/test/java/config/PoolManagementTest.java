package config;

import com.redis.config.PoolManagement;
import com.redis.config.RedisPoolProperty;
import com.redis.config.RedisPools;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-10  上午8:17
 * 注释的都是测试通过的，但是都是依赖于实验测试数据的方法，为了打包通过就全部注释了
 */
public class PoolManagementTest {

    // 直接boolean值的关键字是不能转String的，变量就可以
    @Test
    public void testMap(){
        Map<String,String> map = new HashMap<>();
        boolean flag = true;
        map.put("0",flag+"");
        System.out.println(map.get("0"));
    }

    // 测试创建pool并读取一条数据
    @Test
    public void createPool(){
        // 设置8个属性，id自动生成递增
        RedisPoolProperty property = new RedisPoolProperty();
        property.setHost("127.0.0.1");
        property.setMaxActive(400);
        property.setMaxIdle(100);
        property.setMaxWaitMills(10000);//等待连接超时时间
        property.setTestOnBorrow(false);// 如果设置密码就必须是false
        property.setName("myth");
        property.setPort(6381);
        property.setPassword("myth");
        property.setTimeout(60);//读取超时时间

        RedisPools pool = null;
        try {
           pool = PoolManagement.getRedisPool(PoolManagement.createRedisPool(property));
           System.out.println("连接池实例"+pool);
           Jedis jedis = pool.getJedis();
           jedis.set("name","myth");
           String name = jedis.get("name");
           assert(name!=null);
        } catch (Exception e) {
            e.printStackTrace();
        }// 是否有必要

    }
    // 测试得到单个pool
    @Test
    public void getPool(){
        RedisPools pool = null;
        try {
            pool = PoolManagement.getRedisPool("1022");
            System.out.println("得到连接池："+pool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("size:"+PoolManagement.getPoolMap().size());
        Jedis jedis = null;
        if (pool != null) {
            jedis = pool.getJedis();
        }
        // 得到连接池状态
        Map<String,Integer> map;
        if (pool != null) {
            map = pool.getStatus();
            for(String key:map.keySet()){
                System.out.println(key+"<->"+map.get(key));
            }
            System.out.println("jedis"+jedis);
            jedis.select(1);
        }

//        jedis.set
    }


//    // 测试多个连接池，并将管理类以及所有连接池挂在后台
//    @Test
//    public void getPoolMap() throws Exception {
//        RedisPools redisPool1 = management.getRedisPool("1014");
//        RedisPools redisPool2 = management.getRedisPool("1015");
//        System.out.println("连接池实例数："+management.pools.size());
//        assert(management.pools.size()==2);
//        Jedis jedis1 = redisPool1.getJedis();
//        Jedis jedis2 = redisPool2.getJedis();
//        jedis1.set("name","myth1");
//        jedis2.set("name","myth2");
//        System.out.println(jedis1.get("name"));
//        System.out.println(jedis2.get("name"));
//        while (true){
//            int i=0;
//        }
//
//    }
    // 测试创建多个
    // 虽然看似没有复用连接池，但是地址却是一样的
//    @Test
//    public void crud() throws Exception {
//        RedisPools redisPool1 = management.getRedisPool("1014");
//        RedisPools redisPool2 = management.getRedisPool("1015");
////        System.out.println("连接池实例数："+management.pools.size());
//        assert(management.pools.size()==2);
//        Jedis jedis1 = redisPool1.getJedis();
//        jedis1.select(2);
//        Jedis jedis2 = redisPool2.getJedis();
//        jedis2.select(3);
//        for(int i=0;i<100;i++){
//            jedis1.set(""+i,""+i);
//            jedis2.set(""+i,"name");
//        }
//    }
    // 测试创建销毁多次
    // 销毁要进一步测试，使用Test类不能测试出来
    // 虽然看似是销毁了，因为都是基于集合来判断的，内存有没有就不知道了，很诡异的事情就是，这些内存地址不变的么？？，运行的每一遍都是一样的地址
//    @Test
//    public void destroyPool() throws Exception {
//        RedisPools redisPool1 = management.getRedisPool("1014");
//        System.out.println(redisPool1.getJedisPool());
//        RedisPools redisPool2 = management.getRedisPool("1015");
//        RedisPools redisPool4 = management.getRedisPool("1016");
//        RedisPools redisPool5 = management.getRedisPool("1017");
//        System.out.println("连接池实例数："+management.pools.size());
//        Jedis jedis1 = redisPool1.getJedis();
//        Jedis jedis2 = redisPool2.getJedis();
//        // 状态
//        for(String key:management.pools.keySet()){
//            System.out.println(key);
//        }
//        // 销毁实例
//        management.destroyRedisPool("1014");
//        System.out.println("连接池实例数："+management.pools.size());
//        // 添加实例
//        RedisPools redisPool3 = management.getRedisPool("1014");
//        System.out.println(redisPool3.getJedisPool());
//        System.out.println("连接池实例数："+management.pools.size());
//        // 状态
//        for(String key:management.pools.keySet()){
//            System.out.println(key);
//        }
//    }
    // 清空所有连接池
    @Test
    public void deletePool() throws Exception {
        // 销毁单个
        PoolManagement.deleteRedisPool("1012");
//        PoolManagement.clearAllPools();
    }

    // 测试从已有连接池中直接返回，这里的map又起作用了
    @Test
    public void testUsingId() throws Exception {
        RedisPools redisPool1 = PoolManagement.getRedisPool("1022");
        System.out.println(redisPool1);
        redisPool1.destroyPool();
        redisPool1 = PoolManagement.getRedisPool();
        System.out.println(redisPool1);
    }

    // 单纯的使用，就正常
//    @Test
//    public void wtf(){
//        RedisPoolProperty property = new RedisPoolProperty();
//        property.setHost("127.0.0.1");
//        property.setMaxActive(400);
//        property.setMaxIdle(30);
//        property.setMaxWaitMills(10000);
//        property.setTestOnBorrow(true);
//        property.setName("myth");
////        property.setPassword("myth");
//        property.setPort(6379);
//        property.setTimeout(60);
//
//        RedisPools pool = new RedisPools();
//        pool.setProperty(property);
//        pool.initialPool();
//        Jedis jedis = pool.getJedis();
//        String name = jedis.get("name");
//        System.out.println(name);
//        System.out.println(management.pools.size());
//    }

//    @Test
//    public void testPass(){
//        Jedis jedis = new Jedis("127.0.0.1",6381);
//        jedis.auth("myth");
//        jedis.set("name","");
//        assert jedis.get("name")!=null;
//
//    }

//    由于依赖无法搞定 只好不用Spring commons-logging和slf4j的冲突 gradle无法排除
//    @Test
//    public void  testSpring() throws Exception {
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
//        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
//        RedisPools pools = management.getRedisPool("1022");
//        Jedis jedis = pools.getJedis();
//        System.out.println(jedis.get("name"));
//    }
}
