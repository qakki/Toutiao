package com.summer.blog.controller;

import com.summer.blog.aspect.LogAspect;
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

import java.util.Map;

/**
 * @author     ：summerGit
 * @date       ：2019/5/21 0021
 * @description：
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/reg/", method = RequestMethod.GET)
    @ResponseBody
    public String register(Model model, @RequestParam("username") String name,
                           @RequestParam("password") String password,
                           @RequestParam(value = "rember", defaultValue = "0") int remember) {
        try {
            Map<String, Object> result = userService.register(name, password);
            if (result.isEmpty()) {
                return BlogUtil.getJSONString(0, "注册成功");
            } else {
                return BlogUtil.getJSONString(1, result);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return BlogUtil.getJSONString(1, "注册异常");
        }
    }
}
