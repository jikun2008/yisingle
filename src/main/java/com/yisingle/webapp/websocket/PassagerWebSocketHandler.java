package com.yisingle.webapp.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jikun on 17/7/21.
 */
public class PassagerWebSocketHandler implements WebSocketHandler {

    private Map<String, WebSocketSession> passagerMap = new HashMap<String, WebSocketSession>();

    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    public boolean supportsPartialMessages() {
        return false;
    }
}
