package redis.manager.compont;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import redis.manager.compont.menu.DelMenu;
import redis.manager.compont.menu.CreateKeyMenu;
import redis.manager.compont.menu.DestroyMenu;

/**
 * 左侧树状上下文菜单.
 * User: huang
 * Date: 17-6-23
 */
public class MyContextMenu extends ContextMenu {

    private TreeView treeView;

    public MyContextMenu() {

    }

    public MyContextMenu(TreeView treeView) {
        this.treeView = treeView;
    }

    public MyContextMenu(TreeView treeView, MenuItem... items) {
        super(items);
        this.treeView = treeView;
    }

    /**
     * 设置为一级节点的菜单.
     */
    public void setFirstChileMenu() {
        setEmpty();

        this.getItems().add(new DelMenu(treeView));
        this.getItems().add(new DestroyMenu(treeView));
    }

    /**
     * 设置成二级节点的菜单.
     */
    public void setSecondChildMenu() {
        setEmpty();

        this.getItems().add(new CreateKeyMenu(treeView));

    }

    /**
     * 设置为三级节点的上下文菜单.
     */
    public void setThirdChildMenu() {
        setEmpty();

        this.getItems().add(new DelMenu(treeView));
    }

    /**
     * 将菜单内容设为空.
     */
    public void setEmpty() {
        this.getItems().remove(0, this.getItems().size());
    }

}
