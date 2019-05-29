package com.summer.blog;

import com.summer.blog.dao.BlogMapper;
import com.summer.blog.dao.TicketMapper;
import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.*;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.CommentService;
import com.summer.blog.service.MessageService;
import com.summer.blog.service.UserService;
import com.summer.blog.util.HDFSUtil;
import com.summer.blog.util.JedisAdapter;
import junit.framework.Assert;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
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

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private JedisAdapter jedisAdapter;

    /*
    @Test
    public void RedisTest() {
        Jedis jedis = redisService.getInstance();
        jedis.flushAll();
        jedis.set("hello", "world");
        System.out.println(jedis.get("hello"));
    }*/

    @Test
    public void MessageTest() {
        System.out.println(messageService.getUnreadMsgByUserId(11, "11_16"));
        System.out.println(messageService.getMessageByUserId(16, 1, 10));
    }

    @Test
    public void commentTest1() {
        System.out.println(commentService.getCommentCount(9, 0));
    }


    @Test
    public void commentTest() {
        Comment comment = new Comment();
        comment.setUserId(16);
        comment.setEntityType(EntityType.ENTITY_NEWS);
        comment.setEntityId(9);
        comment.setAddTime(new Date());
        comment.setContent("悬崖上的金鱼公主");
        commentService.addComment(comment);

        Assert.assertNotNull(commentService.getCommentByEntity(9, EntityType.ENTITY_NEWS));
    }


    @Test
    public void hdfs() {
        try {
            FileSystem fs = HDFSUtil.getFileSystem();
            Path path = new Path("/javaTest");
            if (fs.mkdirs(path)) {
                System.out.println("ok");
            } else {
                System.out.println("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fail");
        }
    }

    @Test
    public void ticketDaoTest() {
        /*Ticket ticket = new Ticket();
        ticket.setUserId(22);
        ticket.setExpired(new Date());
        ticket.setStatus(0);
        ticket.setTicket("1321");
        ticketMapper.insertSelective(ticket);*/
        ticketMapper.setStatus("1321", 2);
        Ticket temp = ticketMapper.selectByTicket("1321");
        System.out.println(temp.getId() + "|" + temp.getTicket() + "| status:" + temp.getStatus());
    }

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
