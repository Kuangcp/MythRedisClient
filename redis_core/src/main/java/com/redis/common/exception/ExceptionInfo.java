package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:08
 * 各种异常提示信息放在这里
 */
public interface ExceptionInfo {
    String GET_POOL_BY_ID_FAILED = "获取指定id的连接池失败";
    String NOT_EXIST_CURRENT_POOL = "当前没有活跃的连接池(CurrentId is Null)";


    String POOL_NOT_AVAILABLE = "该连接池获取连接失败";
    String DELETE_POOL_ERROR = "删除连接池异常";
    String DELETE_POOL_NOT_EXIST = "要删除的连接池配置不存在:";
    String VALUE_NOT_INT = "该对象不能转换成数值类型";
    String KEY_TYPE_NOT_STRING = "Key 不是String类型";

    String CREATE_CONFIG_SUCCESS = "创建主配置文件成功";
    String CREATE_CONFIG_FAILED = "创建主配置文件失败,检查文件夹访问权限";
    String OPEN_CONFIG_FAILED = "打开配置文件失败：";
    String POOL_NOT_EXIST_CONFIG = "配置文件中不存在该连接池，请刷新重试或新建添加";
    String UPDATE_CONFIG_KEY_FAILED = "配置文件中更新属性失败";
    String SAVE_CONFIG__KEY_FAILED = "配置文件中添加属性失败";
    String DELETE_CONFIG_KEY_FAILED = "配置文件中删除属性失败";

    String KEY_NOT_EXIST = "Key 不存在";
}
