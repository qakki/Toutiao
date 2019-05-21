package com.summer.blog;

import com.summer.blog.dao.BlogMapper;
import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.Blog;
import com.summer.blog.model.User;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlogApplication.class)
public class BlogApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Test
    public void userDaoTest() {
        System.out.println(userMapper.selectByName("summer").getId());
    }

    @Test
    public void judgeRegister() {
        userService.register("念奴娇", "12345678912345");
    }

    @Test
    public void contextLoads() {
        User user = new User();
        user.setId(1);
        user.setName("summer");
        user.setPassword("123");
        user.setSalt("");
        user.setHeadUrl("");
        userMapper.insert(user);
    }

    @Test
    public void blogTest() {
        List<Blog> list = blogService.selectByUserIdAndTimeDesc(0, 1, 2);
        StringBuilder sb = new StringBuilder();
        for (Blog blog : list) {
            sb.append("blog.getId()" + blog.getId() + "\t");
            sb.append("blog.getTitle()" + blog.getTitle() + "\t");
            sb.append("blog.getLink()" + blog.getLink() + "\t");
            sb.append("blog.getImage()" + blog.getImage() + "\t");
            sb.append("blog.getLikeCount()" + blog.getLikeCount() + "\t");
            sb.append("blog.getAddTime()" + blog.getAddTime() + "\t");
            sb.append("blog.getModTime()" + blog.getModTime() + "\t");
            sb.append("blog.getIsDel()" + blog.getIsDel() + "\t");
            sb.append("blog.getUserId()" + blog.getUserId() + "\t");
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void blogSelectTest() {
        Blog blog = blogMapper.selectByPrimaryKey(1);
        StringBuilder sb = new StringBuilder();

        sb.append("blog.getId()" + blog.getId() + "\t");
        sb.append("blog.getTitle()" + blog.getTitle() + "\t");
        sb.append("blog.getLink()" + blog.getLink() + "\t");
        sb.append("blog.getImage()" + blog.getImage() + "\t");
        sb.append("blog.getLikeCount()" + blog.getLikeCount() + "\t");
        sb.append("blog.getAddTime()" + blog.getAddTime() + "\t");
        sb.append("blog.getModTime()" + blog.getModTime() + "\t");
        sb.append("blog.getIsDel()" + blog.getIsDel() + "\t");
        sb.append("blog.getUserId()" + blog.getUserId() + "\t");
        sb.append("\n");

        System.out.println(sb.toString());
    }
}
