package com.summer.blog.service;

import com.summer.blog.model.Message;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/27 0027
 * @description：
 */
public interface MessageService {

    int addMessage(Message message);

    List<Message> getConversationDetail(String conversationId, int offset, int limit);

    List<Message> getMessageByUserId(int userId, int offset, int limit);

    int getUnreadMsgByUserId(int userId, String conversationId);

    void readMessage(int userId, String conversationId);
}
