package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import redis.manager.compont.alert.MyAlert;


/**
 * 列表添加键值对.
 * User: huang
 * Date: 17-6-26
 */
public class ListAddController {

    private Stage dialogStage;
    private boolean okChecked = false;
    private String flag = "value";
    /** 值输入框. */
    @FXML
    private TextField valueText;
    /** 信息提示. */
    @FXML
    private Label tipLabel;

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
        switch (flag) {
            case "value" :
                okChecked = true;
                dialogStage.close();
                break;

            case "number" :
                if (isNum()) {
                    okChecked = true;
                    dialogStage.close();
                }
                break;
            default: break;
        }

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

    /**
     * 判断输入的是否为数字.
     * @return true为数字
     */
    private boolean isNum() {
        boolean ok = false;
        String input = valueText.getText();
        try {
            Integer.parseInt(input);
            ok = true;
        } catch (Exception e) {
            Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setContentText("请输入整数");
            alert.showAndWait();
        }
        return ok;
    }

    /** 获取输入的值. */
    public String getValue() {
        return valueText.getText();
    }


    /**
     * 获取输入类型.
     * @return value为字符串, number为数字
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置输入类型.
     * @param flag value为字符串, number为数字
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 设置提示信息.
     */
    public void setTipText(String tipText) {
        tipLabel.setText(tipText);
    }
}
