package redis.manager.controller;

import com.redis.assemble.key.RedisKey;
import com.redis.common.exception.ClientExceptionInfo;
import com.redis.common.exception.NoticeInfo;
import com.redis.common.exception.ReadConfigException;
import com.redis.config.Configs;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
import com.redis.config.RedisPoolProperty;
import com.redis.utils.MythReflect;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.manager.Main;
import redis.manager.compont.MyContextMenu;
import redis.manager.compont.MyTab;
import redis.manager.compont.MyTreeItem;

import java.util.Map;
import java.util.Set;

/**
 * 主界面controller.
 * User: huang
 * Date: 17-6-7
 */
@Component
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private PoolManagement poolManagement = Main.management;

    private RedisKey redisKey = Main.redisKey;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("连接显示错误");
        }

        // 监听选择的节点
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    MyTreeItem<Label> selectedItem =
                            (MyTreeItem<Label>) treeView.getSelectionModel().getSelectedItem();
                    String flag = selectedItem.getValue().getAccessibleHelp();
                    if (flag == null) {
                        flag = "";
                    }
                    switch (flag) {
                        case "db" :
                            // 展示当前数据库中所有的键
                            createThridNode(selectedItem);
                            break;

                        case "link" :
                            // 显示当前连接中的所有数据库
                            try {
                                createSecondNode(selectedItem);
                            } catch (Exception e) {
                                logger.error("打开连接失败");
                                logger.debug(NoticeInfo.ERROR_INFO,e);
                            }
                            break;

                        case "key" :
                            // 显示数据面板
                            showTab(selectedItem);
                            break;

                        default:
                            break;
                    }
                }
        );
    }

    /**
     * 添加标签页.
     */
    @FXML
    private void addTab(String tabId, String name) {
        // 创建新标签
        MyTab tab = new MyTab(name);
        tab.setId(tabId);

        tab.init();
        // 设置tab的关闭按钮
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        // 添加标签页, 放在第一个
        tabPane.getTabs().add(0, tab);
        // 设置第一个被选择
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);
    }

    /**
     * 创建新连接.
     */
    @FXML
    private void newConnect() {
        main.showConnectPanel();
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
//            System.out.println(firstLabel.getAccessibleHelp());
            // 将连接的ID保存
            firstLabel.setAccessibleText((String) lists.get(Configs.POOL_ID));
//            System.out.println((String) lists.get(Configs.POOL_ID));
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
     * 创建二级节点, 数据库显示节点.
     * @param treeItem 连接节点
     */
    private void createSecondNode(MyTreeItem<Label> treeItem) {

        String poolId = treeItem.getValue().getAccessibleText();
        poolManagement.switchPool(poolId);

        treeItem.setContextMenuPoolManager(poolManagement);
        int num=0;
        try {
            num= poolManagement.getRedisPool().getDatabaseNum();
        }catch (Exception e){
            logger.error(ClientExceptionInfo.CONNECTION_UNUSABLE);
        }
        // 清除所有的孩子节点
        int childNum = treeItem.getChildren().size();
        treeItem.getChildren().remove(0, childNum);
        for (int i = 0; i < num; i++) {
            Label secondLable = new Label("数据库 " + i);
            // 标志为数据库节点
            secondLable.setAccessibleHelp("db");
            secondLable.setAccessibleText("" + i);
            MyTreeItem<Label> childTwo = new MyTreeItem<>(secondLable);
            MyContextMenu secondMenu = new MyContextMenu(treeView);
            secondMenu.setSecondChildMenu();
            childTwo.setContextMenu(secondMenu);
            // 添加二级子节点
            treeItem.addSecondChild(childTwo);
        }
    }

    /**
     * 创建三级节点, 键的显示.
     * @param treeItem 数据库节点
     */
    private void createThridNode(MyTreeItem<Label> treeItem) {
        String dbId = treeItem.getValue().getAccessibleText();
        try{
            int id = Integer.parseInt(dbId);
            Set<String> keys = redisKey.listAllKeys(id);
            // 清楚所有的子节点
            int childSize = treeItem.getChildren().size();
            treeItem.getChildren().remove(0, childSize);
            for (String key : keys) {
//                logger.debug(" 每个键 ：  "+key);
                Label thridLabel = new Label(key);
                thridLabel.setAccessibleHelp("key");
                MyTreeItem<Label> childThrid = new MyTreeItem<>(thridLabel);
                MyContextMenu thridMenu = new MyContextMenu(treeView);
                thridMenu.setThirdChildMenu();
                childThrid.setContextMenu(thridMenu);
                treeItem.addSecondChild(childThrid);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("数据库定位出错");
            alert.show();
        }
    }

    /**
     * 显示数据显示面板.
     * @param treeItem 数据库的键节点
     */
    private void showTab(MyTreeItem<Label> treeItem) {
        boolean ok = true;
        String name = treeItem.getValue().getText();
        String dbId = treeItem.getParent().getValue().getAccessibleText();
        String poolId = treeItem.getParent().getParent().getValue().getAccessibleText();
        String tabId = poolId + dbId + name;
        for (Tab tab : tabPane.getTabs()) {
            if (tabId.equals(tab.getId())) {
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(tab);
                ok = false;
            }
        }
        if (ok) {
            addTab(tabId, name);
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

}