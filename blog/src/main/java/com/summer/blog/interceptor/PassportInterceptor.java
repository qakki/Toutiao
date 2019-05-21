package com.summer.blog.interceptor;

import com.summer.blog.dao.TicketMapper;
import com.summer.blog.dao.UserMapper;
import com.summer.blog.model.HostHolder;
import com.summer.blog.model.Ticket;
import com.summer.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author     ：summerGit
 * @date       ：2019/5/21 0021
 * @description：
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            Ticket ticket1 = ticketMapper.selectByTicket(ticket);
            if (ticket1 == null || ticket1.getExpired().before(new Date()) || ticket1.getStatus() != 0) {
                return true;
            }
            User user = userMapper.selectByPrimaryKey(ticket1.getUserId());
            hostHolder.setUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
