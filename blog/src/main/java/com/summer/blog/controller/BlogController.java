package com.summer.blog.controller;

import com.summer.blog.model.Blog;
import com.summer.blog.model.HostHolder;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.QiniuService;
import com.summer.blog.util.BlogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

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

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
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

    @RequestMapping(value = "/user/addNews", method = RequestMethod.POST)
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

    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    @ResponseBody
    public void news(@PathVariable int newsId,
                     HttpServletResponse response) {
        String link = blogService.selectLinkById(newsId);
        try {
            if (link != null) {
                response.sendRedirect(link);
            } else {
                response.sendRedirect("/error.html");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
