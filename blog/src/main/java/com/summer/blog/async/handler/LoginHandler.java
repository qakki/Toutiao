package com.summer.blog.async.handler;

import com.summer.blog.async.EventHandler;
import com.summer.blog.async.EventModel;
import com.summer.blog.async.EventType;
import com.summer.blog.model.Message;
import com.summer.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Component
public class LoginHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(1);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setConversationId("1_" + model.getEntityOwnerId());
        Date ss = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime());
        message.setContent("您的账号于" + time + "在ip地址 " + model.getExts("ip") + " 登录");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
