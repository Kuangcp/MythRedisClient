package redis.manager;

import com.redis.SpringInit;
import com.redis.assemble.hash.RedisHash;
import com.redis.assemble.key.RedisKey;
import com.redis.assemble.list.RedisList;
import com.redis.assemble.set.RedisSet;
import com.redis.common.exception.ReadConfigException;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.manager.controller.ConnectController;
import redis.manager.controller.MainController;
import redis.manager.controller.operation.panel.ConnectPanel;

import java.io.IOException;
import java.util.Map;


/**
 * 应用主程序.
 *
 */

public class Main extends Application {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
    private static PoolManagement management = (PoolManagement)context.getBean("poolManagement");
    private static RedisKey redisKey = (RedisKey) context.getBean("redisKey");
    private static RedisList redisList = (RedisList) context.getBean("redisList");
    private static RedisSet redisSet = (RedisSet) context.getBean("redisSet");
    private static RedisHash redisHash = (RedisHash) context.getBean("redisHash");

    private AnchorPane rootLayout;
    private FXMLLoader rootLoader;
    private Stage primaryStage;
    /** 选择的键. */
    private String selectedKey;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Redis客户端");

        rootLoader = new FXMLLoader();
        rootLoader.setLocation(this.getClass().getResource("/views/MainLayout.fxml"));
        rootLayout = rootLoader.load();

        MainController mainController = rootLoader.getController();
        mainController.setMain(this);

        Scene scene = new Scene(rootLayout);
        this.primaryStage.setScene(scene);
        this.primaryStage.setWidth(1300);
        this.primaryStage.setHeight(700);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();

    }


    /**
     * 显示连接设置窗口.
     * @return 是否点击了确定, true为已点击
     */
    public boolean showConnectPanel() {
        boolean ok = false;
        // 显示面板
        ConnectPanel connectPanel = new ConnectPanel();
        connectPanel.isNewLink(true);
        ok = connectPanel.showConnectPanel();

        if (ok) {
            // 更新连接信息
            MainController mainController = rootLoader.getController();
            Map<String, String> map = ConnectController.getConnectMessage();
            String name = map.get("name");
            String id = map.get("id");
            mainController.updateTree(name, id);
        }


        return ok;
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public void setSelectedKey(String selectedKey) {
        this.selectedKey = selectedKey;
    }

    public static void main(String[] args ) throws ReadConfigException {
        management.setCurrentPoolId(PropertyFile.getMaxId()+"");
        launch(args);

    }

    public static PoolManagement getManagement() {
        return management;
    }

    public static RedisKey getRedisKey() {
        return redisKey;
    }

    public static RedisList getRedisList() {
        return redisList;
    }

    public static RedisSet getRedisSet() {
        return redisSet;
    }

    public static RedisHash getRedisHash() {
        return redisHash;
    }
}
