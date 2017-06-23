package redis.manager.compont;

import javafx.scene.control.ContextMenu;
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



}
