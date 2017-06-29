package redis.manager.compont.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import redis.manager.controller.AddKeyController;
import redis.manager.controller.operation.panel.ConnectPanel;

/**
 * 设置属性菜单.
 * User: huang
 * Date: 17-6-29
 */
public class AttributeSetMenu extends MyMenuItem {

    private FXMLLoader loader;
    private AddKeyController controller;

    public AttributeSetMenu(TreeView treeView) {
        super("设置属性");
        setAction(treeView);
    }

    /**
     * 点击处理方法.
     *
     * @param treeView treeView
     */
    @Override
    protected void setAction(TreeView treeView) {
        super.setOnAction(
            (event) -> {
                TreeItem<Label> item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                String flag = item.getValue().getAccessibleHelp();
                if ("link".equals(flag)) {
                    ConnectPanel connectPanel = new ConnectPanel();
                    connectPanel.isNewLink(false);
                    connectPanel.showConnectPanel();
                }
            }
        );
    }
}
