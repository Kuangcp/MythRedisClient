package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * 列表添加键值对.
 * User: huang
 * Date: 17-6-26
 */
public class ListAddController {

    private Stage dialogStage;
    private boolean okChecked = false;
    /** 值输入框. */
    @FXML
    private TextField valueText;

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

    /** 确认按钮事件. */
    @FXML
    private void handlerOk() {
        okChecked = true;
        dialogStage.close();
    }

    /** 取消按钮事件. */
    @FXML
    private void handlerCancel() {
        dialogStage.close();
    }

    /**
     * 获取是否点击确定.
     * @return
     */
    public boolean isOkChecked() {
        return okChecked;
    }

    /** 获取输入的值. */
    public String getValue() {
        return valueText.getText();
    }

}
