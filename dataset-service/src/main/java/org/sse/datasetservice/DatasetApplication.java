package org.sse.datasetservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author ZTL
 */
@SpringCloudApplication

@EnableResourceServer
@EnableOAuth2Client
@EnableSwagger2Doc
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DatasetApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasetApplication.class, args);
    }

}
