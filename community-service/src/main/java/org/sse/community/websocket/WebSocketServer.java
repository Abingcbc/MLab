package org.sse.community.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * @author HPY
 */
@Component
@ServerEndpoint("/socket/{username}")
public class WebSocketServer {

}
