package com.mlab.service;

import com.alibaba.fastjson.JSONArray;
import com.mlab.entity.Model;
import com.mlab.entity.Node;
import org.springframework.stereotype.Service;

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

    public void setModel(JSONArray nodeArray, JSONArray linkArray, String name){
        Model model=new Model(name);
        for(int i=0; i<nodeArray.size();++i){
            Node node=new Node(nodeArray.getJSONObject(i).getInteger("key"));
            model.addNode(node);
        }
        for (int i=0; i<linkArray.size();++i){
            int from=linkArray.getJSONObject(i).getInteger("from");
            int to=linkArray.getJSONObject(i).getInteger("to");
            model.setLink(from,to);
        }
    }
    public Model viewModel(String name){
        Model model=new Model(name);
        return model;
    }

}
