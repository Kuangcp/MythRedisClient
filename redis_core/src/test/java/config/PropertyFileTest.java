package config;

import com.redis.config.PropertyFile;
import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-10  下午10:30
 */
public class PropertyFileTest {
    // 展示配置文件的数据
    @Test
    public void testList() throws Exception {
        Map<String,RedisPoolProperty> map = PropertyFile.getAllPoolConfig();
        for(String key:map.keySet()){
            System.out.println(key);
            RedisPoolProperty property = map.get(key);
            Map lists= MythReflect.getFieldsValue(property);
            for(Object keys:lists.keySet()){
                System.out.println(keys.toString()+":"+lists.get(keys));
            }
            System.out.println();
        }
    }

    @Test
    public void testMaxId(){
        try {
            int maxId = PropertyFile.getMaxId();
//            String name = pr.getString("mak");
            System.out.println(maxId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
