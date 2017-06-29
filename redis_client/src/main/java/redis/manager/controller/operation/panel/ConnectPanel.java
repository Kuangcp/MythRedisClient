package redis.manager.controller.operation.panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import redis.manager.controller.ConnectController;
import java.io.IOException;

/**
 * 连接属性面板.
 * User: huang
 * Date: 17-6-29
 */
public class ConnectPanel {
    private ConnectController connectController;

    /**
     * 显示连接属性面板.
     * @return 是否点击确认
     */
    public boolean showConnectPanel() {
        boolean ok = false;

        // 创建 FXMLLoader 对象
        FXMLLoader loader = new FXMLLoader();
        // 加载文件
        loader.setLocation(this.getClass().getResource("/views/ConnectLayout.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建对话框
        Stage dialogStage = new Stage();
        dialogStage.setTitle("创建连接");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);


        connectController = loader.getController();
        connectController.setDialogStage(dialogStage);

        // 显示对话框, 并等待, 直到用户关闭
        dialogStage.showAndWait();

        ok = connectController.isOkChecked();

        return ok;
    }

    /**
     * 设置是否为新建连接.
     * @param flag true为新建连接, false为修改属性
     */
    public void isNewLink(boolean flag) {
        ConnectController.setFlag(flag);
    }
}
