package com.summer.blog.controller;

import com.summer.blog.model.HostHolder;
import com.summer.blog.model.Message;
import com.summer.blog.model.User;
import com.summer.blog.model.ViewObject;
import com.summer.blog.service.MessageService;
import com.summer.blog.service.UserService;
import com.summer.blog.util.BlogUtil;
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
import java.util.Date;
import java.util.List;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/27 0027
 * @description：
 */
@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    //分页待定
    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messages = messageService.getConversationDetail(conversationId, 1, 10);
            List<ViewObject> list = new ArrayList<>();
            for (Message message : messages) {
                ViewObject viewObject = new ViewObject();
                viewObject.set("message", message);
                User user = userService.selectNameAndUrlById(message.getFromId());
                if (user == null) {
                    continue;
                }
                viewObject.set("headUrl", user.getHeadUrl());
                viewObject.set("userName", user.getName());
                list.add(viewObject);
            }
            model.addAttribute("messages", list);
            return "letterDetail";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "error";
        }
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getMessageByUserId(localUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.selectNameAndUrlById(targetId);
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userName", user.getName());
                vo.set("userId", targetId);
                vo.set("totalCount", msg.getId());
                vo.set("unreadCount", messageService.getUnreadMsgByUserId(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
            return "letter";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setContent(content);
            message.setCreatedDate(new Date());
            String conversionId = fromId > toId ? String.format("%d_%d", toId, fromId) : String.format("%d_%d", fromId, toId);
            message.setConversationId(conversionId);
            if (messageService.addMessage(message) == 1) {
                return BlogUtil.getJSONString(0);
            } else {
                return BlogUtil.getJSONString(1, "发送消息失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "发送消息失败");
        }
    }

}
