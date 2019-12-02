package com.mlab.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Configuartion
 * @packageName: com.mlab.lab.entity
 * @description: configuration for node
 * @data: 2019-11-23 7:32
 **/
public abstract class NodeConfig {
    private String type;

    public String getType() {
        return type;
    }

    abstract public PipelineStage getPipelineStage();
}
