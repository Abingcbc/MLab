package org.sse.modelservice;

//import com.spring4all.swagger.EnableSwagger2Doc;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * @title: LabApplication
 * @package com.mlab.lab
 * @description:
 * @author: cyy
 * @date: 2019-11-19 11:55
 * @version: V1.0
 */

@SpringCloudApplication
@EnableAsync
@EnableOAuth2Client
@EnableFeignClients
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableResourceServer
@EnableSwagger2Doc
public class ModelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelServiceApplication.class, args);
    }

}
