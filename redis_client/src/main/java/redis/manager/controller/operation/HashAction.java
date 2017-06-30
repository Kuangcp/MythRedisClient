package redis.manager.controller.operation;

import com.redis.assemble.hash.RedisHash;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redis.manager.Main;
import redis.manager.compont.alert.MyAlert;
import redis.manager.controller.operation.panel.HashPanel;
import redis.manager.controller.operation.panel.ShowPanel;
import redis.manager.entity.TableEntity;
import java.util.Map;

/**
 * hash操作.
 * User: huang
 * Date: 17-6-30
 */
public class HashAction extends HashPanel implements DoAction {

    private RedisHash redisHash = Main.getRedisHash();

    /** 数据显示表格. */
    private TableView<TableEntity> dataTable;
    /** 数据表格行标. */
    private TableColumn<TableEntity, String> rowColumn;
    /** 表格键. */
    private TableColumn<TableEntity, String> keyColumn;
    /** 表格值. */
    private TableColumn<TableEntity, String> valueColumn;

    public HashAction(TableView dataTable,
                      TableColumn rowColumn, TableColumn keyColumn, TableColumn valueColumn) {
        this.dataTable = dataTable;
        this.rowColumn = rowColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
    }

    /**
     * 装载面板数据.
     *
     * @param key 数据库中的键
     */
    @Override
    public void setValue(String key) {
        ObservableList<TableEntity> values = FXCollections.observableArrayList();
        Map<String, String> maps = redisHash.getAllMap(key);
        int i = 0;
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            TableEntity value = new TableEntity("" + i, entry.getKey().trim(), entry.getValue());
            values.add(value);
            i++;
        }
        this.dataTable.setItems(values);
        this.rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty());
        this.keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        this.valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    /**
     * 修改值.
     *
     * @param key          数据库中的键
     * @param nowSelectRow 当前选择的值
     * @param selected     是否选择值
     */
    @Override
    public void setValueByIndex(String key, int nowSelectRow, boolean selected) {
        if (selected) {
            ShowPanel showPanel = new ShowPanel();
            boolean ok = showPanel.showValuePanel(false);
            if (ok) {
                String childKey = dataTable.getSelectionModel().getSelectedItem().getKey();
                String value = showPanel.getValueText();
                redisHash.save(key, childKey, value);
            }
        } else {
            Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setContentText("请选择一个键");
            alert.showAndWait();
        }
    }

    /**
     * 添加值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void addValue(String key) {
        showPanel(true, key);
    }

    /**
     * 删除值.
     *
     * @param key 数据库中的键
     * @param selected 是否已选择一个键.
     */
    @Override
    public void delValue(String key, boolean selected) {
        if (selected) {
            String childKey = dataTable.getSelectionModel().getSelectedItem().getKey();
            redisHash.remove(key, childKey);
        } else {
            Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setContentText("请选择一个键");
            alert.showAndWait();
        }
    }

    /**
     * 左添加值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void leftAddValue(String key) {

    }

    /**
     * 左删除值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void leftDelValue(String key) {

    }
}
