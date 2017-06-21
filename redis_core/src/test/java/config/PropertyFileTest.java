package config;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.PropertyFile;
import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import org.junit.Test;

import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-10  下午10:30
 */
public class PropertyFileTest {
    // 展示配置文件的所有连接池配置的数据
    @Test
    public void testList() throws Exception {
        Map<String,RedisPoolProperty> map = PropertyFile.getAllPoolConfig();
        for(String key:map.keySet()){
            System.out.println(">> "+key+" <<");
            RedisPoolProperty property = map.get(key);
            Map lists= MythReflect.getFieldsValue(property);
            for(Object keys:lists.keySet()){
                System.out.println(keys.toString()+":"+lists.get(keys));
            }
            System.out.println("--------");
        }
    }

    @Test
    public void testMaxId() throws ReadConfigException {
        int maxId = PropertyFile.getMaxId();
        System.out.println(maxId);
    }
    @Test
    public void testSave() throws  ReadConfigException {
        String result = PropertyFile.save("test.key","1223232");
        System.out.println(result);
    }
    @Test
    public void testUpdate() throws  ReadConfigException {
        String result = PropertyFile.update("test.key23","4783902");
        System.out.println(result);
    }
    @Test
    public void testDelete() throws  ReadConfigException {
        PropertyFile.delete("test.key3");
    }
}
