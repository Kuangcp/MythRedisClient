package redis.manager.compont.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import redis.manager.Main;
import redis.manager.controller.AddKeyController;

import java.io.IOException;

/**
 * 创建键菜单.
 * User: huang
 * Date: 17-6-28
 */
public class CreateKeyMenu extends MyMenuItem {

    private FXMLLoader loader;
    private AddKeyController controller;

    public CreateKeyMenu(TreeView treeView) {
        super("添加键");
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
                    if ("db".equals(flag)) {
                        showPanel(treeView, item);
                    }
                }
        );
    }

    /**
     * 显示输入窗体.
     * @param treeView 树
     * @param item 数据库
     * @return 是否点击确认
     */
    private boolean showPanel(TreeView treeView, TreeItem<Label> item) {
        // 创建 FXMLLoader 对象
        loader = new FXMLLoader();
        // 加载文件
        loader.setLocation(Main.class.getResource("/views/AddKeyLayout.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建对话框
        Stage dialogStage = new Stage();
        dialogStage.setTitle("添加值");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);

        controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setItem(item);
        controller.setTreeView(treeView);

        // 显示对话框, 并等待, 直到用户关闭
        dialogStage.showAndWait();

        return controller.isOkChecked();
    }
}
