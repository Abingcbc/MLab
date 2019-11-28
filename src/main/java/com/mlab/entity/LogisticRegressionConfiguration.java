package com.mlab.entity;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;

public class LogisticRegressionConfiguration extends Configuartion{

    public LogisticRegressionConfiguration(int maxIter, double param){
        super();
        this.maxIter=maxIter;
        this.param=param;
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new LogisticRegression().setMaxIter(maxIter).setRegParam(param);
    }

    private int maxIter;
    private double param;
}
