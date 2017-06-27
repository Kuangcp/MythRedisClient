package redis.manager.controller.operation;

import com.redis.common.exception.ActionErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import redis.manager.compont.alert.MyAlert;
import redis.manager.controller.operation.panel.ShowPanel;
import redis.manager.entity.TableEntity;
import java.util.List;
import java.util.Optional;
import static redis.manager.Main.redisList;

/**
 * 列表操作.
 * User: huang
 * Date: 17-6-26
 */
public class ListAction extends ShowPanel implements DoAction {

    /** 数据显示表格. */
    private TableView<TableEntity> dataTable;
    /** 数据表格行标. */
    private TableColumn<TableEntity, String> rowColumn;
    /** 表格键. */
    private TableColumn<TableEntity, String> keyColumn;
    /** 表格值. */
    private TableColumn<TableEntity, String> valueColumn;

    public ListAction(TableView dataTable,
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
        List<String> lists = redisList.allElements(key);
        for (int i = 0; i < lists.size(); i++) {
            TableEntity value = new TableEntity("" + i, key, lists.get(i));
            values.add(value);
        }
        this.dataTable.setItems(values);
        this.rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty());
        this.keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        this.valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }


    /**
     * 修改数据.
     * @param key 数据库中的键
     * @param nowSelectRow 当前选择的行
     * @param selected 是否选择值
     */
    @Override
    public void setValueByIndex(String key, int nowSelectRow, boolean selected) {
        if (selected) {
            boolean ok = showValuePanel();
            if (ok) {
                String value = controller.getValue();
                try {
                    redisList.setByIndex(key, nowSelectRow, value);
                } catch (ActionErrorException e) {
                    Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("");
                    alert.setContentText("设置失败");
                    alert.showAndWait();
                }
                controller = null;
            }
        } else {
            Alert alert = MyAlert.getInstance(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("");
            alert.setContentText("请选择一个键");
            alert.showAndWait();
        }
    }

    /**
     * 添加值
     */
    @Override
    public void addValue(String key) {
        boolean ok = showValuePanel();
        if (ok) {
            String value = controller.getValue();
            redisList.rPush(key, value);
        }
        controller = null;
    }

    /**
     * 删除值.
     * @param key 数据库中的键
     */
    @Override
    public void delValue(String key) {
        Alert confirmAlert = MyAlert.getInstance(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("提示");
        confirmAlert.setHeaderText("");
        confirmAlert.setContentText("将删除显示的最后一个数据");
        Optional<ButtonType> opt = confirmAlert.showAndWait();
        ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            // 确定
            redisList.rPop(key);
        }
    }

    /**
     * 左添加值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void leftAddValue(String key) {
        boolean ok = showValuePanel();
        if (ok) {
            String value = controller.getValue();
            redisList.lPush(key, value);
        }
        controller = null;
    }

    /**
     * 左删除值.
     *
     * @param key 数据库中的键
     */
    @Override
    public void leftDelValue(String key) {
        Alert confirmAlert = MyAlert.getInstance(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("提示");
        confirmAlert.setHeaderText("");
        confirmAlert.setContentText("将删除显示的第一个数据");
        Optional<ButtonType> opt = confirmAlert.showAndWait();
        ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            // 确定
            redisList.lPop(key);
        }
    }

}
