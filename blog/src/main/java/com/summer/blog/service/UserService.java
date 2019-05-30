package com.summer.blog.service;

import com.summer.blog.model.User;

import java.util.Map;

/**
 * @author     ：summerGit
 * @date       ：2019/5/20 0020
 * @description：
 */
public interface UserService {
    User selectNameAndUrlById(int id);

    Map<String, Object> register(String name, String password);

    Map<String, Object> login(String name, String password);

    String addUserLoginTicket(int userId);

    void logout(String ticket);

    int selectIdByName(String name);
}
