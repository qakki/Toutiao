package com.summer.blog.service;

import com.github.pagehelper.PageHelper;
import com.summer.blog.dao.BlogMapper;
import com.summer.blog.model.Blog;
import com.summer.blog.util.BlogUtil;
import com.summer.blog.util.HDFSUtil;
import com.summer.blog.util.SettingUtil;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    //hdfs

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
            //最后没用hdfs存储
            //InputStream in = file.getInputStream();
            //FSDataOutputStream out = fileSystem.create(new Path(HDFS_IMAGE_DIR + fileName), true);
            //IOUtils.copyBytes(in, out, 4096, true);
            //return IMAGE_BLOG_DOMIN + "image?name=" + fileName;
            Files.copy(file.getInputStream(), new File(SettingUtil.NATIVE_IMAGE_DIR + fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            return SettingUtil.IMAGE_BLOG_DOMIN + "?name=" + fileName;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    @Override
    public void saveBlog(Blog blog) {
        blogMapper.insertSelective(blog);
    }

    @Override
    public String selectLinkById(int id) {
        return blogMapper.selectLinkById(id);
    }

    @Override
    public Blog selectAllById(int id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateById(Blog blog) {
        blogMapper.updateByPrimaryKeySelective(blog);
    }

}
