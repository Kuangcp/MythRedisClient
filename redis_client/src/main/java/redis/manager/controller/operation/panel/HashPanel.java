package redis.manager.controller.operation.panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import redis.manager.controller.HashAddController;
import java.io.IOException;

/**
 * Describe.
 * User: huang
 * Date: 17-6-30
 */
public class HashPanel {
    private HashAddController hashAddController;

    /**
     * 显示连接属性面板.
     * @param isHash 是否为hash
     * @param key 键
     * @return 是否点击确认
     */
    public boolean showPanel(boolean isHash, String key) {
        boolean ok = false;

        // 创建 FXMLLoader 对象
        FXMLLoader loader = new FXMLLoader();
        // 加载文件
        loader.setLocation(this.getClass().getResource("/views/HashAddLayout.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建对话框
        Stage dialogStage = new Stage();
        dialogStage.setTitle("添加键值对");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);


        hashAddController = loader.getController();
        hashAddController.setDialogStage(dialogStage);
        isAddHash(isHash);
        setHashKey(key);

        // 显示对话框, 并等待, 直到用户关闭
        dialogStage.showAndWait();

        ok = hashAddController.isOkChecked();

        return ok;
    }

    /**
     * 设置是否为添加Hash键值对.
     * @param flag true为添加hash键值对, false为添加zset键值对
     */
    private void isAddHash(boolean flag) {
        HashAddController.setFlag(flag);
    }

    public HashAddController getHashAddController() {
        return hashAddController;
    }

    /**
     * 获取输入的键.
     * @return 输入的键
     */
    public String getKey() {
        return hashAddController.getKey();
    }

    /**
     * 获得输入的值.
     * @return 输入的值
     */
    public String getVlaue() {
        return hashAddController.getVlaue();
    }

    private void setHashKey(String key) {
        hashAddController.setAddKey(key);
    }
}
