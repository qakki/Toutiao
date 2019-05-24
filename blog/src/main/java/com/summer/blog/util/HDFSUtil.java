package com.summer.blog.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * @author     ：summerGit
 * @date       ：2019/5/24 0024
 * @description：
 */
@Component
public class HDFSUtil {
    private static String hdfsPath;
    private static String hdfsName;
    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.username}")
    private String username;

    /**
     * 获取HDFS配置信息
     * @return
     */
    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsPath);
        return configuration;
    }

    /**
     * @author: lightingSummer
     * @date: 2019/5/24 0024
     * @description: get hdfs fileSystem
     * @return org.apache.hadoop.fs.FileSystem
     */
    public static FileSystem getFileSystem() throws Exception {
        FileSystem fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
        return fileSystem;
    }

    @PostConstruct
    private void setHdfsPath() {
        hdfsPath = this.path;
    }

    @PostConstruct
    private void setHdfsName() {
        hdfsName = this.username;
    }
}
