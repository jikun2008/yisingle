package com.yisingle.webapp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yisingle.webapp.data.SocketData;
import com.yisingle.webapp.data.SocketHeadData;
import com.yisingle.webapp.utils.JsonUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by jikun on 17/8/1.
 */
public abstract class BaseWebSocketHandler implements WebSocketHandler {

    private SimpleLog simpleLog = new SimpleLog(BaseWebSocketHandler.class.getSimpleName());


    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) {

        checkHandleMsg(wss, wsm);

    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }


    public abstract void collatingData(String jsonData, WebSocketSession wss);

    private void checkHandleMsg(WebSocketSession wss, WebSocketMessage<?> wsm) {
        TextMessage returnMessage = new TextMessage(wsm.getPayload() + "");

        String jsonData = returnMessage.getPayload();
        String errorMsg = "";



        try {
            if (JsonUtils.isGoodJson(returnMessage.getPayload())) {

                collatingData(jsonData, wss);
            } else {
                errorMsg = "传递数据不是json格式";
                sendInvalidData(wss, errorMsg);
            }
        } catch (Exception e) {
            simpleLog.info("" + e.toString());
        }
    }

    protected void sendInvalidData(WebSocketSession session, String errorMsg) throws IOException {
        SocketHeadData data = new SocketHeadData();
        data.setType(SocketHeadData.Type.ILLEGAL_DATA.value());
        data.setCode(-1);
        data.setMsg(errorMsg);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        sendMsg(session, json);

    }


    protected void sendMsg(WebSocketSession session, String info) {
        try {
            simpleLog.info("websocket服务器发送的数据=" + info);
            TextMessage msg = new TextMessage(info);

            session.sendMessage(msg);
        } catch (IOException e) {
            simpleLog.info("websocket服务器发送的数据报错");
        }

    }
}
