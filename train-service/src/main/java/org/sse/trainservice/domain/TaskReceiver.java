package org.sse.trainservice.domain;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.websocket.WebSocketSever;

/**
 * @version: 1.0
 * @author: usr
 * @className: DirectReceiver
 * @packageName: org.sse.trainservice.domain
 * @description: receive message
 * @data: 2019-12-03 18:34
 **/
@RabbitListener(queues = RabbitConfig.TASK_QUEUE_NAME)
public class TaskReceiver {

    private String instanceName;
    @Autowired
    WebSocketSever webSocketSever;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public TaskReceiver(String instanceName){
        this.instanceName=instanceName;
    }

    @RabbitHandler
    public void train(Integer id) throws Exception {
        System.out.println("监听器"+this.instanceName+"号已接收消息"+id.toString());
        Thread.sleep(5000);
        System.out.println("监听器"+this.instanceName+"号已完成消息"+id.toString());
        rabbitTemplate.convertAndSend(RabbitConfig.TASK_EXCHANGE_NAME, RabbitConfig.TASK_ROUTING_NAME,id);
    }


}
