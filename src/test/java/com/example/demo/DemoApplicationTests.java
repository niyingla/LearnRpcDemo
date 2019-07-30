package com.example.demo;

import com.example.demo.dto.CompareDto;
import com.example.demo.rpc.FreamWork;
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
        byte[] objectByte = FreamWork.getObjectByte(invoke);
        Object objectByByte = FreamWork.getObjectByByte(objectByte);
        System.out.println(objectByByte);
    }

}
