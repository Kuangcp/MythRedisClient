package redis.manager.compont.menu;

import com.redis.config.PoolManagement;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 连接删除菜单.
 * User: huang
 * Date: 17-6-24
 */
public class ConnectDelMenu extends MenuItem {

    private PoolManagement poolManagement;

    public ConnectDelMenu(TreeView treeView) {
        super("删除");
        setAction(treeView);
    }


    /**
     * 删除连接.
     * @param treeView
     */
    private void setAction(TreeView treeView) {
        super.setOnAction(
                (event) -> {
                    TreeItem<Label> item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    item.getParent().getChildren().remove(item);
                    String flag = item.getValue().getAccessibleHelp();
                    if ("link".equals(flag)) {
                        String id = item.getValue().getAccessibleText();
                        try {
                            poolManagement.deleteRedisPool(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public void setPoolManagement(PoolManagement poolManagement) {
        this.poolManagement = poolManagement;
    }
}
