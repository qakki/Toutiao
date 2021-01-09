package com.summer.blog.controller;

import com.summer.blog.model.Blog;
import com.summer.blog.model.User;
import com.summer.blog.model.ViewObject;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.UserService;
import com.summer.blog.util.BlogUtil;
import com.summer.blog.util.SettingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/31 0031
 * @description：
 */
@Controller
public class ManageController {
    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/manage/blog", method = {RequestMethod.GET, RequestMethod.POST})
    public String manageNews(Model model) {
        //重新定义方法
        List<Blog> blogs = blogService.selectNeedAuthBlog(0, 1, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Blog blog : blogs) {
            ViewObject vo = new ViewObject();
            User user = userService.selectNameAndUrlById(blog.getUserId());
            vo.set("blog", blog);
            vo.set("user", user);
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "manage";
    }

    @RequestMapping(value = "/manage/agree", method = {RequestMethod.POST})
    @ResponseBody
    public String agreeBlog(@RequestParam("newsId") int newsId) {
        try {
            blogService.authBlog(newsId, SettingUtil.BLOG_AGREE);
            return BlogUtil.getJSONString(0);
        } catch (Exception e) {
            return BlogUtil.getJSONString(1, "操作失败");
        }
    }

    @RequestMapping(value = "/manage/refuse", method = {RequestMethod.POST})
    @ResponseBody
    public String refuseBlog(@RequestParam("newsId") int newsId) {
        try {
            blogService.authBlog(newsId, SettingUtil.BLOG_REFUSE);
            return BlogUtil.getJSONString(0);
        } catch (Exception e) {
            return BlogUtil.getJSONString(1, "操作失败");
        }
    }

    @RequestMapping(value = {"/noauth"}, method = RequestMethod.GET)

    public String noAuth(Model model) {
        return "auth";
    }

}
