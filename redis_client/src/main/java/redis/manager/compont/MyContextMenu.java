package redis.manager.compont;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * 我的上下文菜单.
 * User: huang
 * Date: 17-6-23
 */
public class MyContextMenu extends ContextMenu {

    public MyContextMenu() {

    }

    public MyContextMenu(MenuItem... items) {
        super(items);
    }

    /**
     * 设置为一级节点的菜单.
     */
    public void firstChileMenu() {
        setEmpty();

        // TODO 添加一级节点的上下文菜单内容
    }

    /**
     * 设置成二级节点的菜单.
     */
    public void secondChildMenu() {
        setEmpty();

        // TODO 添加二级节点的上下文菜单内容
    }

    /**
     * 设置为三级节点的上下文菜单.
     */
    public void thirdChildMenu() {
        setEmpty();

        // TODO 添加三级节点的上下文菜单内容
    }

    /**
     * 将菜单内容设为空.
     */
    public void setEmpty() {
        this.getItems().remove(0, this.getItems().size());
    }

}
