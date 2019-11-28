package com.mlab.domain;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.HashingTF;

public class HashingTFConfiguration extends Configuartion {
    private String inputCol;
    private String outputCol;

    public HashingTFConfiguration(String inputCol, String outputCol) {
        super();
        this.inputCol = inputCol;
        this.outputCol = outputCol;
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new HashingTF().setInputCol(inputCol).setOutputCol(outputCol);
    }
}
