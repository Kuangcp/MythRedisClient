package redis.manager.compont.menu;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Describe.
 * User: huang
 * Date: 17-6-26
 */
public class DestroyMenu extends MyMenuItem {

    public DestroyMenu(TreeView treeView) {
        super("销毁");
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
                item.getChildren().remove(0, item.getChildren().size());
                if ("link".equals(flag)) {
                    String id = item.getValue().getAccessibleText();
                    poolManagement.destroyRedisPool(id);
                }
            }
        );
    }
}
