package com.summer.blog.async;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
