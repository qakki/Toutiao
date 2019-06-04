package com.summer.blog.controller;

import com.summer.blog.model.*;
import com.summer.blog.service.*;
import com.summer.blog.util.BlogUtil;
import com.summer.blog.util.SettingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author     ：summerGit
 * @date       ：2019/5/24 0024
 * @description：
 */
@Controller
public class BlogController {
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/content/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            //String fileUrl = blogService.saveImage(file);
            String fileUrl = qiniuService.uploadImage(file);
            if (fileUrl == null) {
                return BlogUtil.getJSONString(1, "上传失败");
            }
            return BlogUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(value = "/content/addNews", method = RequestMethod.POST)
    @ResponseBody
    public String addBlog(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            Blog blog = new Blog();
            if (hostHolder.getUser() == null) {
                blog.setUserId(2);
            } else {
                blog.setUserId(hostHolder.getUser().getId());
            }
            blog.setTitle(title);
            blog.setImage(image);
            blog.setLink(link);
            blog.setAddTime(new Date());
            blog.setModTime(new Date());
            blogService.saveBlog(blog);
            return BlogUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "增加博客失败");
        }
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File("E:/JavaTest/" + imageName)), response.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/content/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setAddTime(new Date());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setUserId(hostHolder.getUser().getId());
            commentService.addComment(comment);
            Blog blog = new Blog();
            blog.setCommentCount(commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS));
            blog.setId(newsId);
            blogService.updateById(blog);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:" + SettingUtil.BLOG_DOMAIN + "/news/" + newsId;
    }

    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public String news(@PathVariable int newsId, Model model) {
        Blog blog = blogService.selectAllById(newsId);
        int nowId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
        try {
            if (blog != null) {
                User user = userService.selectNameAndUrlById(blog.getUserId());
                model.addAttribute("news", blog);
                model.addAttribute("owner", user);

                if (nowId != 0) {
                    model.addAttribute("like", likeService.likeStatus(nowId, EntityType.ENTITY_NEWS, blog.getId()));
                } else {
                    model.addAttribute("like", 0);
                }

                List<Comment> comments = commentService.getCommentByEntity(blog.getId(), EntityType.ENTITY_NEWS);
                List<ViewObject> list = new ArrayList<>();
                for (Comment comment : comments) {
                    User user1 = userService.selectNameAndUrlById(comment.getUserId());
                    ViewObject viewObject = new ViewObject();
                    viewObject.set("user", user1);
                    viewObject.set("comment", comment);
                    list.add(viewObject);
                }
                model.addAttribute("comments", list);
                return "detail";
            } else {
                return "error";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "error";
        }
    }
}
