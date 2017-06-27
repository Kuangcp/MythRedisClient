package redis.manager.controller.operation;

import com.redis.assemble.key.RedisKey;
import redis.manager.Main;

/**
 * 数据操作的接口.
 * User: huang
 * Date: 17-6-26
 */
public interface DoAction {

    RedisKey redisKey = Main.redisKey;

    /**
     * 装载面板数据.
     *
     * @param key 数据库中的键
     */
    void setValue(String key);

    /**
     * 修改值.
     * @param key 数据库中的键
     * @param nowSelectRow 当前选择的值
     * @param selected 是否选择值
     */
    void setValueByIndex(String key, int nowSelectRow, boolean selected);

    /**
     * 添加值.
     * @param key 数据库中的键
     */
    void addValue(String key);

    /**
     * 删除值.
     * @param key 数据库中的键
     */
    void delValue(String key);

    /**
     * 左添加值.
     * @param key 数据库中的键
     */
    void leftAddValue(String key);

    /**
     * 左删除值.
     * @param key 数据库中的键
     */
    void leftDelValue(String key);

}
