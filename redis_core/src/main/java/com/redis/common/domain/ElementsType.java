package com.redis.common.domain;

/**
 * Created by https://github.com/kuangcp on 17-6-17  下午10:50
 * 所有主体元素的类型
 */
public enum ElementsType {
    ROOT, SERVER, DATABASE, CONTAINER, STRING, HASH, LIST, SET, SORTED_SET;

    @Override
    public String toString() {
        return super.toString();
    }
}
