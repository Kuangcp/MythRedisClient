package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:24
 * 记录操作的放在这里
 */
public interface NoticeInfo {
    String ALREADY_EXIST_POOL = "连接池集合中已经存在，可直接调用 : ";
    String CRETE_POOL = "配置文件中创建连接池 : ";
    String DELETE_POOL_SUCCESS = "删除该连接池配置成功 : ";
    String DELETE_POOL_FAILED = "删除该连接池配置失败 : ";

}
