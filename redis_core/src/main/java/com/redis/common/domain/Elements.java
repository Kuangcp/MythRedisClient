package com.redis.common.domain;

import lombok.AllArgsConstructor;

/**
 * Created by https://github.com/kuangcp on 17-6-17  下午10:50
 * Redis 中的主体元素
 */
@AllArgsConstructor
public class Elements {
    private int db;
    private String id;
    private String key;
    private ElementsType elementsType;
    private Order order;

    public Elements(int db, String id, String key, ElementsType elementsType) {
        this.db = db;
        this.id = id;
        this.key = key;
        this.elementsType = elementsType;
    }
}
