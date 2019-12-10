package org.sse.trainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.cloud.client.SpringCloudApplication;

//@SpringCloudApplication
@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class TrainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainServiceApplication.class, args);
    }

}
