package com.tlh.security.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return "Hello Oauth2 :" + name;
    }
}
