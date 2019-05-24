package com.summer.blog.controller;

import com.summer.blog.service.BlogService;
import com.summer.blog.util.BlogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/upLoadImage", method = RequestMethod.POST)
    @ResponseBody
    public String upLoadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = blogService.saveImage(file);
            if (fileUrl == null) {
                return BlogUtil.getJSONString(1, "上传失败");
            }
            return BlogUtil.getJSONString(1, fileUrl);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BlogUtil.getJSONString(1, "上传失败");
        }
    }
}
