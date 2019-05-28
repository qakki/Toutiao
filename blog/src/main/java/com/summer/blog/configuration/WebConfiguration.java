package com.summer.blog.configuration;

import com.summer.blog.interceptor.LoginRequiredInterceptor;
import com.summer.blog.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author     ：summerGit
 * @date       ：2019/5/22 0022
 * @description：
 */
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    /**
     * @author: lightingSummer
     * @date: 2019/5/27 0027
     * @description: filter 拦截器
     * @param registry
     * @return void
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/msg*");
        super.addInterceptors(registry);
    }
}
