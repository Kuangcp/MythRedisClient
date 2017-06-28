package redis.manager.compont.menu;

import com.redis.assemble.key.RedisKey;
import com.redis.config.PoolManagement;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import redis.manager.Main;

/**
 * 上下文菜单的抽象类, 所有上下文菜单的基类.
 * User: huang
 * Date: 17-6-25
 */
public abstract class MyMenuItem extends MenuItem {

    protected PoolManagement poolManagement = Main.management;
    protected RedisKey redisKey = Main.redisKey;

    protected MyMenuItem(String label) {
        super(label);
    }

    /**
     * 点击处理方法.
     * @param treeView treeView
     */
    protected abstract void setAction(TreeView treeView);

}
