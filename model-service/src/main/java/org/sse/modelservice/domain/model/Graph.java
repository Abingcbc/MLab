package org.sse.modelservice.domain.model;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import org.bson.BsonArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int id;
    private JSONObject model;
    public Graph(int id, JSONObject object){
        this.id=id;
        this.model=object;
    }
}
