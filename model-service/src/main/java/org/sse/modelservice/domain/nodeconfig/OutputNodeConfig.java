package org.sse.modelservice.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;

/**
 * @version: 1.0
 * @author: usr
 * @className: OutputNodeConfig
 * @packageName: org.sse.modelservice.domain.nodeconfig
 * @description: outputNode
 * @data: 2019-12-16 02:47
 **/
public class OutputNodeConfig extends NodeConfig {
    public OutputNodeConfig(){
        super();
        this.setType("Output");
    }
    @Override
    public PipelineStage getPipelineStage() {
        return null;
    }
}
