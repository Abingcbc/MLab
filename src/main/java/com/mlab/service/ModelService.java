package com.mlab.service;

import com.alibaba.fastjson.JSONArray;
import com.mlab.domain.Model;
import com.mlab.domain.Node;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Boolean setModel(JSONArray nodeArray, JSONArray linkArray, String name) {
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
        };
        return generateSparkPipeline(model);
    }

    public Model viewModel(String name) {
        Model model = new Model(name);
        return model;
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
