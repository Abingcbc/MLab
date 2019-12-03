package org.sse.modelservice.websocket;

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
 * @version: 1.0
 * @author: usr
 * @className: WebSocketSever
 * @packageName: com.mlab.websocket
 * @description: websocket sever
 * @data: 2019-12-02 14:11
 **/
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketSever {
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String,WebSocketSever> websocketList = new ConcurrentHashMap<>();
    private String userId="";
    public Session session;

    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId,this);
        addOnlineCount();           //在线数加1
        this.userId=userId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
        }
    }

    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            subOnlineCount();           //在线数减1
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
        WebSocketSever.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketSever.onlineCount--;
    }

    public static WebSocketSever get(Integer userID){return WebSocketSever.websocketList.get(userID.toString());}

}
