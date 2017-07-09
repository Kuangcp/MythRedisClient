package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:24
 * 记录操作的放在这里
 */
public interface NoticeInfo {
    String MAP_CONTAIN_POOL = "缓存集合中已经存在 从内存中调用: ";
    String CONFIG_CONTAIN_POOL = "配置文件中存在该连接池配置: ";
    String CRETE_POOL = "创建连接池并存入配置文件 : ";
    String DELETE_POOL_SUCCESS = "删除该连接池配置成功 : ";
    String DELETE_POOL_FAILED = "删除该连接池配置失败 : ";

    String DESTROY_POOL = "销毁连接池 : ";
    String ERROR_INFO="错误栈信息如下 : ";

}
