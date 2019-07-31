package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author tangsg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcServerDto {

    private String name;
    private List<example> examples;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class example{
        private String ip;
        private String port;
    }
}
