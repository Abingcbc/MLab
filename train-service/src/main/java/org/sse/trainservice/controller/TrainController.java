package org.sse.trainservice.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.trainservice.service.MailService;
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
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/train/{userId}/{pipelineId}/{fileId}", method = RequestMethod.GET)
    public boolean train(@PathVariable String userId, @PathVariable String pipelineId,@PathVariable String fileId){ return trainService.pushIntoTrainMq(userId,pipelineId,fileId); }

    @RequestMapping(value = "/predict/{userId}/{pipelineId}/{fileId}", method = RequestMethod.GET)
    public boolean predict(@PathVariable String userId, @PathVariable String pipelineId, @PathVariable String fileId){return trainService.pushIntoPredictMq(userId, pipelineId, fileId);}


}
