package redis.manager.controller;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.Configs;
import com.redis.config.PropertyFile;
import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import redis.manager.Main;
import redis.manager.compont.MyTab;
import redis.manager.entity.TreeEntity;

import java.util.Map;

/**
 * 主界面controller.
 * User: huang
 * Date: 17-6-7
 */
public class MainController {


    @FXML
    private TabPane tabPane;
    /** 左侧树. */
    @FXML
    private TreeView<Label> treeView;

    private Main main;

    /**
     * 初始化
     */
    @FXML
    private void initialize() {
        try {
            setTreeView();
        } catch (ReadConfigException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    /**
     * 设置面板左侧树状图.
     */
    private void setTreeView() throws ReadConfigException, IllegalAccessException {

        TreeItem<Label> root = null;
        root = new TreeItem<>(new Label("连接"));
        root.setExpanded(true);

        Map<String,RedisPoolProperty> map = PropertyFile.getAllPoolConfig();
        for(String key:map.keySet()){
            RedisPoolProperty property = map.get(key);
            Map lists= MythReflect.getFieldsValue(property);

            TreeItem<Label> child = new TreeItem<>(new Label((String) lists.get(Configs.NAME)));
            //child.setExpanded(true);
            child.getChildren().add(new TreeItem<>(new Label((String) lists.get(Configs.NAME))));

            // 创建子节点
            root.getChildren().add(child);
        }

        treeView.setShowRoot(true);
        treeView.setRoot(root);
    }

    public void setMain(Main main) {
        this.main = main;
    }


}