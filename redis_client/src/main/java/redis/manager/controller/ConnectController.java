package redis.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 连接设置Controller.
 * User: huang
 * Date: 17-6-22
 */
public class ConnectController {

    private Stage dialogStage;
    private boolean okChecked = false;
    /** 最大连接数输入框. */
    @FXML
    private TextField maxActiveText;
    /** 最大闲置数输入框. */
    @FXML
    private TextField maxIdleText;
    /** 连接等待的最长时间. */
    @FXML
    private TextField maxWaitMillsText;
    /** 端口输入框. */
    @FXML
    private TextField portText;
    /** 服务器输入框. */
    @FXML
    private TextField hostText;
    /** 连接名称输入框. */
    @FXML
    private TextField nameText;
    /** 密码输入框. */
    @FXML
    private PasswordField passwordText;
    /** 密码确认框. */
    @FXML
    private PasswordField repasswordText;

    /** 最大连接数输入提醒. */
    @FXML
    private Label maxActiveLabel;
    /** 最大闲置数输入提醒. */
    @FXML
    private Label maxIdleLabel;
    /** 连接等待的最长时间输入提醒. */
    @FXML
    private Label maxWaitMillsLabel;
    /** 端口输入提醒. */
    @FXML
    private Label portLabel;
    /** 确认密码输入提醒. */
    @FXML
    private Label passwordLabel;

    /** 最大连接数输入是否为数字. */
    private final boolean[] isNumActive = {false};
    /** 最大闲置数输入是否为数字. */
    private final boolean[] isNumIdle = {false};
    /** 连接等待的最长时间输入是否为数字. */
    private final boolean[] isNumWait = {false};
    /** 端口输入是否为数字. */
    private final boolean[] isNumPort = {false};
    /** 两次密码是否输入一致. */
    private final boolean[] isEqPassword = {false};

    /**
     * 初始化.
     */
    @FXML
    private void initialize() {

        // 监听最大连接数输入
        textChangeListener(maxActiveText, maxActiveLabel, isNumActive);
        // 监听最大闲置数输入
        textChangeListener(maxIdleText, maxIdleLabel, isNumIdle);
        // 监听连接等待的最长时间输入
        textChangeListener(maxWaitMillsText, maxWaitMillsLabel, isNumWait);
        // 监听端口输入
        textChangeListener(portText, portLabel, isNumPort);
        // 监听密码确认
        confirmPassword(passwordText, repasswordText, passwordLabel, isEqPassword);


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

        // 是否符合输入规则
        if (isNumActive[0] && isNumIdle[0] && isNumWait[0] && isNumPort[0] && isEqPassword[0]) {
            okChecked = true;
            dialogStage.close();
        }

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

    /**
     * 文本框输入数字监听.
     * @param field 文本框
     * @param label 信息提示
     */
    private void textChangeListener(TextField field, Label label, boolean[] ok) {
        field.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (!field.getText().matches("[0-9]*")) {
                        label.setText("请输入数字");
                        label.setTextFill(Color.rgb(255, 0, 0));
                        ok[0] = false;
                        return;
                    }

                    label.setText("");
                    ok[0] = true;

                }
        );
    }

    /**
     * 确认两次密码是否一致.
     * @param password 密码输入框
     * @param rePassword 确认密码输入框
     */
    private void confirmPassword(PasswordField password, PasswordField rePassword, Label label, boolean[] ok) {
        rePassword.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!rePassword.getText().equals(password.getText())) {
                        label.setText("两次密码不一致");
                        label.setTextFill(Color.rgb(255, 0, 0));
                        ok[0] = false;
                        return;
                    }

                    label.setText("");
                    ok[0] = true;
                }
        );
    }
}
