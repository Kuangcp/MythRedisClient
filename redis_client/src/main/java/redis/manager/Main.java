package redis.manager;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
import com.redis.config.RedisPools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;


/**
 * 应用主程序.
 *
 */
public class Main extends Application {


    private AnchorPane rootLayout;
    private FXMLLoader rootLoader;



    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = primaryStage;
        stage.setTitle("Redis客户端");

        rootLoader = new FXMLLoader();
        rootLoader.setLocation(this.getClass().getResource("/views/MainLayout.fxml"));
        rootLayout = rootLoader.load();

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();

    }




    public static void main( String[] args ) {
        ApplicationContext context;
        PoolManagement management;
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement)context.getBean("poolManagement");
        management.setCurrentPoolId("1001");
        /*RedisPools pools = management.getRedisPool();
        Jedis jedis = pools.getJedis();
        jedis.set("name","huang");
        System.out.println(jedis.get("name"));*/
        launch(args);
    }
}
