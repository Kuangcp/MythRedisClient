package redis.manager.compont.menu;

import com.redis.config.PoolManagement;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 连接删除菜单.
 * User: huang
 * Date: 17-6-24
 */
public class DelMenu extends MyMenuItem {

    public DelMenu(TreeView treeView) {
        super("删除");
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
                    if ("link".equals(flag)) {
                        item.getParent().getChildren().remove(item);
                        String id = item.getValue().getAccessibleText();
                        try {
                            poolManagement.deleteRedisPool(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ("key".equals(flag)) {
                        String key = item.getValue().getText().trim();
                        redisKey.deleteKey(key);
                        MultipleSelectionModel msm = treeView.getSelectionModel();
                        int row = treeView.getRow( item.getParent() );
                        msm.select(row);
                    }
                }
        );
    }

}
