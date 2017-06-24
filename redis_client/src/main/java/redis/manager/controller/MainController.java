package redis.manager.controller;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.*;
import com.redis.utils.MythReflect;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
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
@Component
public class MainController {

    private PoolManagement poolManagement;

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 监听选择的节点
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    MyTreeItem<Label> selectedItem = (MyTreeItem<Label>) treeView.getSelectionModel().getSelectedItem();
                    String flag = selectedItem.getValue().getAccessibleHelp();
                    // 展示当前数据库中所有的键
                    if ("db".equals(flag)) {
                        System.out.println("查询键");
                        return;
                    }
                    // 显示当前连接中的所有数据库
                    if ("link".equals(flag)) {
                        try {
                            createSecondNode(selectedItem);
                        } catch (Exception e) {
                            System.out.println("打开连接失败::::");
                        }
                        return;
                    }
                }
        );
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
            Label firstLabel = new Label((String) lists.get(Configs.NAME));
            firstLabel.setAccessibleHelp("link");
            // 将连接的ID保存
            firstLabel.setAccessibleText((String) lists.get(Configs.POOL_ID));
            MyTreeItem<Label> childOne = new MyTreeItem<>(firstLabel);
            MyContextMenu firstMenu = new MyContextMenu(treeView);
            firstMenu.setFirstChileMenu();
            childOne.setContextMenu(firstMenu);
            // 添加一级子节点
            root.addSecondChild(childOne);
        }

        treeView.setShowRoot(true);
        treeView.setRoot(root);
    }


    /**
     * 创建二级节点, 数据库显示节点
     * @param treeItem 连接节点
     */
    private void createSecondNode(MyTreeItem treeItem) {

        String poolId = ((Label)treeItem.getValue()).getAccessibleText();
        poolManagement.switchPool(poolId);
        System.out.println("Main : ::::::::::::::::::::::" + poolManagement);

        treeItem.setContextMenuPoolManager(poolManagement);

        RedisPools redisPools = poolManagement.getRedisPool();
        int num = redisPools.getDatabaseNum();
        for (int i = 0; i < num; i++) {
            Label secondLable = new Label("数据库 " + (i+1));
            // 标志为数据库节点
            secondLable.setAccessibleHelp("db");
            MyTreeItem<Label> childTwo = new MyTreeItem<>(secondLable);
            MyContextMenu secondMenu = new MyContextMenu(treeView);
            secondMenu.setSecondChildMenu();
            childTwo.setContextMenu(secondMenu);
            // 添加二级子节点
            treeItem.addSecondChild(childTwo);
        }
    }

    /**
     * 更新连接树显示.
     * @param name 连接名称
     * @param id 连接编号
     */
    public void updateTree(String name, String id) {
        Label firstLabel = new Label(name);
        firstLabel.setAccessibleHelp("link");
        firstLabel.setAccessibleText(id);
        MyTreeItem<Label> childOne = new MyTreeItem<>(firstLabel);
        MyContextMenu firstMenu = new MyContextMenu(treeView);
        firstMenu.setFirstChileMenu();
        childOne.setContextMenu(firstMenu);

        treeView.getRoot().getChildren().add(childOne);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public PoolManagement getPoolManagement() {
        return poolManagement;
    }

    public void setPoolManagement(PoolManagement poolManagement) {
        this.poolManagement = poolManagement;
    }
}