package redis.manager.compont.menu;

import javafx.scene.control.*;
import redis.manager.compont.alert.MyAlert;
import redis.manager.controller.MainController;
import java.io.IOException;
import java.util.Optional;

/**
 * 连接删除菜单.
 * User: huang
 * Date: 17-6-24
 */
public class DelMenu extends MyMenuItem {

    public DelMenu(TreeView treeView) {
        super("删除");
        setAction(treeView);
    }


    /**
     * 删除连接.
     * @param treeView
     */
    @Override
    protected void setAction(TreeView treeView) {
        super.setOnAction(
                (event) -> {
                    TreeItem<Label> item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    String flag = item.getValue().getAccessibleHelp();
                    if ("link".equals(flag)) {
                        // 删除连接
                        item.getParent().getChildren().remove(item);
                        String id = item.getValue().getAccessibleText();

                        try {
                            if (showPanel("将删除连接")) {
                                poolManagement.deleteRedisPool(id);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ("key".equals(flag)) {
                        // 删除键
                        if (showPanel("将删除键")) {
                            delKey(treeView, item);
                        }
                    }
                }
        );
    }

    /**
     * 删除tab.
     * @param tabId tabID
     */
    private void delTab(String tabId) {
        try {
            for (Tab tab : MainController.tabs) {
                String id = tab.getId();
                if (tabId.equals(id)) {
                    MainController.tabs.remove(tab);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 删除键.
     * @param treeView 树
     * @param item 树的节点
     */
    private void delKey(TreeView<Label> treeView, TreeItem<Label> item) {
        String key = item.getValue().getText();
        String dbId = item.getParent().getValue().getAccessibleText();
        String poolId = item.getParent().getParent().getValue().getAccessibleText();
        String tabId = poolId + "." + dbId + "." + key;
        redisKey.deleteKey(key);
        delTab(tabId);
        MultipleSelectionModel msm = treeView.getSelectionModel();
        int row = treeView.getRow( item.getParent() );
        msm.select(row);
    }

    /**
     * 显示删除对话框.
     * @return 是否确认
     */
    private boolean showPanel(String contentText) {
        boolean ok = false;
        Alert confirmAlert = MyAlert.getInstance(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("提示");
        confirmAlert.setContentText(contentText);
        Optional<ButtonType> opt = confirmAlert.showAndWait();
        ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            ok = true;
        }
        return ok;
    }

}
