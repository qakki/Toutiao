package com.summer.blog.model;

import org.springframework.stereotype.Component;

/**
 * @author     ：summerGit
 * @date       ：2019/5/21 0021
 * @description： 把用户信息暂存在ThreadLocal里
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}

