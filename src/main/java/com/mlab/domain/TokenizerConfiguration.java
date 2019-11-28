package com.mlab.domain;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.Tokenizer;

public class TokenizerConfiguration extends Configuartion {
    private String inputCol;
    private String outputCol;

    public TokenizerConfiguration(String inputCol, String outputCol) {
        super();
        this.inputCol = inputCol;
        this.outputCol = outputCol;
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new Tokenizer().setInputCol(inputCol).setOutputCol(outputCol);
    }
}
