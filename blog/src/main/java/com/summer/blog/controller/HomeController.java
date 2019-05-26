package com.summer.blog.controller;

import com.summer.blog.model.Blog;
import com.summer.blog.model.User;
import com.summer.blog.model.ViewObject;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.UserService;
import com.summer.blog.util.SettingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author     ：summerGit
 * @date       ：2019/5/16 0016
 * @description：
 */
@Controller
public class HomeController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        List<ViewObject> list = getNews(0, 1, 10);
        model.addAttribute("vos", list);
        model.addAttribute("pop", pop);
        return "home";
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public String userIndex(Model model, @PathVariable int userId) {
        List<ViewObject> list = getNews(userId, 1, 10);
        model.addAttribute("vos", list);
        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<Blog> blogs = blogService.selectByUserIdAndTimeDesc(userId, offset, limit);
        List<ViewObject> list = new ArrayList<>();
        for (Blog blog : blogs) {
            User user = userService.selectNameAndUrlById(blog.getUserId());
            ViewObject homeView = new ViewObject();
            homeView.set("news", blog);
            homeView.set("user", user);
            homeView.set("domain", SettingUtil.BLOG_DOMAIN + "news/");
            list.add(homeView);
        }
        return list;
    }
}
