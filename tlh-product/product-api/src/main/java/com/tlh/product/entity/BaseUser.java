package com.tlh.product.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseUser {
    private Long id;
    private int age;
    private String userName;
    private String passWord;
    private String phone;
    private String email;
    private int enabled;
    private Date createTime;
}
