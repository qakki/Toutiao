package com.summer.blog.controller;

import com.summer.blog.model.*;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.LikeService;
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

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop,
                        @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageCount = blogService.getTotalPageNum(0);
        if (page <= pageCount && page >= 0) {
            List<ViewObject> list = getNews(0, page, SettingUtil.BLOG_PAGE_LIMIT);
            model.addAttribute("vos", list);
            model.addAttribute("pop", pop);
        }
        return "home";
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public String userIndex(Model model, @PathVariable int userId,
                            @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageCount = blogService.getTotalPageNum(userId);
        if (page <= pageCount && page >= 0) {
            List<ViewObject> list = getNews(userId, page, SettingUtil.BLOG_PAGE_LIMIT);
            model.addAttribute("vos", list);
        }
        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<Blog> blogs = blogService.selectByUserIdAndAuthedBlog(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> list = new ArrayList<>();
        for (Blog blog : blogs) {
            User user = userService.selectNameAndUrlById(blog.getUserId());
            ViewObject homeView = new ViewObject();
            homeView.set("news", blog);
            homeView.set("user", user);
            homeView.set("domain", SettingUtil.BLOG_DOMAIN + "news/");
            if (localUserId != 0) {
                homeView.set("like", likeService.likeStatus(localUserId, EntityType.ENTITY_NEWS, blog.getId()));
            } else {
                homeView.set("like", 0);
            }
            list.add(homeView);
        }
        return list;
    }
}
