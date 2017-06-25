package redis.manager.compont.menu;

import com.redis.config.PoolManagement;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;

/**
 * 上下文菜单的抽象类.
 * User: huang
 * Date: 17-6-25
 */
public abstract class MyMenuItem extends MenuItem {

    protected PoolManagement poolManagement;

    protected MyMenuItem(String label) {
        super(label);
    }

    /**
     * 点击处理方法.
     * @param treeView treeView
     */
    protected abstract void setAction(TreeView treeView);

    public void setPoolManagement(PoolManagement poolManagement) {
        this.poolManagement = poolManagement;
    }
}
