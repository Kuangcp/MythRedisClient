package redis.manager.compont;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import redis.manager.Main;
import redis.manager.controller.TabPaneController;

import java.io.IOException;

/**
 * 我的tab.
 * User: huang
 * Date: 17-6-9
 */
public class MyTab extends Tab {

    public MyTab(String title) {
        super(title);
    }

    /**
     * 初始化设置, 添加一个特定的Pane.
     */
    public void init() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/TabPaneLayout.fxml"));
        try {
            AnchorPane tabPane = loader.load();
            // 添加新标签页
            this.setContent(tabPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
