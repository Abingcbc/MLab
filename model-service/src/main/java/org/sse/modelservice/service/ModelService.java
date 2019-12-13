package org.sse.modelservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.sse.modelservice.domain.model.*;
import org.sse.modelservice.domain.nodeconfig.InputNodeConfig;
import org.sse.modelservice.repository.MongoDao;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import org.sse.modelservice.client.DataServiceClient;
import org.sse.modelservice.client.MedataServiceClient;

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
    @Autowired
    DataServiceClient dataServiceClient;
    @Autowired
    MedataServiceClient medataServiceClient;
    @Autowired
    DeleteHandler deleteHandler;

    public String setModel(JSONObject jsonObject,String username, String pipelineName, String description) {
        JSONArray nodeArray = jsonObject.getJSONArray("nodeDataArray");
        JSONArray linkArray = jsonObject.getJSONArray("linkDataArray");
        PipelineInformation pipelineInformation=new PipelineInformation(username,pipelineName,description);
        Model model = new Model(pipelineInformation);
        String inputFile=new String();
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node node = new Node(nodeArray.getJSONObject(i).getInteger("key"));
            node.setConfig(nodeArray.getJSONObject(i));
            if(node.getConfig().getType()=="Input"){
                InputNodeConfig config=(InputNodeConfig) node.getConfig();
                inputFile=config.getFileName();
            }
            model.addNode(node);
        }
        for (int i = 0; i < linkArray.size(); ++i) {
            int from = linkArray.getJSONObject(i).getInteger("from");
            int to = linkArray.getJSONObject(i).getInteger("to");
            model.setLink(from, to);
        }
        if(!model.tpSort()){
            return "Circle Graph";
        }

        String result=generateSparkPipeline(model);
       /** ResponseEntity<Response> response=medataServiceClient.createNewPipeline(model.getPipelineInformation());
        if(response.getStatusCode()!= HttpStatus.OK){
            return "fail";
        }**/
        mongoDao.save(new Graph("123",inputFile,jsonObject));
        return result;
    }



    public Graph editPipeline(String id) {
        return mongoDao.findByGraphId(id);
    }

    public int deleteFile(Long userId,String fileType,Long fileId){
        try {
            //medataServiceClient.deletePipeline(fileId);
            mongoDao.deleteById(fileId.toString());
            deleteHandler.deleteDirectory(fileType+"/"+userId.toString()+"/"+fileId.toString());
        }
        catch (Exception e){
            return -1;
        }
        return 1;
    }

    public String generateSparkPipeline(Model model) {
        SparkSession spark=SparkSession.builder().appName(model.getPipelineInformation().getPipelineName()).master("local").getOrCreate();
        ArrayList<PipelineStage> pipelineStages = new ArrayList<PipelineStage>();
        for (Node node : model.getNodeList()) {
            if(node.getConfig().getType()=="Input"){
                continue;
            }
            pipelineStages.add(node.getConfig().getPipelineStage());
        }
        Pipeline pipeline = new Pipeline().setStages(pipelineStages.toArray(new PipelineStage[0]));
        try {
            pipeline.write().overwrite().save("pipeline/"+model.getPipelineInformation().getUsername()+"/"+model.getPipelineInformation().getPipelineName());
        }
        catch (Exception e){
            spark.stop();
            return e.getMessage();
        }

        spark.stop();
        return "OK";
    }



}

