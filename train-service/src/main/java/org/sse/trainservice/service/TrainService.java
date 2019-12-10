package org.sse.trainservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.sse.trainservice.configuration.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @version: 1.0
 * @author: usr
 * @className: TrainService
 * @packageName: org.sse.trainservice.service
 * @description: train service
 * @data: 2019-12-03 11:16
 **/
@Service
public class TrainService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public Boolean pushIntoTrainMq(String userId, String pipelineId, String fileId){

        Map<String,String> map=new HashMap<String, String>();
        map.put("userId",userId);
        map.put("pipelineId",pipelineId);
        map.put("fileId",fileId);
        rabbitTemplate.convertAndSend(RabbitConfig.TASK_EXCHANGE_NAME, RabbitConfig.TASK_ROUTING_NAME,map);
        return true;
    }
    public Boolean pushIntoPredictMq(String userId, String pipelineId, String fileId){
        Map<String,String> map=new HashMap<String, String>();
        map.put("userId",userId);
        map.put("pipelineId",pipelineId);
        map.put("fileId",fileId);
        rabbitTemplate.convertAndSend(RabbitConfig.PREDICT_EXCHANGE_NAME, RabbitConfig.PREDICT_ROUTING_NAME,map);
        return true;
    }
}
