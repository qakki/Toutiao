package com.summer.blog.async.handler;

import com.summer.blog.async.EventHandler;
import com.summer.blog.async.EventModel;
import com.summer.blog.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Component
public class LikeHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        System.out.println("点赞");


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
