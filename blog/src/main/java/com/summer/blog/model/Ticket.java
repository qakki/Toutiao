package com.summer.blog.model;

import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    private Integer id;

    private Integer userId;

    private String ticket;

    private Date expired;

    private Integer status;

}