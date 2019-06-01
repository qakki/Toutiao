package com.summer.blog.service;

import com.github.pagehelper.PageHelper;
import com.summer.blog.dao.MessageMapper;
import com.summer.blog.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/27 0027
 * @description：
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * @author: lightingSummer
     * @date: 2019/6/1 0001
     * @description: 添加站内信
     */
    @Override
    public int addMessage(Message message) {
        return messageMapper.insertSelective(message);
    }

    /**
     * @author: lightingSummer
     * @date: 2019/6/1 0001
     * @description: 查询站内信详细内容
     */
    @Override
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return messageMapper.selectByConversionId(conversationId);
    }

    /**
     * @author: lightingSummer
     * @date: 2019/6/1 0001
     * @description: 查询站内信列表
     */
    @Override
    public List<Message> getMessageByUserId(int userId, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return messageMapper.selectByUserId(userId);
    }

    @Override
    public int getUnreadMsgByUserId(int userId, String conversationId) {
        return messageMapper.selectUnreadCount(userId, conversationId);
    }

    @Override
    public void readMessage(int userId, String conversationId) {
        messageMapper.updateReadByConversionId(userId, conversationId);
    }
}
