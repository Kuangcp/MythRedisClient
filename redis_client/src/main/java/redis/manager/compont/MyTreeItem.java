package redis.manager.compont;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

/**
 * 我的树节点.
 * User: huang
 * Date: 17-6-23
 */
public class MyTreeItem<T> extends TreeItem<T> {

    public MyTreeItem() {
    }

    public MyTreeItem(T t) {
        super(t);
    }

    /**
     * 设置节点内容.
     * @param t 节点内容
     */
    public void addFirstChild(T t) {
        TreeItem<T> treeItem = new TreeItem<>(t);
        this.getChildren().add(treeItem);
    }

    /**
     * 添加下级节点.
     * @param item 下级节点
     */
    public void addSecondChild(TreeItem<T> item) {
        this.getChildren().add(item);
    }

    /**
     * 设置上下文菜单.
     * @param menu 上下文菜单
     */
    public void setContextMenu(ContextMenu menu) {
        ((Label)this.getValue()).setContextMenu(menu);
    }

    /**
     * 设置所有孩子节点的上下文菜单.
     * @param menu 上下文菜单
     */
    public void setNextContextMenu(ContextMenu menu) {
        ObservableList<TreeItem<T>> items = this.getChildren();
        for (TreeItem item : items) {
            ((Label)item.getValue()).setContextMenu(menu);
        }
    }


}
