package com.mlab.controller;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mlab.domain.Model;
import com.mlab.domain.Node;
import com.mlab.service.ModelService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.http.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version: V1.0
 * @author: cyy
 * @className: modelController
 * @packageName: com.mlab.lab.controller
 * @description: model Controller
 * @data: 2019-11-19 12:50
 **/

@RestController
@RequestMapping("/model")
public class modelController {

    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generate(@RequestBody JSONObject jsonObject){
        JSONArray nodeArray=jsonObject.getJSONArray("nodeDataArray");
        JSONArray linkArray=jsonObject.getJSONArray("linkDataArray");
        String name=jsonObject.getString("class");
        modelService.setModel(nodeArray,linkArray,name);
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public Model view(@RequestBody JSONObject jsonObject){
        return modelService.viewModel(jsonObject.getString("class"));
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ResponseEntity<byte[]> download(@RequestBody JSONPObject jsonpObject){
        return null;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<Node> test(@RequestBody JSONObject jsonObject){

        JSONArray nodeArray=jsonObject.getJSONArray("nodeDataArray");
        JSONArray linkArray=jsonObject.getJSONArray("linkDataArray");
        String name=jsonObject.getString("class");
        return modelService.test(nodeArray,linkArray,name);
    }

    @RequestMapping(value = "/sparktest", method = RequestMethod.GET)
    public void sparkTest(@RequestBody JSONObject jsonObject){
        SparkSession spark= SparkSession.builder().appName("c").master("local").getOrCreate();
        Dataset<Row>data=spark.read().format("libsvm").load("sample_libsvm_data.txt");
        System.out.println(data.count());
        spark.stop();
    }
}
