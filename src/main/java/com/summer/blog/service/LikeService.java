package com.summer.blog.service;

import com.summer.blog.util.JedisAdapter;
import com.summer.blog.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/29 0029
 * @description：
 */
@Service
public class LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * @author: lightingSummer
     * @date: 2019/5/29 0029
     * @description: judge if like
     */
    public int likeStatus(int userId, int entityType, int entityId) {
        if (jedisAdapter.sismember(RedisKeyUtil.getLikeKey(entityType, entityId), String.valueOf(userId))) {
            return 1;
        } else if (jedisAdapter.sismember(RedisKeyUtil.getDislikeKey(entityType, entityId), String.valueOf(userId))) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * @author: lightingSummer
     * @date: 2019/5/29 0029
     * @description: 点赞
     */
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    /**
     * @author: lightingSummer
     * @date: 2019/5/29 0029
     * @description: 点踩
     */
    public long dislike(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
