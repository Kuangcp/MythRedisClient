package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * 连接设置Controller.
 * User: huang
 * Date: 17-6-22
 */
public class ConnectController {

    private Stage dialogStage;
    private boolean okChecked = false;

    /**
     * 初始化.
     */
    @FXML
    private void initialize() {

    }


    /**
     * 设置dialogStage
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * 点击确定.
     */
    @FXML
    private void handleOk() {

        // TODO 保存数据

        okChecked = true;
        dialogStage.close();
    }

    /**
     * 点击取消.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * 获取是否点击确定.
     * @return
     */
    public boolean isOkChecked() {
        return okChecked;
    }
}
