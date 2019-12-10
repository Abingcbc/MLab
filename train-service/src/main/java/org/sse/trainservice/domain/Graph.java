package org.sse.trainservice.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @version: 1.0
 * @author: usr
 * @className: Graph
 * @packageName: org.sse.trainservice.domain
 * @description: graph
 * @data: 2019-12-10 14:16
 **/
@Data
public class Graph {
    private String graphId;
    private String inputFile;
    private JSONObject model;
    public Graph(String graphId,String inputFile, JSONObject model){
        this.graphId=graphId;
        this.inputFile=inputFile;
        this.model=model;
    }
}
