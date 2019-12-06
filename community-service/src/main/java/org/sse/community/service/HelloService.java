package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.HelloMapper;

import java.util.List;

@Service
public class HelloService {

    @Autowired
    HelloMapper helloMapper;


    public List<String> sayHello(){
        return helloMapper.sayHello();
    }
}
