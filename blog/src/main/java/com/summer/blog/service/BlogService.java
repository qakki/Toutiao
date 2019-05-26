package com.summer.blog.service;

import com.summer.blog.model.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author     ：summerGit
 * @date       ：2019/5/16 0016
 * @description：
 */
public interface BlogService {
    List<Blog> selectByUserIdAndTimeDesc(int id, int page, int size);

    String saveImage(MultipartFile file);

    void saveBlog(Blog blog);

    String selectLinkById(int id);
}
