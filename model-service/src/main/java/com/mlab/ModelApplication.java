package com.mlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @title: LabApplication
 * @package com.mlab.lab
 * @description:
 * @author: cyy
 * @date: 2019-11-19 11:55
 * @version: V1.0
 */

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class ModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class, args);
    }

}
