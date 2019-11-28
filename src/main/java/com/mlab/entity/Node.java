package com.mlab.entity;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: cyy
 * @className: model
 * @packageName: com.mlab.lab
 * @description: model
 * @data: 2019-11-19 13:39
 **/
public class Node {
    private int key;
    private Configuartion config;
    private List<Integer> prevNodes;
    private List<Integer> succNodes;
    private int indegree;

    public Node(Integer key){
        this.prevNodes=new ArrayList<Integer>();
        this.succNodes=new ArrayList<Integer>();
        this.key=key;
        this.config=null;
        this.indegree=0;
    }
    public void setKey(int k){
        this.key=k;
    }
    public Integer getKey(){
        return this.key;
    }
    public void addPrev(Node node){
        this.prevNodes.add(node.getKey());
        this.indegree=prevNodes.size();
    }
    public void addSucc(Node node){
        this.succNodes.add(node.getKey());
    }
    public void setConfig(JSONObject  jsonObject){
        String name=jsonObject.getString("name");
        if(name=="Tokenizar"){
            config=new TokenizerConfiguration(jsonObject.getString("inputCol"),jsonObject.getString("outputCol"));
        }
        else if(name=="HashingTF"){
            config= new HashingTFConfiguration(jsonObject.getString("inputCol"),jsonObject.getString("outputCol"));
        }
        else if(name=="LogisticRegression"){
            config=new LogisticRegressionConfiguration(jsonObject.getInteger("maxIter"), jsonObject.getDouble("regParam"));
        }
    }
    public List<Integer> getPrevNodes(){
        return prevNodes;
    }
    public List<Integer> getSuccNodes(){
        return succNodes;
    }
    public Configuartion getConfig(){
        return this.config;
    }
    public int getIndegree(){
        return indegree;
    }
    public  void setIndegree(int i){
        indegree=i;
    }
}
