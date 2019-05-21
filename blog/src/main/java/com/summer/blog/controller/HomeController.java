package com.summer.blog.controller;

import com.summer.blog.model.Blog;
import com.summer.blog.model.User;
import com.summer.blog.model.ViewObject;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Blog> blogs = blogService.selectByUserIdAndTimeDesc(0, 1, 10);
        List<ViewObject> list = new ArrayList<>();
        for (Blog blog : blogs) {
            User user = userService.selectNameAndUrlById(blog.getUserId());
            ViewObject homeView = new ViewObject();
            homeView.set("news", blog);
            homeView.set("user", user);
            list.add(homeView);
        }
        model.addAttribute("vos", list);
        return "home";
    }

    @RequestMapping(value = "/sayHi", method = RequestMethod.GET)
    public String sayHi(Model model) {
        model.addAttribute("hello", "hello world");
        return "hi";
    }

}
