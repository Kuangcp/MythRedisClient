package redis.manager.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 键数据显示表格的数据实体.
 * User: huang
 * Date: 17-6-26
 */
public class TableEntity {

    private StringProperty row;
    private StringProperty key;
    private StringProperty value;

    public TableEntity() {
    }

    public TableEntity(String row, String key, String value) {
        this.row = new SimpleStringProperty(row);
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
    }

    public String getRow() {
        return row.get();
    }

    public StringProperty rowProperty() {
        return row;
    }

    public void setRow(String row) {
        this.row.set(row);
    }

    public String getKey() {
        return key.get();
    }

    public StringProperty keyProperty() {
        return key;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
