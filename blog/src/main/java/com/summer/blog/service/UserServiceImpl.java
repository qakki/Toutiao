package com.summer.blog.service;

import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.User;
import com.summer.blog.util.BlogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

    /**
     * @author: lightingSummer
     * @date: 2019/5/21 0021
     * @description: 用户注册
     * @param name 用户名
     * @param password 用户密码
     * @return java.util.Map<java.lang.String, java.lang.Object> 错误信息
     */
    @Override
    public Map<String, Object> register(String name, String password) {
        Map<String, Object> errors = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            errors.put("msgname", "用户名不能为空");
            return errors;
        }
        if (StringUtils.isBlank(password)) {
            errors.put("msgpwd", "用户密码不能为空");
            return errors;
        }
        User userIfEmpty = userMapper.selectByName(name);
        if (userIfEmpty != null) {
            errors.put("msgname", "用户名已经被注册");
            return errors;
        }

        //可以正常注册
        User user = new User();
        String salt = UUID.randomUUID().toString().substring(0, 5);
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setName(name);
        user.setSalt(salt);
        user.setPassword(BlogUtil.MD5(password + salt));
        user.setHeadUrl(head);
        userMapper.insertSelective(user);

        return errors;
    }
}
