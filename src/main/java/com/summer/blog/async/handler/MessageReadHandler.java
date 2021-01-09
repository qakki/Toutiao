package com.summer.blog.async.handler;

import com.summer.blog.async.EventHandler;
import com.summer.blog.async.EventModel;
import com.summer.blog.async.EventType;
import com.summer.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Component
public class MessageReadHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        messageService.readMessage(model.getEntityOwnerId(), model.getExts("conversationId"));
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.MESSAGE_READ);
    }
}
