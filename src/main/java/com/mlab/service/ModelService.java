package com.mlab.service;

import com.alibaba.fastjson.JSONArray;
import com.mlab.entity.Model;
import com.mlab.entity.Node;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
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

    public void setModel(JSONArray nodeArray, JSONArray linkArray, String name) {
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
        model.tpSort();
        generateSparkPipeline(model);
    }

    public Model viewModel(String name) {
        Model model = new Model(name);
        return model;
    }

    public void generateSparkPipeline(Model model) {
        ArrayList<PipelineStage> pipelineStages = new ArrayList<PipelineStage>();
        for (Node node : model.getNodeList()) {

        }
        Pipeline pipeline = new Pipeline().setStages(pipelineStages.toArray(new PipelineStage[0]));

    }

    public List<Node> test(JSONArray nodeArray, JSONArray linkArray, String name) {
        Model model = new Model(name);
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node node = new Node(nodeArray.getJSONObject(i).getInteger("key"));
            node.setConfig(nodeArray.getJSONObject(i));
            model.addNode(node);
            System.out.println(node.getKey());
        }
        for (int i = 0; i < linkArray.size(); ++i) {
            int from = linkArray.getJSONObject(i).getInteger("from");
            int to = linkArray.getJSONObject(i).getInteger("to");
            model.setLink(from, to);
        }
        model.tpSort();
        return model.getNodeList();
    }

}
