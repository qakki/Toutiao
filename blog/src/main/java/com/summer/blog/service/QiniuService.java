package com.summer.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.summer.blog.util.BlogUtil;
import com.summer.blog.util.SettingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author     ：summerGit
 * @date       ：2019/5/26 0026
 * @description：
 */
@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    /**
     * @author: lightingSummer
     * @date: 2019/5/26 0026
     * @description: qiniu upload image
     * @param file
     * @return java.lang.String
     */

    public String uploadImage(MultipartFile file) {
        //格式检查
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!BlogUtil.isFileAllowed(fileExt)) {
            return null;
        }
        try {
            //要上传的空间(bucket)的存储区域为华东时
            Zone z = Zone.zone0();
            //密钥配置
            Configuration c = new Configuration(z);
            Auth auth = Auth.create(SettingUtil.ACCESS_KEY, SettingUtil.SECRET_KEY);
            UploadManager uploadManager = new UploadManager(c);
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            Response response = uploadManager.put(file.getBytes(), fileName, auth.uploadToken(SettingUtil.bucketname));
            System.out.println(response.bodyString());
            return SettingUtil.QINIU_DOMAIN + JSONObject.parseObject(response.bodyString()).get("key");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
