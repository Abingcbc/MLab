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
import org.sse.trainservice.client.UserServiceClient;
import org.sse.trainservice.configuration.RabbitConfig;
import org.sse.trainservice.domain.User;
import org.sse.trainservice.websocket.WebSocketSever;

import java.io.*;
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
    @Autowired
    DataServiceClient dataServiceClient;
    @Autowired
    UserServiceClient userServiceClient;
    public PredictReceiver(String instanceName){
        this.instanceName=instanceName;
    }

    @RabbitHandler
    public void predict(Map<String,String> map){

        try {
            try {
                WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":running");
            }
            catch (Exception e){

            }
            String filePath=downloadFromDataService(map.get("fileId"));
            SparkSession spark=SparkSession.builder().appName(map.get("modelId")).master("local").getOrCreate();
            Dataset<Row> dataset=spark.read().option("inferSchema", true).option("header", true).csv(filePath);
            PipelineModel model= PipelineModel.load("model/"+map.get("username")+"/"+map.get("modelId"));
            Dataset<Row> predictions = model.transform(dataset);
            predictions.select("id","prediction").write().format("csv").option("header",true).save("predictions/"+map.get("username")+"/"+map.get("historyId"));
            spark.stop();
            try {
                WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":complete");
            }
            catch (Exception e){
                try {
                    WebSocketSever.get(map.get("username")).sendMessage(map.get("historyId")+":fail");
                }
                catch (Exception e2){
                }
                Map<String,String> rmap=new HashMap<String, String>();
                User userInfo=userServiceClient.getUserInfoWithoutPassword(map.get("username"));
                rmap.put("to", userInfo.getEmail());
                rmap.put("subject","预测任务失败："+map.get("historyId").toString());
                rmap.put("content","预测任务失败："+map.get("historyId").toString());
                rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,String> rmap=new HashMap<String, String>();
        rmap.put("to","caiyiyang1998@126.com");
        rmap.put("subject","已完成预测："+map.get("pipelineId"));
        rmap.put("content","已完成预测："+map.get("pipelineId"));
        rabbitTemplate.convertAndSend(RabbitConfig.RESULT_EXCHANGE_NAME, RabbitConfig.RESULT_ROUTING_NAME,rmap);
    }
    private String downloadFromDataService(String fileId){
        Response response = dataServiceClient.downloadFile(fileId, "csv");
        try(InputStream inputStream = response.body().asInputStream();
            OutputStream outputStream = new FileOutputStream(new File("tmp/"+fileId+".csv"))
        ){
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            outputStream.write(b);
            outputStream.flush();
            return "tmp/"+fileId+".csv";
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
