package com.example.demo.dto;

import lombok.Data;

/**
 * @program: demo
 * @description:
 * @author: xiaoye
 * @create: 2019-08-12 11:55
 **/
@Data
public class RpcRequestDto {

    private String requestId;

    private String classPath;

    private String methodName;

    private Object[] args;
}
