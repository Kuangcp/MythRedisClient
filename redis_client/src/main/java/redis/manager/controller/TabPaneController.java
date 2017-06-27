package redis.manager.controller;

import com.redis.assemble.key.RedisKey;
import com.redis.assemble.list.RedisList;
import com.redis.common.exception.ActionErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import redis.manager.Main;
import redis.manager.controller.operation.DoAction;
import redis.manager.controller.operation.ListAction;
import redis.manager.controller.operation.SetAction;
import redis.manager.entity.TableEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * tab面板的controller.
 * User: huang
 * Date: 17-6-9
 */
@Component
public class TabPaneController {

    private RedisKey redisKey = Main.redisKey;
    public static  String key;
    public static String poolId;

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
     * 删除数据.
     */
    @FXML
    private void del() {
        // TODO 删除数据.

    }

    /**
     * 添加一行数据.
     */
    @FXML
    private void addRow() {
        addValue();
    }

    /**
     * 删除一行数据.
     */
    @FXML
    private void delRow() {
        delValue();
    }

    /**
     * 重新加载数据.
     */
    @FXML
    public void reloadValue() {
        setValue();
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
            setValueByIndex();
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 装载面板数据.
     */
    private void setValue() {
        redisKey.getManagement().switchPool(poolId);
        String type = redisKey.type(key);
        typeText.setText(type);
        switch (type) {
            case "list" :
                doAction = new ListAction(dataTable, rowColumn, keyColumn, valueColumn);
                break;

            case "set" :
                doAction = new SetAction(dataTable, rowColumn, keyColumn, valueColumn);
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
                    public void delValue(String key) {}
                };
                break;
        }

        doAction.setValue(key);
    }


    /**
     * 修改列表对应键的值.
     */
    private void setValueByIndex() {
        doAction.setValueByIndex(key, nowSelectRow, selected);
        reloadValue();
    }

    /**
     * 添加列表的值.
     */
    private void addValue() {
        doAction.addValue(key);
        reloadValue();
    }

    /** 删除值, 删除显示的第一个值. */
    private void delValue() {
        doAction.delValue(key);
        reloadValue();
    }


}
