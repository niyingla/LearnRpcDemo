package com.example.demo.service;

import com.example.demo.dto.CompareDto;

/**
 * @author tangsg
 */
public interface UserInfoService {

     CompareDto getCompareDto(String type);

     CompareDto getCompareTest(String type);
}
