package org.sse.trainservice.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.sse.trainservice.configuration.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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



    public Boolean pushIntoMq(Integer id){
        CorrelationData correlationData = new CorrelationData(id.toString()+new Date());
        rabbitTemplate.convertAndSend(RabbitConfig.TASK_EXCHANGE_NAME, RabbitConfig.TASK_ROUTING_NAME,id,correlationData);
        return true;
    }

    @Async
    public void trainModel(Integer id){
        while (true){
            if(rabbitTemplate.receiveAndConvert()==null){

            }
        }

    }
}
