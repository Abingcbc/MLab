package org.sse.trainservice.domain;

import lombok.Data;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Result
 * @packageName: com.mlab.org.sse.trainservice.domain
 * @description: Result of training
 * @data: 2019/12/2 上午11:15
 **/
@Data
public class Result {
    private Integer id;
    private String result;
    private TrainStatus status;

    public Result(){
        this.id=null;
        this.result=null;
        this.status=TrainStatus.READY;
    }
    public Result(String r, int i){
        this.id=i;
        this.result=r;
        this.status=TrainStatus.TRAINING;
    }
}
