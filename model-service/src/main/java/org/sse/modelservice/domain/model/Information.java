package org.sse.modelservice.domain.model;

import lombok.Data;

/**
 * @version: 1.0
 * @author: usr
 * @className: Information
 * @packageName: org.sse.modelservice.domain.model
 * @description: model information
 * @data: 2019-12-09 12:31
 **/
@Data
public class Information {
    private long modelId;
    private String username;
    private String pipelineName;
    private String description;
    private java.sql.Timestamp createTime;
    private long status;

    public Information(String username, String pipielineName, String description){
        this.pipelineName=pipelineName;
        this.username=username;
        this.description=description;
    }
}
