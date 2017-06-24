package redis.manager.controller;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.Configs;
import com.redis.config.PropertyFile;
import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import redis.manager.Main;
import redis.manager.compont.MyContextMenu;
import redis.manager.compont.MyTab;
import redis.manager.compont.MyTreeItem;

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

        MyTreeItem<Label> root = new MyTreeItem<>(new Label("连接"));
        // 默认展开
        root.setExpanded(true);

        Map<String,RedisPoolProperty> map = PropertyFile.getAllPoolConfig();
        for(String key:map.keySet()){
            RedisPoolProperty property = map.get(key);
            Map lists= MythReflect.getFieldsValue(property);

            // 创建一级子节点
            MyTreeItem<Label> childOne = new MyTreeItem<>(new Label((String) lists.get(Configs.NAME)));
            MyContextMenu firstMenu = new MyContextMenu(treeView);
            firstMenu.setFirstChileMenu();
            childOne.setContextMenu(firstMenu);
            // 创建二级子节点
            MyTreeItem<Label> childTwo = new MyTreeItem<>(new Label("二级节点"));
            MyContextMenu secondMenu = new MyContextMenu(treeView);
            secondMenu.setSecondChildMenu();
            childTwo.setContextMenu(secondMenu);
            // 添加三级子节点
            childTwo.addFirstChild(new Label("三级节点"));
            MyContextMenu thridMenu = new MyContextMenu(treeView);
            thridMenu.setThirdChildMenu();
            childTwo.setNextContextMenu(thridMenu);
            // 添加二级子节点
            childOne.addSecondChild(childTwo);
            // 添加一级子节点
            root.addSecondChild(childOne);
        }


        treeView.setShowRoot(true);
        treeView.setRoot(root);
    }

    public void setMain(Main main) {
        this.main = main;
    }


}