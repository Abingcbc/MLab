package org.sse.trainservice.service;

import feign.Response;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.sse.trainservice.client.DataServiceClient;
import org.sse.trainservice.client.MedataServiceClient;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.domain.Model;
import org.sse.trainservice.domain.History;
import org.sse.trainservice.websocket.WebSocketSever;

import java.io.*;
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
    @Autowired
    MedataServiceClient medataServiceClient;
    @Autowired
    DataServiceClient dataServiceClient;

    public TaskReceiver(String instanceName){
        this.instanceName=instanceName;
    }

    @RabbitHandler
    public void train(Map<String,String> map){

        try {
            try {
                WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":running");
            }
            catch (Exception e){

            }
            Response response = dataServiceClient.downloadFile(map.get("fileId"), "csv");
            try(InputStream inputStream = response.body().asInputStream();
                OutputStream outputStream = new FileOutputStream(new File("tmp/"+map.get("fileId")+".csv"))
            ){
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                outputStream.write(b);
                outputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
            Long historyId=Long.valueOf(map.get("historyId"));
            Long modelId=Long.valueOf(map.get("modelId"));
            medataServiceClient.setHistory(historyId,2);
            SparkSession spark=SparkSession.builder().appName(map.get("pipelineId")).master("local").getOrCreate();
            Dataset<Row> dataset=spark.read().option("inferSchema", true).option("header", true).csv("tmp/"+map.get("fileId")+".csv");
            Pipeline pipeline= Pipeline.load("pipeline/"+map.get("username")+"/"+map.get("pipelineName"));
            PipelineModel model=pipeline.fit(dataset);
            model.write().overwrite().save("model/"+map.get("username")+"/"+modelId);
            spark.stop();
            medataServiceClient.setHistory(historyId,3);
            medataServiceClient.setEndTime(historyId);
            try {
                WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":complete");
            }
            catch (Exception e){
            }

        }catch (Exception e){
            medataServiceClient.setHistory(Long.valueOf(map.get("historyId")),4);
            medataServiceClient.setEndTime(Long.valueOf(map.get("historyId")));
            try {
                WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":fail");
            }
            catch (Exception e2){
            }
            Map<String,String> rmap=new HashMap<String, String>();
            rmap.put("to","caiyiyang1998@126.com");
            rmap.put("subject","任务训练失败："+map.get("historyId").toString());
            rmap.put("content","任务训练失败："+map.get("historyId").toString());
            rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
        }
        Map<String,String> rmap=new HashMap<String, String>();
        rmap.put("to","caiyiyang1998@126.com");
        rmap.put("subject","已完成任务训练："+map.get("historyId").toString());
        rmap.put("content","已完成任务训练："+map.get("historyId").toString());
        rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
    }


}
