package org.sse.modelservice.domain.nodeconfig;

import lombok.Data;
import org.apache.spark.ml.PipelineStage;

/**
 * @version: 1.0
 * @author: usr
 * @className: InputNodeConfig
 * @packageName: org.sse.modelservice.domain.nodeconfig
 * @description: inputnode
 * @data: 2019-12-10 13:31
 **/
@Data
public class InputNodeConfig extends NodeConfig {
    private String fileName;

    public InputNodeConfig(String fileName){
        super();
        this.fileName=fileName;
        this.setType("Input");
    }

    @Override
    public PipelineStage getPipelineStage() {
        return null;
    }
}
