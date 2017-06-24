package redis.manager.compont.menu;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * 连接删除菜单.
 * User: huang
 * Date: 17-6-24
 */
public class ConnectDelMenu extends MenuItem {

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
                    TreeItem item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    item.getParent().getChildren().remove(item);
                }
        );
    }


}
