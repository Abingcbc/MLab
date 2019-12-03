package com.mlab.domain;

import org.springframework.stereotype.Component;

@Component
public class ResultPool {
    private Result[] pool;
    public ResultPool(){
        this.pool=new Result[3];
        this.pool[0]=new Result();
        this.pool[1]=new Result();
        this.pool[2]=new Result();
    }
    public Result get(int index){
        return pool[index];
    }
    public Boolean isFull(){
        boolean flag=true;
        for(Result r: pool){
            if(r.getStatus()==TrainStatus.READY){
                flag=false;
            }
        }
        return flag;
    }
    public Result getEmptySite(){
        for(Result r: pool){
            if(r.getStatus()==TrainStatus.READY){
                return r;
            }
        }
        return null;
    }
}
