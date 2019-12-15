package org.sse.trainservice.domain;

import lombok.Data;

/**
 * @version: 1.0
 * @author: usr
 * @className: Task
 * @packageName: org.sse.trainservice.domain
 * @description: task
 * @data: 2019-12-15 13:51
 **/
@Data
public class Task {

    private long trainId;
    private long runType;
    private String username;
    private long pipelineId;
    private long modelId;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    private long status;

    public Task(long runType,String username,long pipelineId,long modelId){
        this.runType=runType;
        this.username=username;
        this.pipelineId=pipelineId;
        this.modelId=modelId;
    }
}
