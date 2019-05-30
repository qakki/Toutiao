package com.summer.blog.controller;

import com.summer.blog.async.EventModel;
import com.summer.blog.async.EventProducer;
import com.summer.blog.async.EventType;
import com.summer.blog.model.Blog;
import com.summer.blog.model.EntityType;
import com.summer.blog.model.HostHolder;
import com.summer.blog.service.BlogService;
import com.summer.blog.service.LikeService;
import com.summer.blog.util.BlogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/29 0029
 * @description：
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private BlogService blogService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/like", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        try {
            int userId = hostHolder.getUser().getId();
            long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
            blogService.updateLikeCount(newsId, String.valueOf(likeCount));
            Blog blog = blogService.selectAllById(newsId);

            EventModel model = new EventModel();
            model.setType(EventType.LIKE);
            model.setActorId(hostHolder.getUser().getId());
            model.setEntityType(EntityType.ENTITY_NEWS);
            model.setEntityId(newsId);
            model.setEntityOwnerId(blog.getUserId());
            eventProducer.addEvent(model);

            return BlogUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "点赞失败");
        }
    }

    @RequestMapping(value = "/dislike", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {
        try {
            int userId = hostHolder.getUser().getId();
            long likeCount = likeService.dislike(userId, EntityType.ENTITY_NEWS, newsId);
            blogService.updateLikeCount(newsId, String.valueOf(likeCount));
            return BlogUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "点踩失败");
        }
    }
}
