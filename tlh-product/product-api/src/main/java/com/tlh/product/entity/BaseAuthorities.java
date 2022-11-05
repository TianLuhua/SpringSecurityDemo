package com.tlh.product.entity;

import lombok.Data;

@Data
public class BaseAuthorities {
    private Long id;
    private Long userId;
    private String authorities;
}
