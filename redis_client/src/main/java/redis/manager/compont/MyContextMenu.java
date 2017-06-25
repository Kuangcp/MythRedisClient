package redis.manager.compont;

import com.redis.config.PoolManagement;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import redis.manager.compont.menu.ConnectDelMenu;
import redis.manager.compont.menu.MyMenuItem;

/**
 * 左侧树状上下文菜单.
 * User: huang
 * Date: 17-6-23
 */
public class MyContextMenu extends ContextMenu {

    private TreeView treeView;
    private PoolManagement poolManagement;

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

        // TODO 添加一级节点的上下文菜单内容
        this.getItems().add(new ConnectDelMenu(treeView));
    }

    /**
     * 设置成二级节点的菜单.
     */
    public void setSecondChildMenu() {
        setEmpty();

        // TODO 添加二级节点的上下文菜单内容

    }

    /**
     * 设置为三级节点的上下文菜单.
     */
    public void setThirdChildMenu() {
        setEmpty();

        // TODO 添加三级节点的上下文菜单内容
        this.getItems().add(new ConnectDelMenu(treeView));
    }

    /**
     * 将菜单内容设为空.
     */
    public void setEmpty() {
        this.getItems().remove(0, this.getItems().size());
    }

    /**
     * 设置子节点的PoolManager.
     * @param poolManagement PoolManagement
     */
    public void setPoolManagement(PoolManagement poolManagement) {
        this.poolManagement = poolManagement;
        for (MenuItem item : this.getItems()) {
            if (item instanceof MyMenuItem) {
                ((MyMenuItem)item).setPoolManagement(poolManagement);
            }
        }
    }
}
