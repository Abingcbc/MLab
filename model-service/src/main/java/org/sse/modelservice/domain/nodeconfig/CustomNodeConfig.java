package org.sse.modelservice.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.Transformer;

/**
 * @version: 1.0
 * @author: usr
 * @className: CustomNodeConfig
 * @packageName: org.sse.modelservice.domain.nodeconfig
 * @description: configuration for custom node
 * @data: 2019-12-10 18:41
 **/
public class CustomNodeConfig extends NodeConfig {

    private String filePath;

    public CustomNodeConfig(String filePath){
        this.filePath=filePath;
    }

    @Override
    public PipelineStage getPipelineStage() {
        return transformer(filePath);
    }

    private Transformer transformer(String filePath){
        return null;
    }
}
