package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import redis.manager.compont.MyTab;

/**
 * 主界面controller.
 * User: huang
 * Date: 17-6-7
 */
public class MainController {

    @FXML
    private TabPane tabPane;

    /**
     * 初始化
     */
    private void initialize() {
    }

    /**
     * 添加标签页.
     */
    @FXML
    private void addTab() {
        int num = tabPane.getTabs().size();
        // 创建新标签
        MyTab tab = new MyTab("tab " + num);
        tab.init();

        // 设置tab的关闭按钮
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        // 添加标签页
        tabPane.getTabs().add(tab);
    }

    /**
     * 退出程序.
     */
    @FXML
    private void exit() {
        System.exit(0);
    }

}