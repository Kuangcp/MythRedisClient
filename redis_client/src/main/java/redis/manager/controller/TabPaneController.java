package redis.manager.controller;

import com.redis.assemble.key.RedisKey;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * tab面板的controller.
 * User: huang
 * Date: 17-6-9
 */
@Component
public class TabPaneController {
    @Autowired
    RedisKey redisKey;
    /**
     * 初始化, 装载数据.
     */
    @FXML
    private void initialize() {
        //TODO 装载相应的数据.
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
        // TODO 添加数据.
    }

    /**
     * 删除一行数据.
     */
    @FXML
    private void delRow() {
        // TODO 删除数据.
    }

    /**
     * 重新加载数据.
     */
    @FXML
    private void reloadValue() {
        // TODO 重新加载数据.
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

}
