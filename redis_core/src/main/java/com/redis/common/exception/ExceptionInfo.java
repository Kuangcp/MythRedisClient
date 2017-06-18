package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:08
 * 各种异常提示信息放在这里
 */
public interface ExceptionInfo {
    public final String GET_POOL_BY_ID_FAILED = "获取指定id的连接池失败";
    public final String NOT_EXIST_CURRENT_POOL = "当前没有活跃的连接池";
    public final String OPEN_CONFIG_FAILED = "打开配置文件失败：";
    public final String POOL_NOT_EXIST_CONFIG = "配置文件中不存在该连接池，请刷新重试或新建添加";
    public final String POOL_NOT_AVAILABLE = "该连接池不可用";
    public final String DELETE_POOL_ERROR = "删除连接池异常";
    public final String DELETE_POOL_NOT_EXIST = "要删除的连接池配置不存在:";
    public final String VALUE_NOT_INT = "该对象不能转换成数值类型";
    public final String KEY_TYPE_NOT_STRING = "键不是String类型";
}
