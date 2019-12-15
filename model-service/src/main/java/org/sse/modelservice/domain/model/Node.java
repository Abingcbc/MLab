package org.sse.modelservice.domain.model;

import com.alibaba.fastjson.JSONObject;
import org.sse.modelservice.domain.nodeconfig.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: cyy
 * @className: model
 * @packageName: com.mlab.lab
 * @description: node
 * @data: 2019-11-19 13:39
 **/
@Data
public class Node {
    private int key;
    private NodeConfig config;
    private List<Integer> prevNodes;
    private List<Integer> succNodes;
    private int indegree;

    public Node(Integer key) {
        this.prevNodes = new ArrayList<Integer>();
        this.succNodes = new ArrayList<Integer>();
        this.key = key;
        this.config = null;
        this.indegree = 0;
    }

    public void addPrev(Node node) {
        this.prevNodes.add(node.getKey());
        this.indegree = prevNodes.size();
    }

    public void addSucc(Node node) {
        this.succNodes.add(node.getKey());
    }

    public void setConfig(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        System.out.println(name);
        if (name.equals("TokenizerNode")) {
            System.out.println("OK!!!!!!!!!!!!!!!!!");
            config = new TokenizerNodeConfig(jsonObject.getString("inputCol"), jsonObject.getString("outputCol"));
        } else if (name.equals("HashingTFNode")) {
            config = new HashingTFNodeConfig(jsonObject.getString("inputCol"), jsonObject.getString("outputCol"),jsonObject.getInteger("numFeatures"));
        } else if (name.equals("LogisticRegressionNode")) {
            config = new LogisticRegressionNodeConfig(jsonObject.getInteger("maxIter"), jsonObject.getDouble("regParam"));
        }else if(name.equals("InputNode")){
            config=new InputNodeConfig(jsonObject.getString("fileName"));
        }else if(name.equals("OutputNode")){
            config=new OutputNodeConfig();
        }
    }

    public NodeConfig getConfig() {
        return this.config;
    }

}
