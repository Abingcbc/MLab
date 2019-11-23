package org.sse.dataservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cbc
 */
@Configuration
public class HdfsConfig {

    @Value("${hdfs.path}")
    String hdfsPath;

    @Bean
    public org.apache.hadoop.conf.Configuration getHdfsConfig() {
        org.apache.hadoop.conf.Configuration configuration =
                new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", hdfsPath);
        return configuration;
    }
}
