package org.sse.modelservice.domain.model;

import lombok.Data;

/**
 * @version: 1.0
 * @author: usr
 * @className: Pipline
 * @packageName: org.sse.modelservice.domain.model
 * @description: pipline information
 * @data: 2019-12-09 12:55
 **/
@Data
public class PipelineInformation {
    private long pipelineId;
    private String username;
    private String pipelineName;
    private String description;
    private java.sql.Timestamp createTime;
    private long inputFile;
    private long status;

    public  PipelineInformation(String username,String pipelineName,String description){
        this.username=username;
        this.pipelineName=pipelineName;
        this.description=description;
    }
}
