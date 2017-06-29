package redis.manager.controller.operation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redis.manager.controller.operation.panel.ShowPanel;
import redis.manager.entity.TableEntity;

/**
 * String类型的操作.
 * User: huang
 * Date: 17-6-27
 */
public class StringAction extends ShowPanel implements DoAction {

    /** 数据显示表格. */
    private TableView<TableEntity> dataTable;
    /** 数据表格行标. */
    private TableColumn<TableEntity, String> rowColumn;
    /** 表格键. */
    private TableColumn<TableEntity, String> keyColumn;
    /** 表格值. */
    private TableColumn<TableEntity, String> valueColumn;

    public StringAction(TableView dataTable,
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
        String value = REDIS_KEY.getJedis().get(key);
        TableEntity tableValue = new TableEntity("" + 1, key, value);
        values.add(tableValue);
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
        boolean ok = showValuePanel();
        if (ok) {
            String value = controller.getValue();
            REDIS_KEY.getJedis().set(key, value);
            controller = null;
        }
    }
    /**
     * 添加值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void addValue(String key) {

    }

    /**
     * 删除值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void delValue(String key) {

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
