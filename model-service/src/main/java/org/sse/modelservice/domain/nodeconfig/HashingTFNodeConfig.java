package org.sse.modelservice.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.HashingTF;

/**
 * @version: V1.0
 * @author: cyy
 * @className: HashingTFNodeConfig
 * @packageName: com.mlab.org.sse.trainservice.domain.nodeconfig
 * @description: HashTF
 * @data: 2019/12/2 下午12:39
 **/
public class HashingTFNodeConfig extends NodeConfig {
    private String inputCol;
    private String outputCol;
    private Boolean binary;
    private Integer numFeatures;

    public HashingTFNodeConfig(String inputCol, String outputCol,int numFeatures) {
        super();
        this.inputCol = inputCol;
        this.outputCol = outputCol;
        this.numFeatures=numFeatures;
        this.setType("HashingTF");
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new HashingTF().setInputCol(inputCol).setOutputCol(outputCol).setNumFeatures(numFeatures);
    }
}
