package org.sse.communityservice.websocket;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HPY
 */
@Component
@ServerEndpoint("/socket/{sid}")
public class WebSocketServer {

    static Log log = LogFactory.getLog(WebSocketServer.class);

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String,WebSocketServer> webSocketsMap = new ConcurrentHashMap<>();

    private Session session;
    private String sid="";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketsMap.put(sid,this);
        addOnlineCount();
        this.sid = sid;
        try {
            sendMessage("Connect successfully!");
        } catch (IOException e) {

        }
    }

    @OnClose
    public void onClose() {
        if (webSocketsMap.get(this.sid)!=null) {
            webSocketsMap.remove(this.sid);
            subOnlineCount();
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static WebSocketServer getInstance(String sid)
    {
        return WebSocketServer.webSocketsMap.get(sid);
    }

}
