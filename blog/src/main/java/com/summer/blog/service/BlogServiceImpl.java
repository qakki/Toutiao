package com.summer.blog.service;

import com.github.pagehelper.PageHelper;
import com.summer.blog.dao.BlogMapper;
import com.summer.blog.model.Blog;
import com.summer.blog.util.BlogUtil;
import com.summer.blog.util.HDFSUtil;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author     ：summerGit
 * @date       ：2019/5/16 0016
 * @description：
 */
@Service
public class BlogServiceImpl implements BlogService {
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    private static final String IMAGE_DIR = "/javaTest/";
    private static final String BLOG_DOMIN = "http://127.0.0.1:8080";

    @Autowired
    private BlogMapper blogMapper;

    /**
     * @author: lightingSummer
     * @date: 2019/5/24 0024
     * @description: 用过userId查最新的blogs
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.summer.blog.model.Blog>
     */
    @Override
    public List<Blog> selectByUserIdAndTimeDesc(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectByUserIdAndModTimeDesc(userId);
        return list;
    }

    /**
     * @author: lightingSummer
     * @date: 2019/5/24 0024
     * @description: 保存图片
     * @param file
     * @return java.lang.String
     */
    @Override
    public String saveImage(MultipartFile file) {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!BlogUtil.isFileAllowed(fileExt)) {
            return null;
        }
        try {
            FileSystem fileSystem = HDFSUtil.getFileSystem();
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            InputStream in = file.getInputStream();
            FSDataOutputStream out = fileSystem.create(new Path(IMAGE_DIR + fileName), true);
            IOUtils.copyBytes(in, out, 4096, true);
            return BLOG_DOMIN + "image?name=" + fileName;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

}
