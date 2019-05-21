package com.summer.blog.service;

import com.summer.blog.model.User;

/**
 * @author     ：summerGit
 * @date       ：2019/5/20 0020
 * @description：
 */
public interface UserService {
    User selectNameAndUrlById(int id);
}
