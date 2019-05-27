package com.summer.blog.service;

import com.summer.blog.model.Comment;

import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/27 0027
 * @description：
 */
public interface CommentService {
    int addComment(Comment comment);

    List<Comment> getCommentByEntity(int entityId, int entityType);

    int getCommentCount(int entityId, int entityType);
}
