package org.sse.modelservice.domain.nodeconfig;

import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.Tokenizer;


/**
 * @version: V1.0
 * @author: cyy
 * @className: TokenizerNodeConfig
 * @packageName: com.mlab.org.sse.trainservice.domain.nodeconfig
 * @description: Tokenizer
 * @data: 2019/12/2 下午12:42
 **/
public class TokenizerNodeConfig extends NodeConfig {
    private String inputCol;
    private String outputCol;

    public TokenizerNodeConfig(String inputCol, String outputCol) {
        super();
        this.inputCol = inputCol;
        this.outputCol = outputCol;
    }

    @Override
    public PipelineStage getPipelineStage() {
        return new Tokenizer().setInputCol(inputCol).setOutputCol(outputCol);
    }
}
