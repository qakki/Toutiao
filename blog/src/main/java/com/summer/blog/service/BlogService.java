package com.summer.blog.service;

import com.summer.blog.model.Blog;

import java.util.List;

/**
 * @author     ：summerGit
 * @date       ：2019/5/16 0016
 * @description：
 */
public interface BlogService {
    List<Blog> selectByUserIdAndTimeDesc(int id,int page,int size);
}