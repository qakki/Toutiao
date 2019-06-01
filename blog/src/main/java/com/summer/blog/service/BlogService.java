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
    List<Blog> selectByUserIdAndAuthedBlog(int userId, int pageNum, int pageSize);

    List<Blog> selectNeedAuthBlog(int userId, int pageNum, int pageSize);

    void authBlog(int id, int status);

    int getTotalPageNum(int userId);

    String saveImage(MultipartFile file);

    void saveBlog(Blog blog);

    String selectLinkById(int id);

    Blog selectAllById(int id);

    void updateById(Blog blog);

    void updateLikeCount(int id, String likeCount);
}
