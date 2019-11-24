package com.mlab.entity;

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
    private List<Node> prevNodes;
    private List<Node> succNodes;
    private int key;
    private Configuartion config;

    public Node(Integer key){
        this.prevNodes=new ArrayList<Node>();
        this.succNodes=new ArrayList<Node>();
        this.key=key;
        this.config=null;
    }
    public void setKey(int k){
        this.key=k;
    }
    public Integer getKey(){
        return this.key;
    }
    public void addPrev(Node node){
        this.prevNodes.add(node);
    }
    public void addSucc(Node node){
        this.succNodes.add(node);
    }
    public void setConfig(String name){

    }
    public Configuartion getConfig(){
        return this.config;
    }
}
