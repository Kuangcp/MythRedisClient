package redis.manager.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 面板左侧树信息实体.
 * User: huang
 * Date: 17-6-23
 */
public class TreeEntity {
    /** 连接名称. */
    private StringProperty name;

    public TreeEntity(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
