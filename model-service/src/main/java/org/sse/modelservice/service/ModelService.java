package org.sse.modelservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.sse.modelservice.repository.MongoDao;
import org.sse.modelservice.domain.model.Graph;
import org.sse.modelservice.domain.model.Model;
import org.sse.modelservice.domain.model.Node;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        //return generateSparkPipeline(model);
        return true;
    }



    public Graph viewModel(String name) {
        return mongoDao.findByName(name);
    }
    public Boolean deleteModel(String name){
        mongoDao.deleteById(name);
        return true;
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





}
