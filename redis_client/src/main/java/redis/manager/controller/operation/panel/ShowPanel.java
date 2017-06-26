package redis.manager.controller.operation.panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import redis.manager.controller.ListAddController;

import java.io.IOException;

/**
 * 显示面板.
 * User: huang
 * Date: 17-6-26
 */
public class ShowPanel {
    protected ListAddController controller;

    /** 显示值输入窗口. */
    protected boolean showValuePanel() {
        // 创建 FXMLLoader 对象
        FXMLLoader loader = new FXMLLoader();
        // 加载文件
        loader.setLocation(this.getClass().getResource("/views/ListAddLayout.fxml"));
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

        // 显示对话框, 并等待, 直到用户关闭
        dialogStage.showAndWait();

        return controller.isOkChecked();
    }

}
