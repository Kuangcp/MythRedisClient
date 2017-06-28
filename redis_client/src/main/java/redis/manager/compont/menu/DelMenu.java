package redis.manager.compont.menu;

import com.redis.config.PoolManagement;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import redis.manager.controller.MainController;

import java.io.IOException;

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
                        item.getParent().getChildren().remove(item);
                        String id = item.getValue().getAccessibleText();
                        try {
                            poolManagement.deleteRedisPool(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ("key".equals(flag)) {
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

}
