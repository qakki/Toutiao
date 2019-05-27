package com.summer.blog.service;

import com.summer.blog.dao.CommentMapper;
import com.summer.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/27 0027
 * @description：
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    @Override
    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentMapper.selectByEntity(entityId, entityType);
    }

    @Override
    public int getCommentCount(int entityId, int entityType) {
        return commentMapper.getCommentCount(entityId, entityType);
    }
}
