package com.mlab.domain;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @version: 1.0
 * @author: usr
 * @className: Sender
 * @packageName: com.mlab.domain
 * @description: MessageQueue Sender
 * @data: 2019-12-02 14:00
 **/
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

}