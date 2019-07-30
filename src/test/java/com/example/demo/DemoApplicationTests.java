package com.example.demo;

import com.example.demo.dto.CompareDto;
import com.example.demo.rpc.FreamWork;
import com.example.demo.util.IoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private FreamWork freamWork;


    @Test
    public void contextLoads()throws Exception{
        CompareDto compareDto = new CompareDto();
        compareDto.setType("2222");
        Object invoke = freamWork.methodInvoke("com.example.demo.rpc.FreamWork", "testRpc", compareDto);
        byte[] objectByte = IoUtils.getObjectByte(invoke);
        Object objectByByte = IoUtils.getObjectByByte(objectByte);
        System.out.println(objectByByte);
    }

}
