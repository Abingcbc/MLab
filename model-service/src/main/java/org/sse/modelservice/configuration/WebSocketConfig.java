package org.sse.modelservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @version: 1.0
 * @author: usr
 * @className: WebSocketConfig
 * @packageName: com.mlab.configuration
 * @description: websocket configuration
 * @data: 2019-12-02 14:09
 **/
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

