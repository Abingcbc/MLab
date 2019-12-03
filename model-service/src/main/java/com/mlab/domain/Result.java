package com.mlab.domain;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Result
 * @packageName: com.mlab.domain
 * @description: Result of training
 * @data: 2019/12/2 上午11:15
 **/
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
    public String getResult(){
        return result;
    }
    public TrainStatus getStatus(){
        return status;
    }
    public void setStatus(TrainStatus s){
        status=s;
    }
    public void setID(int id){this.id=id;}
}
