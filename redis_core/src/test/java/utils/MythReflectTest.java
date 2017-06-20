package utils;

import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-20  上午8:31
 */
public class MythReflectTest {

    @Test
    public void testFields(){
        RedisPoolProperty property = new RedisPoolProperty();
        property.setHost("120.25.203.47");
        property.setMaxActive(400);
        property.setMaxIdle(100);
        property.setMaxWaitMills(10000);//等待连接超时时间
        property.setTestOnBorrow(false);// 如果设置密码就必须是false
        property.setName("myth");
        property.setPort(6381);
        property.setPassword("myth");
        property.setTimeout(600);//读取超时时间

        try {
            List<String> list = MythReflect.getFieldsByInstance(property);
            for(String l :list){
                System.out.println(l);
            }
            Map<String,Object> map = MythReflect.getFieldsValue(property);
            for(String key:map.keySet()){
                System.out.println(key+"----"+map.get(key));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
