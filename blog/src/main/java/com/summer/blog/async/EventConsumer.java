package com.summer.blog.async;

import com.alibaba.fastjson.JSON;
import com.summer.blog.util.JedisAdapter;
import com.summer.blog.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(InitializingBean.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> list = entry.getValue().getSupportEventTypes();
                for (EventType type : list) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> tasks = jedisAdapter.brpop(0, key);
                    for (String task : tasks) {
                        if (task.equals(key)) {
                            continue;
                        }

                        EventModel event = JSON.parseObject(task, EventModel.class);
                        if (!config.containsKey(event.getType())) {
                            logger.error("unexpected key");
                            continue;
                        }

                        for (EventHandler handler : config.get(event.getType())) {
                            handler.doHandle(event);
                        }
                    }
                }
            }
        });
        thread.start();

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
