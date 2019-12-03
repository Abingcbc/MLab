package com.mlab.domain;

import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @version: V1.0
 * @author: cyy
 * @className: MessageQueue
 * @packageName: com.mlab.domain
 * @description: Message Queue
 * @data: 2019/12/2 上午11:14
 **/
@Component
public class MessageQueue {
    private Queue<Integer> messageQueue;

    public MessageQueue(){
        this. messageQueue=new LinkedList<Integer>();
    }

    public void offer(int i){
        messageQueue.offer(i);
    }
    public int poll(){
        return messageQueue.poll();
    }
    public int size(){
        return messageQueue.size();
    }
    public boolean isEmpty(){return messageQueue.isEmpty();}
}