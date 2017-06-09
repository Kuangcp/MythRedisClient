import org.junit.Test;

import java.io.File;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午10:24
 */
public class StatusTest {

    // 将配置文件放在用户目录下作为隐藏文件
    @Test
    public void path(){
        String propertyFile = System.getProperty("user.home") + File.separatorChar +".MythRedisClient.properties";
        System.out.println(propertyFile);
    }
}
