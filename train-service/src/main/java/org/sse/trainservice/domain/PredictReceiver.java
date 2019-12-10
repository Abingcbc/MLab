package org.sse.trainservice.domain;

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
import org.sse.trainservice.websocket.WebSocketSever;

import java.util.HashMap;
import java.util.Map;

/**
 * @version: 1.0
 * @author: usr
 * @className: PredictListener
 * @packageName: org.sse.trainservice.domain
 * @description: receive predict task
 * @data: 2019-12-09 15:22
 **/
@RabbitListener(queues = RabbitConfig.PREDICT_QUEUE_NAME)
public class PredictReceiver {
    private String instanceName;
    @Autowired
    WebSocketSever webSocketSever;

    @Autowired
    RabbitTemplate rabbitTemplate;
    public PredictReceiver(String instanceName){
        this.instanceName=instanceName;
    }

    @RabbitHandler
    public void predict(Map<String,String> map){

        try {
            SparkSession spark=SparkSession.builder().appName(map.get("pipelineId")).master("local").getOrCreate();
            Dataset<Row> dataset=spark.read().option("inferSchema", true).option("header", true).csv("dataset/"+map.get("fileId")+".csv");
            PipelineModel model= PipelineModel.load("model/"+map.get("userId")+"/"+map.get("pipelineId"));
            Dataset<Row> predictions = model.transform(dataset);
            predictions.select("id","text","prediction").write().format("csv").option("header",true).save("predictions/"+map.get("pipelineId"));
            spark.stop();

        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,String> rmap=new HashMap<String, String>();
        rmap.put("to","caiyiyang1998@126.com");
        rmap.put("subject","已完成预测："+map.get("pipelineId"));
        rmap.put("content","已完成预测："+map.get("pipelineId"));
        rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
    }
}
