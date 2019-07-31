package com.example.demo.rpc;

import com.example.demo.service.UserInfoService;
import com.example.demo.util.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author tangsg
 */
@Component
public class AutoWiredFactory {

    private List<Class> rpcInterFace = Arrays.asList(UserInfoService.class);

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;


    public void setBean(Class interfaceServer) {
        Object interfaceInfo = ClassUtils.getInterfaceInfo(interfaceServer);
        defaultListableBeanFactory.registerSingleton(interfaceServer.getName(), interfaceInfo);

    }


    /**
     * todo 通过扫描获取所有rpc代理类
     */
    @PostConstruct
    public void autoWiredRpcProxy() {
        for (Class inter : rpcInterFace) {
            setBean(inter);
        }
    }
}
