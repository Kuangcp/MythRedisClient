package com.redis.assemble.set.sort;

import com.redis.common.Commands;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-23  下午9:51
 * @author kuangcp
 */
@Component
public class RedisSortSet extends Commands {
    // TODO 有序的set

    /**
     *
     * @param key 键
     * @param score 分数，可以相同，
     * @param member 成员，当已存在时，更新对应的分数
     * @return 1/0 成功/失败
     */
    public Long add(String key, Double score, String member){
        return getJedis().zadd(key, score,member);
    }
    /**按小到大排序，相同时，先插入在前*/
    public Long index(String key, String member){
       return getJedis().zrank(key, member);
    }
    /**得到成员对应的分数*/
    public Double score(String key, String member){
        return getJedis().zscore(key, member);
    }
    /**得到成员的下标 大到小排序 相同的分数时，先插入在后*/
    public Long rank(String key, String member){
        return getJedis().zrevrank(key, member);
    }
    /**得到相应索引范围的元素  大到小排序*/
    public Set<String> rangeByIndex(String key, long start, long end){
        return getJedis().zrevrange(key, start,end);
    }
    /**得到相应分数范围的元素，小到大排序*/
    public Set<String> rangeByScore(String key, Double min, Double max){
        return getJedis().zrevrangeByScore(key, max, min);
    }
    /**
     *根据字典来排序的
     * @param key 键
     * @param max 匹配的max [/( 开头
     * @param min 匹配的min [/( 开头
     * @return 成员集合
     */
    public Set<String> rangeByLex(String key, String max, String min){
        return getJedis().zrevrangeByLex(key, max, min);
    }
    /**删除指定字典范围中的元素*/
    public Long removeByLex(String key, String min, String max){
        return getJedis().zremrangeByLex(key,min,max);
    }
    /**对字典范围进行计数*/
    public Long countByLex(String key, String min, String max){
        return getJedis().zlexcount(key,min,max);
    }

    /**
     * 得到 范围内的成员,排序是按分数来的，小到大
     * @param key 键
     * @param start 起
     * @param end 终
     * @return 集合
     */
    public Set<String> range(String key, long start, long end){
        return getJedis().zrange(key, start, end);
    }
    /**得到所有成员 小到大排序*/
    public Set<String> getMemberSet(String key){
        return range(key,0,-1);
    }
    /**得到所有成员，大到小*/
    public Set<String> getMemberSetDesc(String key){
//        return rangeByLex(key,"+","-");
        return rangeByIndex(key,0,-1);
    }
    /**得到所有成员和分数的一个对象集合 小到大排序*/
    public Set<Tuple> getMemberSetWithScore(String key){
        return rangeWithScores(key, 0, -1);
    }
    /**
     * 得到成员和分数的集合
     * @param start 起
     * @param end 终
     * @return  Tuple集合
     */
    public Set<Tuple> rangeWithScores(String key, long start, long end){
        return getJedis().zrangeWithScores(key, start, end);
    }

    /**
     * 交集运算，存入目标键中
     * @param desKey 目标键
     * @param fromKey 若干个比较键
     * @return 返回交集大小
     */
    public Long interStore(String desKey, String... fromKey){
        return getJedis().zinterstore(desKey,fromKey);
    }

    /**
     * 为指定成员增加分数
     * @param score 增加的分数
     * @param member 成员，不存在就以增量来新建一个
     * @return 最终的分数
     */
    public Double increase(String key, Double score, String member){
        return getJedis().zincrby(key, score, member);
    }
    /**删除指定成员 返回 1/0 */
    public Long remove(String key, String... value){
        return getJedis().zrem(key, value);
    }
    /**
     * 删除区间的元素 小到大 索引 [start,end]
     * @param start 起始下标
     * @param end 终止下标
     * @return 删除的个数
     */
    public Long removeByRank(String key, long start, long end){
        return getJedis().zremrangeByRank(key,start,end);
    }
    //

    /**
     * 删除元素 分数区间：[start,end]
     * @param max 最大值
     * @param min 最小值
     * @return 删除的个数
     */
    public Long removeByScore(String key, Double min, Double max){
        return getJedis().zremrangeByScore(key,min,max);
    }

    public Long size(String key){
        return getJedis().zcard(key);
    }

    /**
     * 计数 分数区间 [min,max]
     * @param min min
     * @param max max
     * @return 个数
     */
    public Long count(String key, double min, double max){
        return getJedis().zcount(key,min,max);
    }
}

