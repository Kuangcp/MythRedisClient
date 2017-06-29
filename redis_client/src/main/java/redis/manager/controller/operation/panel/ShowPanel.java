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

    /** 显示值输入窗口.
     *  @param isNum 是否选择整数类型, true为整数, false为字符串
     */
    public boolean showValuePanel(boolean isNum) {
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
        if (isNum) {
            controller.setTipText("输入0便立即删除\n-1 则永久存活 \n-2 则不存在");
            controller.setFlag("number");
        }

        // 显示对话框, 并等待, 直到用户关闭
        dialogStage.showAndWait();

        return controller.isOkChecked();
    }

    /**
     * 获取输入内容.
     * @return 输入的内容
     */
    public String getValueText() {
        return controller.getValue();
    }

}
