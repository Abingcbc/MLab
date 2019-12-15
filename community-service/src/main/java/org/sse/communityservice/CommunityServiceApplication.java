package org.sse.communityservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

/**
 * @author HPY
 */
@SpringCloudApplication
@EnableTransactionManagement
@EnableResourceServer
@EnableOAuth2Client
@EnableFeignClients
@EnableSwagger2Doc
public class CommunityServiceApplication {

    public static void main(String[] args) {
        System.out.println(TimeZone.getDefault().getID());
        SpringApplication.run(CommunityServiceApplication.class, args);
    }

}
