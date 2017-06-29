package redis.manager.compont.alert;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

/**
 * 使用单例获取提示框.
 * User: huang
 * Date: 17-6-27
 */
public final class MyAlert {

    private static Alert warningAlert;

    private MyAlert() {

    }

    /**
     * 获取提示框实例.
     * @param alertType 类型
     * @return 提示框实例
     */
    public synchronized static Alert getInstance(@NamedArg("alertType") Alert.AlertType alertType) {
        if (warningAlert == null) {
            synchronized (MyAlert.class) {
                if (warningAlert == null) {
                    warningAlert = new Alert(alertType);
                    warningAlert.setHeaderText("");
                }
            }
            return warningAlert;
        }
        synchronized (MyAlert.class) {
            warningAlert.setAlertType(alertType);
            warningAlert.setHeaderText("");
            return warningAlert;
        }
    }

}
