package org.sse.trainservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.service.MailService;

import java.util.Map;

/**
 * @version: 1.0
 * @author: usr
 * @className: ResultReceive
 * @packageName: org.sse.trainservice.domain
 * @description: send email
 * @data: 2019-12-04 15:49
 **/

@RabbitListener(queues = RabbitConfig.RESULT_QUEUE_NAME)
public class ResultReceiver {

    @Autowired
    MailService mailService;

    @RabbitHandler
    public void sendMail(Map<String,String> map) throws Exception
    {
        System.out.println("邮件监听器已收到消息");
        String to=map.get("to");
        String subject=map.get("subject");
        String content=map.get("content");
        mailService.sendSimpleMail(to,subject,content);
    }
}
