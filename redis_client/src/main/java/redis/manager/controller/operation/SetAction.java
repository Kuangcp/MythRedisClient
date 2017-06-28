package redis.manager.controller.operation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redis.manager.controller.operation.panel.ShowPanel;
import redis.manager.entity.TableEntity;
import java.util.Optional;
import java.util.Set;
import static redis.manager.Main.redisSet;

/**
 * 集合操作.
 * User: huang
 * Date: 17-6-26
 */
public class SetAction extends ShowPanel implements DoAction {

    /** 数据显示表格. */
    private TableView<TableEntity> dataTable;
    /** 数据表格行标. */
    private TableColumn<TableEntity, String> rowColumn;
    /** 表格键. */
    private TableColumn<TableEntity, String> keyColumn;
    /** 表格值. */
    private TableColumn<TableEntity, String> valueColumn;

    public SetAction(TableView dataTable,
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
        Set<String> sets = redisSet.getMembersSet(key);
        int i = 0;
        for (String set : sets) {
            TableEntity value = new TableEntity("" + i, key, set);
            values.add(value);
            i++;
        }
        this.dataTable.setItems(values);
        this.rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty());
        this.keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        this.valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    /**
     * 修改数据.
     *
     * @param key          数据库中的键
     * @param nowSelectRow 当前选择的行
     * @param selected     是否选择值
     */
    @Override
    public void setValueByIndex(String key, int nowSelectRow, boolean selected) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText("");
        alert.setContentText("集合不支持此操作");
        alert.showAndWait();
    }

    /**
     * 添加值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void addValue(String key) {
        boolean ok = showValuePanel();
        if (ok) {
            String value = controller.getValue();
            redisSet.save(key, value);
        }
        controller = null;
    }

    /**
     * 删除值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void delValue(String key) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("提示");
        confirmAlert.setHeaderText("");
        confirmAlert.setContentText("将随机删除一个值");
        Optional<ButtonType> opt = confirmAlert.showAndWait();
        ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            // 确定
            redisSet.pop(key);
        }
    }

}
