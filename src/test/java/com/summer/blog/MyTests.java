package com.summer.blog;

import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.User;
import com.summer.blog.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/29 0029
 * @description：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlogApplication.class)
public class MyTests {

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void jedisTest() {
        jedisAdapter.set("test", "Hello,world");
    }

    @Test
    public void jedisTest1() {
        User user = userMapper.selectByPrimaryKey(16);
        jedisAdapter.setObject("user16", user);
        System.out.println(jedisAdapter.getObject("user16", User.class));
    }
}
