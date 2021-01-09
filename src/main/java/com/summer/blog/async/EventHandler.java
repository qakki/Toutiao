package com.summer.blog.async;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description： handler以后写的时候 处理的时候 按照场景分
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
