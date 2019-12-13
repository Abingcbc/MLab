package org.sse.trainservice.service;

import org.apache.spark.ml.Model;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.service.MailService;
import org.sse.trainservice.websocket.WebSocketSever;

import javax.xml.crypto.Data;
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
    public void train(Map<String,String> map){

        try {
            SparkSession spark=SparkSession.builder().appName(map.get("pipelineId")).master("local").getOrCreate();
            Dataset<Row> dataset=spark.read().option("inferSchema", true).option("header", true).csv("dataset/"+map.get("fileId")+".csv");
            Pipeline pipeline= Pipeline.load("pipeline/"+map.get("userId")+"/"+map.get("pipelineId"));
            PipelineModel model=pipeline.fit(dataset);
            model.write().overwrite().save("model/"+map.get("userId")+"/"+map.get("pipelineId"));
            spark.stop();

        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,String> rmap=new HashMap<String, String>();
        rmap.put("to","caiyiyang1998@126.com");
        rmap.put("subject","已完成任务训练："+map.get("pipelineId"));
        rmap.put("content","已完成任务训练："+map.get("pipelineId"));
        rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
    }


}
