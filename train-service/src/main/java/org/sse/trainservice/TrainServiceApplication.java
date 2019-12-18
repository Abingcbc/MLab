package org.sse.trainservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringCloudApplication
@EnableAsync
@EnableOAuth2Client
@EnableFeignClients
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableResourceServer
@EnableSwagger2Doc
public class TrainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainServiceApplication.class, args);
    }

}
