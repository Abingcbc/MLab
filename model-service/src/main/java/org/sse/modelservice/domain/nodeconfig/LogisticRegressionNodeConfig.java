package org.sse.modelservice.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;

/**
 * @version: V1.0
 * @author: cyy
 * @className: LogisticRegressionNodeConfig
 * @packageName: com.mlab.org.sse.trainservice.domain.nodeconfig
 * @description: LogisticRegression
 * @data: 2019/12/2 下午12:42
 **/
public class LogisticRegressionNodeConfig extends NodeConfig {

    private int aggregationDepth;
    private double elasticNetParam;
    private double tol;
    private int maxIter;
    private double param;

    public LogisticRegressionNodeConfig(int maxIter, double param) {
        super();
        this.maxIter = maxIter;
        this.param = param;
        this.setType("Logistic");
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new LogisticRegression().setMaxIter(maxIter).setRegParam(param);
    }


}

