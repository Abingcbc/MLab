package org.sse.trainservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.sse.trainservice.client.MedataServiceClient;
import org.sse.trainservice.configuration.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.trainservice.domain.History;
import org.sse.trainservice.domain.Model;
import org.sse.trainservice.websocket.WebSocketSever;

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
@Transactional
public class TrainService {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MedataServiceClient medataServiceClient;
    @Autowired
    WebSocketSever webSocketSever;

    public Boolean pushIntoTrainMq(String username, Long pipelineId,String pipelineName,String description, String fileId){

        try {
            Long modelId= medataServiceClient.createNewModel(new Model(username,pipelineName,description));
            Long historyId =medataServiceClient.createNewHistory(new History(1,username,Long.valueOf(pipelineId),modelId));
            Map<String,String> map=new HashMap<String, String>();
            map.put("username",username);
            map.put("pipelineName",pipelineName);
            map.put("pipelineId",pipelineId.toString());
            map.put("fileId",fileId);
            map.put("description",description);
            map.put("historyId",historyId.toString());
            map.put("modelId",modelId.toString());
            rabbitTemplate.convertAndSend(RabbitConfig.TASK_EXCHANGE_NAME, RabbitConfig.TASK_ROUTING_NAME,map);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Boolean pushIntoPredictMq(String username, Long modelId,String modelName, String fileId){
        try {
            Long historyId =medataServiceClient.createNewHistory(new History(0,username,0,modelId));
            Map<String,String> map=new HashMap<String, String>();
            map.put("userId",username);
            map.put("modelId",modelId.toString());
            map.put("historyId",historyId.toString());
            map.put("fileId",fileId);
            rabbitTemplate.convertAndSend(RabbitConfig.PREDICT_EXCHANGE_NAME, RabbitConfig.PREDICT_ROUTING_NAME,map);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
