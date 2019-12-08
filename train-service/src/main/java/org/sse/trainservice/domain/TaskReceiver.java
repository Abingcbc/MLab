package org.sse.trainservice.domain;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.service.MailService;
import org.sse.trainservice.websocket.WebSocketSever;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map=new HashMap<String, String>();
        map.put("to","caiyiyang1998@126.com");
        map.put("subject","已完成任务训练："+id.toString());
        map.put("content","已完成任务训练："+id.toString());
        rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,map);
    }


}
