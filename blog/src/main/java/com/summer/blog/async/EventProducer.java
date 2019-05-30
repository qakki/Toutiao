package com.summer.blog.async;

import com.alibaba.fastjson.JSON;
import com.summer.blog.util.JedisAdapter;
import com.summer.blog.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean addEvent(EventModel model) {
        try {
            String json = JSON.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


}
