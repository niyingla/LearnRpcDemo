package com.example.demo.controller;

import com.example.demo.dto.CompareDto;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Lazy
    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("getCompareDto")
    public CompareDto getCompareDto(){
        return userInfoService.getCompareDto("324");
    }
}
