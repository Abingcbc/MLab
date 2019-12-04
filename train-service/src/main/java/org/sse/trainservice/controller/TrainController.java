package org.sse.trainservice.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sse.trainservice.service.TrainService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @version: 1.0
 * @author: usr
 * @className: TrainController
 * @packageName: org.sse.trainservice.controller
 * @description: controller of train service
 * @data: 2019-12-03 11:14
 **/
@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    TrainService trainService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    public boolean train(@RequestParam(name = "id") int id){
        return trainService.pushIntoMq(id);
    }

    @RequestMapping(value = "/predict", method = RequestMethod.GET)
    public Boolean predict(@RequestParam(name = "id") int id){return false;}

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public boolean test(){
        for (int i=0;i<10;++i){
            trainService.pushIntoMq(i);
        }
        return true;
    }

}
