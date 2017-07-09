package redis.manager.controller;

import com.redis.assemble.key.RedisKey;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import redis.manager.Main;
import redis.manager.controller.operation.*;
import redis.manager.controller.operation.panel.ShowPanel;
import redis.manager.entity.TableEntity;

/**
 * tab面板的controller.
 * User: huang
 * Date: 17-6-9
 */
public class TabPaneController {

    private RedisKey redisKey = Main.getRedisKey();
    private static  String key;
    private static String poolId;
    private static int dbId;

    /** 数据显示表格. */
    @FXML
    private TableView<TableEntity> dataTable;
    /** 数据表格行标. */
    @FXML
    private TableColumn<TableEntity, String> rowColumn;
    /** 表格键. */
    @FXML
    private TableColumn<TableEntity, String> keyColumn;
    /** 表格值. */
    @FXML
    private TableColumn<TableEntity, String> valueColumn;
    /** 类型显示框. */
    @FXML
    private TextField typeText;
    /** 选中的键显示. */
    @FXML
    private TextField keyShowText;
    /** 选中的值的显示. */
    @FXML
    private TextArea valueShowText;
    /** 左添加按钮. */
    @FXML
    private Button leftAddBtn;
    /** 添加按钮. */
    @FXML
    private Button addRowBtn;
    /** 删除一行数据. */
    @FXML
    private Button delRowBtn;
    /** 左删除按钮. */
    @FXML
    private Button leftDelBtn;
    /** 修改值按钮. */
    @FXML
    private Button setValueBtn;
    /** TTL值显示. */
    @FXML
    private Label ttlValue;
    /** 当前选择的行. */
    private int nowSelectRow;
    /** 是否选择. */
    private boolean selected;
    private DoAction doAction;


    /**
     * 初始化, 装载数据.
     */
    @FXML
    private void initialize() {
        setValue();

        ttlValue.setText(" " + redisKey.getJedis().ttl(key));

        // 监听数据的选择
        dataTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                try {
                    keyShowText.setText(newValue.getKey());
                    valueShowText.setText(newValue.getValue());
                    nowSelectRow = Integer.parseInt(newValue.getRow());
                    selected = true;
                } catch (Exception e) {
                    keyShowText.setText("");
                    valueShowText.setText("");
                    nowSelectRow = 0;
                    selected = false;
                }
            }
        );

    }

    /**
     * 改变名称.
     */
    @FXML
    private void rename() {
        // TODO 重置名称.

    }


    /**
     * 添加一行数据.
     */
    @FXML
    private void addRow() {
        doAction.addValue(key);
        reloadValue();
    }

    /**
     * 删除一行数据.
     */
    @FXML
    private void delRow() {
        doAction.delValue(key, selected);
        reloadValue();
    }

    /**
     * 重新加载数据.
     */
    @FXML
    public void reloadValue() {
        setValue();
    }

    /**
     * 左添加数据.
     */
    @FXML
    private void leftAddValue() {
        doAction.leftAddValue(key);
        reloadValue();
    }

    /**
     * 左删除数据.
     */
    @FXML
    private void leftDelValue() {
        doAction.leftDelValue(key);
        reloadValue();
    }

    /**
     * 设置TTL.
     */
    @FXML
    private void setTTL() {
        ShowPanel showPanel = new ShowPanel();
        boolean ok = showPanel.showValuePanel(true);
        if (ok) {
            int ttl;
            try {
                ttl = Integer.parseInt(showPanel.getValueText());
            } catch (Exception e) {
                String t = showPanel.getValueText();
                t = t.substring(0, t.lastIndexOf("."));
                ttl = Integer.parseInt(t);
            }

            redisKey.expire(key, ttl);
        }
        ttlValue.setText(" " + redisKey.getJedis().ttl(key));
    }

    /**
     * 跳转到指定的数据显示页.
     */
    @FXML
    private void setPage() {
        // TODO 跳转至制定页面
    }

    /**
     * 前一页数据.
     */
    @FXML
    private void previous() {
        // TODO 显示前一页数据.
    }

    /**
     * 后一页数据.
     */
    @FXML
    private void next() {
        // TODO 显示后一页数据.
    }

    /** 设置制定位置的键对应的值. */
    @FXML
    private void setListValue() {
        doAction.setValueByIndex(key, nowSelectRow, selected);
        reloadValue();
    }


    /**
     * 装载面板数据.
     */
    private void setValue() {
        redisKey.management.switchPool(poolId);
        redisKey.setDb(dbId);
        String type = redisKey.type(key);
        typeText.setText(type);
        ttlValue.setText(" " + redisKey.getJedis().ttl(key));
        switch (type) {
            case "list" :
                addRowBtn.setText("Right Add");
                delRowBtn.setText("Right Del");
                doAction = new ListAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            case "set" :
                leftAddBtn.setDisable(true);
                leftDelBtn.setDisable(true);
                setValueBtn.setDisable(true);
                doAction = new SetAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            case "string" :
                leftDelBtn.setDisable(true);
                leftAddBtn.setDisable(true);
                addRowBtn.setDisable(true);
                delRowBtn.setDisable(true);
                doAction = new StringAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            case "hash" :
                leftAddBtn.setDisable(true);
                leftDelBtn.setDisable(true);
                doAction = new HashAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            case "zset" :
                leftAddBtn.setDisable(true);
                leftDelBtn.setDisable(true);
                doAction = new ZsetAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            default:
                doAction = new DoAction() {
                    @Override
                    public void setValue(String key) {}
                    @Override
                    public void setValueByIndex(String key, int nowSelectRow, boolean selected) {}
                    @Override
                    public void addValue(String key) {}
                    @Override
                    public void delValue(String key, boolean selected) {}
                    @Override
                    public void leftAddValue(String key) {}
                    @Override
                    public void leftDelValue(String key) {}
                };
                break;
        }

        doAction.setValue(key);
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        TabPaneController.key = key;
    }

    public static String getPoolId() {
        return poolId;
    }

    public static void setPoolId(String poolId) {
        TabPaneController.poolId = poolId;
    }

    public static int getDbId() {
        return dbId;
    }

    public static void setDbId(int dbId) {
        TabPaneController.dbId = dbId;
    }
}
