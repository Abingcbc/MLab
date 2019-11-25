package com.mlab.entity;

import java.util.List;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Model
 * @packageName: com.mlab.lab.entity
 * @description: model from json
 * @data: 2019-11-23 7:33
 **/
public class Model {
    private Node input;
    private Node output;
    private List<Node> nodeList;
    private List<Link> linkList;
    private String name;
    public Model(String name){
        this.name=name;
    }
    public void excute(){}
    public Node getNodeByKey(int key){
        for (Node e:nodeList) {
            if(e.getKey()==key){
                return e;
            }
        }
        return null;
    }
    public void addNode(Node node){
        nodeList.add(node);
    }
    public  void setLink(int from, int to){
        Node node1=this.getNodeByKey(from);
        Node node2=this.getNodeByKey(to);
        node1.addSucc(this.getNodeByKey(to));
        node2.addPrev(this.getNodeByKey(from));
        linkList.add(new Link(node1,node2));
    }

    public String getName() {
        return name;
    }
}
