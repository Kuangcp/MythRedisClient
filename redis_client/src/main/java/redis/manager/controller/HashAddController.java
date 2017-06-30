package redis.manager.controller;

import com.redis.assemble.hash.RedisHash;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import redis.manager.Main;
import redis.manager.compont.alert.MyAlert;


/**
 * Describe.
 * User: huang
 * Date: 17-6-30
 */
public class HashAddController {

    private RedisHash redisHash = Main.getRedisHash();
    private boolean okChecked = false;
    private Stage dialogStage;
    private String addKey;

    /** 标志是否是Hash输入. */
    private static boolean flag = true;

    /** 键输入框. */
    @FXML
    private TextField keyText;
    /** 值输入框. */
    @FXML
    private TextField valueText;

    @FXML
    private void initialize() {

    }

    /**
     * 确认.
     */
    @FXML
    private void handlerOk() {
        System.out.println(flag);
        // Hash
        if (flag) {
            long result = redisHash.saveWhenNotExist(addKey, getKey(), getVlaue());
            if (result == 0) {
                Alert alert = MyAlert.getInstance(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setContentText("该键已存在");
                alert.showAndWait();
                return;
            }
            okChecked = true;
            dialogStage.close();
        }
    }

    /**
     * 取消.
     */
    @FXML
    private void handlerCancel() {
        dialogStage.close();
    }

    /**
     * 设置dialogStage
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * 获取是否点击确定.
     * @return
     */
    public boolean isOkChecked() {
        return okChecked;
    }

    public static boolean isFlag() {
        return flag;
    }

    /**
     * 设置是否为新建连接.
     * @param flag true为新建连接
     */
    public static void setFlag(boolean flag) {
        HashAddController.flag = flag;
    }

    /**
     * 获取输入的键.
     * @return 输入的键
     */
    public String getKey() {
        return keyText.getText().trim();
    }

    /**
     * 获得输入的值.
     * @return 输入的值
     */
    public String getVlaue() {
        return valueText.getText().trim();
    }

    public void setAddKey(String addKey) {
        this.addKey = addKey;
    }
}
