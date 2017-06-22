package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import redis.manager.Main;
import redis.manager.compont.MyTab;

/**
 * 主界面controller.
 * User: huang
 * Date: 17-6-7
 */
public class MainController {

    @FXML
    private TabPane tabPane;

    private Main main;

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
     * 创建新连接.
     */
    @FXML
    private void newConnect() {
        boolean ok = false;
        ok = main.showConnectPanel();
    }

    /**
     * 退出程序.
     */
    @FXML
    private void exit() {
        System.exit(0);
    }

    public void setMain(Main main) {
        this.main = main;
    }

}