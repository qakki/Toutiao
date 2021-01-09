package com.summer.blog.async.handler;

import com.summer.blog.async.EventHandler;
import com.summer.blog.async.EventModel;
import com.summer.blog.async.EventType;
import com.summer.blog.model.Blog;
import com.summer.blog.model.Message;
import com.summer.blog.model.User;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.MessageService;
import com.summer.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(1);
        message.setToId(model.getEntityOwnerId());
        message.setConversationId("1_" + model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        //后续改成拓展成枚举 EntityType 后续可能会有点赞评论
        User user = userService.selectNameAndUrlById(model.getActorId());
        Blog blog = blogService.selectAllById(model.getEntityId());
        message.setContent("您的文章" + blog.getTitle() + "被用户" + user.getName() + "赞了一下");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
