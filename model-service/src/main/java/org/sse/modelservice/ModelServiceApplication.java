package org.sse.modelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
 **/

/**
 * @title: LabApplication
 * @package com.mlab.lab
 * @description:
 * @author: cyy
 * @date: 2019-11-19 11:55
 * @version: V1.0
 */

//@SpringCloudApplication
@SpringBootApplication
@EnableAsync
//@EnableOAuth2Client
//@EnableFeignClients
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class ModelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelServiceApplication.class, args);
    }

}
