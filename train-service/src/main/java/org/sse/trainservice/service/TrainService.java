package org.sse.trainservice.service;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.scheduling.annotation.Async;
import org.sse.trainservice.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private MessageQueue messageQueue;
    @Autowired
    private ResultPool resultPool;
    @Autowired
    ApplicationEventPublisher publisher;

    public Boolean pushIntoMq(int id){
        messageQueue.offer(id);
        publisher.publishEvent(new TrainEvent(new Object()));
        return true;
    }

    @Async
    public List<String> trainModel(Integer id){

        return null;
    }

    @EventListener
    public void onTrainEvent(TrainEvent event){
        if(messageQueue.isEmpty()){
            return;
        }
        if(resultPool.isFull()){
            return;
        }
        Integer id= messageQueue.poll();
        Result r=resultPool.getEmptySite();
        r.setId(id);
        r.setStatus(TrainStatus.TRAINING);
        trainModel(id);

    }
}
