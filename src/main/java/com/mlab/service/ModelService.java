package com.mlab.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlab.Repository.MongoDao;
import com.mlab.domain.*;

import com.mlab.domain.model.Graph;
import com.mlab.domain.model.Model;
import com.mlab.domain.model.Node;
import com.mlab.websocket.WebSocketSever;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version: V1.0
 * @author: cyy
 * @className: ModelService
 * @packageName: com.mlab.lab.service
 * @description: model service
 * @data: 2019-11-19 12:47
 **/

@Service
public class ModelService {

    @Autowired
    private MessageQueue messageQueue;
    @Autowired
    private ResultPool resultPool;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    MongoDao mongoDao;

    public Boolean setModel(JSONObject jsonObject) {
        JSONArray nodeArray = jsonObject.getJSONArray("nodeDataArray");
        JSONArray linkArray = jsonObject.getJSONArray("linkDataArray");
        String name = jsonObject.getString("class");
        Model model = new Model(name);
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node node = new Node(nodeArray.getJSONObject(i).getInteger("key"));
            node.setConfig(nodeArray.getJSONObject(i));
            model.addNode(node);
        }
        for (int i = 0; i < linkArray.size(); ++i) {
            int from = linkArray.getJSONObject(i).getInteger("from");
            int to = linkArray.getJSONObject(i).getInteger("to");
            model.setLink(from, to);
        }
        if(!model.tpSort()){
            return false;
        }
        mongoDao.save(new Graph(name,nodeArray,linkArray));
        //mongoDao.save(nodeArray.getJSONObject(0));
        //return generateSparkPipeline(model);
        return true;
    }

    @Async
    public List<String> trainModel(Integer id){
        ArrayList<String> list=new ArrayList<String>();
        SparkSession spark= SparkSession.builder().master("local").getOrCreate();
        PipelineModel model = PipelineModel.load("data/bbbb");
        Dataset<Row> test = spark.createDataFrame(Arrays.asList(
                new JavaDocument(4L, "spark i j k"),
                new JavaDocument(5L, "l m n"),
                new JavaDocument(6L, "spark hadoop spark"),
                new JavaDocument(7L, "apache hadoop")
        ), JavaDocument.class);
        Dataset<Row> predictions = model.transform(test);
        for (Row r : predictions.select("id", "text", "probability", "prediction").collectAsList()) {
            list.add("(" + r.get(0) + ", " + r.get(1) + ") --> prob=" + r.get(2)
                    + ", prediction=" + r.get(3));
        }
        spark.stop();
        return list;
    }

    public Graph viewModel(String name) {
        return mongoDao.findByName(name);
    }

    public Boolean generateSparkPipeline(Model model) {
        SparkSession spark=SparkSession.builder().appName(model.getName()).master("local").getOrCreate();
        ArrayList<PipelineStage> pipelineStages = new ArrayList<PipelineStage>();
        for (Node node : model.getNodeList()) {
            pipelineStages.add(node.getConfig().getPipelineStage());
        }
        Pipeline pipeline = new Pipeline().setStages(pipelineStages.toArray(new PipelineStage[0]));
        try {
            pipeline.save("data/pipeline/");
        }
        catch (Exception e){
            spark.stop();
            return false;
        }

        spark.stop();
        return true;
    }

    public Boolean pushIntoMq(int id){
        messageQueue.offer(id);
        publisher.publishEvent(new TrainEvent(new Object()));
        return true;
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
        r.setID(id);
        r.setStatus(TrainStatus.TRAINING);
        trainModel(id);

    }

}
