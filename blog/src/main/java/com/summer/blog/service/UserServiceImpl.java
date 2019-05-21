package com.summer.blog.service;

import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author     ：summerGit
 * @date       ：2019/5/20 0020
 * @description：
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectNameAndUrlById(int id) {
        return userMapper.selectNameAndUrlById(id);
    }
}
