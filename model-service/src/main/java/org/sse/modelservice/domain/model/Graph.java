package org.sse.modelservice.domain.model;



import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * @version: 1.0
 * @author: usr
 * @className: Graph
 * @packageName: com.mlab.org.sse.trainservice.domain.model
 * @description: Graph
 * @data: 2019-12-02 21:01
 **/
@Data
public class Graph {
    @Id
    private String graphId;
    private String username;
    private String inputFile;
    private JSONObject model;
    public Graph(String graphId,String inputFile,String username, JSONObject model){
        this.graphId=graphId;
        this.inputFile=inputFile;
        this.model=model;
        this.username=username;
    }
}
