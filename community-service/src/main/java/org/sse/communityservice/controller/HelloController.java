package org.sse.communityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sse.communityservice.service.HelloService;

import java.util.List;

/**
 * @author HPY
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    HelloService helloService;

    @RequestMapping("/sayHello")
    public List<String> sayHello(){
        return helloService.sayHello();
    }


}
