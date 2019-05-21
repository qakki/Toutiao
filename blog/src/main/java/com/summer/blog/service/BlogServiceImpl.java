package com.summer.blog.service;

import com.github.pagehelper.PageHelper;
import com.summer.blog.dao.BlogMapper;
import com.summer.blog.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author     ：summerGit
 * @date       ：2019/5/16 0016
 * @description：
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> selectByUserIdAndTimeDesc(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectByUserIdAndModTimeDesc(userId);
        return list;
    }

}
