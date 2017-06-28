package redis.manager.controller;

import com.redis.assemble.key.RedisKey;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import redis.clients.jedis.Jedis;
import redis.manager.Main;
import redis.manager.compont.MyContextMenu;
import redis.manager.compont.MyTreeItem;
import redis.manager.compont.alert.MyAlert;

/**
 * Describe.
 * User: huang
 * Date: 17-6-28
 */
public class AddKeyController {

    private RedisKey redisKey = Main.redisKey;

    private Stage dialogStage;
    private boolean okChecked = false;

    /** 类型选择框. */
    @FXML
    private ComboBox<String> typeBox;
    /** 名称输入框. */
    @FXML
    private TextField nameText;
    /** 散列键. */
    @FXML
    private TextField hashKeyText;
    /** 值输入框. */
    @FXML
    private TextField valueText;
    private TreeItem<Label> item;
    private TreeView<Label> treeView;

    @FXML
    private void initialize() {
        typeBox.setValue("STRING");

        // 监听类型的输入
        typeBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String type = typeBox.getValue().trim();
                    if ("HASH".equals(type) || "hash".equals(type) || "ZSET".equals(type) || "zset".equals(type)) {
                        hashKeyText.setDisable(false);
                        return;
                    }
                    hashKeyText.setDisable(true);
                }
        );
    }

    /** 点击确认. */
    @FXML
    private void handlerOk() {
        boolean ok;
        ok = handler();
        if (ok) {
            if (addKey()) {
                Label thridLabel = new Label(nameText.getText().trim());
                thridLabel.setAccessibleHelp("key");
                MyTreeItem<Label> childThrid = new MyTreeItem<>(thridLabel);
                MyContextMenu thridMenu = new MyContextMenu(treeView);
                thridMenu.setThirdChildMenu();
                childThrid.setContextMenu(thridMenu);
                item.getChildren().add(childThrid);
                okChecked = true;
                dialogStage.close();
            }
        }
    }

    /** 点击取消. */
    @FXML
    private void handlerCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkChecked() {
        return okChecked;
    }

    public void setItem(TreeItem<Label> item) {
        this.item = item;
    }

    public void setTreeView(TreeView<Label> treeView) {
        this.treeView = treeView;
    }

    /**
     * 判断是否填写正确.
     * @return true为填写正确
     */
    private boolean handler() {
        String type = typeBox.getValue().trim();
        Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        if (!"set".equals(type) && !"SET".equals(type) && !"LIST".equals(type) && !"list".equals(type)
                && !"STRING".equals(type) && !"string".equals(type) && !"HASH".equals(type)
                && !"hash".equals(type) && !"ZSET".equals(type) && !"zset".equals(type)) {
            alert.setContentText("类型错误");
            alert.showAndWait();
            return false;
        }
        if ("".equals(nameText.getText())) {
            alert.setContentText("请填写键的名称");
            alert.showAndWait();
            return false;
        }
        if ("".equals(valueText.getText())) {
            alert.setContentText("请填写值");
            alert.showAndWait();
            return false;
        }
        for (String key : MainController.allKeys) {
            if (nameText.getText().trim().equals(key)) {
                alert.setContentText("已存在该键");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    /**
     * 往数据库中添加键.
     * @return 是否成功
     */
    private boolean addKey() {
        Jedis jedis = redisKey.getJedis();
        String type = typeBox.getValue().trim();
        String key = nameText.getText().trim();
        String value = valueText.getText().trim();
        if ("string".equals(type) || "STRING".equals(type)) {
            jedis.set(key, value);
            return true;
        }
        if ("LIST".equals(type) || "list".equals(type)) {
            jedis.lpush(key, value);
            return true;
        }
        if ("SET".equals(type) || "set".equals(type)) {
            jedis.sadd(key, value);
            return true;
        }
        if ("HASH".equals(type) || "hash".equals(type)) {
            String key1 = hashKeyText.getText().trim();
            if ("".equals(key1)) {
                showAlert("请填写第二个键");
                return false;
            }
            jedis.hset(key, key1, value);
            return true;
        }
        if ("ZSET".equals(type) || "zset".equals(type)) {
            String key1 = hashKeyText.getText().trim();
            if ("".equals(key1)) {
                showAlert("请填写第二个键");
                return false;
            }
            Double num;
            try {
                num = Double.parseDouble(value);
            } catch (Exception e) {
                showAlert("值请输入浮点数");
                return false;
            }
            jedis.zadd(key, num, key1);
            return true;
        }
        return false;
    }

    /**
     * 显示错误提示框.
     * @param context 提示内容
     */
    private void showAlert(String context) {
        Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
        alert.setContentText(context);
        alert.showAndWait();
    }

}
