package com.summer.blog.model;

import lombok.Data;

import java.util.Date;

@Data
public class Blog {
    private Integer id;

    private String title;

    private String link;

    private String image;

    private String likeCount;

    private Integer commentCount;

    private Date addTime;

    private Date modTime;

    private Integer isDel;

    private Integer userId;

}