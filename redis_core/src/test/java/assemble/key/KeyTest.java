package assemble.key;

import com.redis.assemble.key.RedisKey;
import com.redis.config.PoolManagement;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午10:35
 */
public class KeyTest {
    @Before
    public void init(){
        PoolManagement.initPool("1022");
    }

    @Test
    public void testDump(){
        RedisKey redisKey = new RedisKey();
        byte[]s = redisKey.dump("name");
        System.out.println(s.length);
        for(byte sr:s){
            System.out.println(s.toString());
        }
    }
}
