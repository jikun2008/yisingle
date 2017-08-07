package com.yisingle.webapp.websocket;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.SocketData;
import com.yisingle.webapp.data.SocketHeadData;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.service.OrderService;
import org.apache.commons.logging.impl.SimpleLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jikun on 17/7/21.
 */
@Component
public class PassagerWebSocketHandler extends BaseWebSocketHandler implements WebSocketHandler {

    private Map<String, WebSocketSession> passagerMap = new HashMap<String, WebSocketSession>();


    private SimpleLog simpleLog = new SimpleLog(PassagerWebSocketHandler.class.getSimpleName());


    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private OrderService orderService;


    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {


        String passengerID = webSocketSession.getHandshakeHeaders().getFirst("passengerId");
        if (null != passengerID && !passengerID.equals("")) {
            passagerMap.put(passengerID, webSocketSession);
        }
    }


    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        String passengerId = webSocketSession.getHandshakeHeaders().getFirst("passengerId");
        if (null != passengerId && !passengerId.equals("")) {
            passagerMap.remove(passengerId);
        }
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    public void collatingData(String jsonData, WebSocketSession wss) {
        try {


            SocketHeadData headData = mapper.readValue(jsonData, SocketHeadData.class);


            if (headData.getType() == SocketHeadData.Type.PASSENGER_ORDER_STATES_CHANGE.value()) {
                SocketData<OrderDetailData> socketData = mapper.readValue(jsonData, new TypeReference<SocketData<OrderDetailData>>() {
                });

                if (null != socketData && null != socketData.getResponse()) {
                    orderService.savePassengerRelyStateToOrder(socketData.getResponse().getId());
                }
                //312313 socketData.getResponse().getId();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOrderToPassenger(String id, OrderEntity orderEntity,int headDataType) {
        WebSocketSession socketSession = passagerMap.get(id);
        simpleLog.info("orderJob sendPrcieTPassenger=socketSession---==" + socketSession);
        if (null != socketSession) {
            try {
                OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
                orderDetailData.setUser(orderEntity.getUserEntity());
                orderDetailData.setDriver(orderEntity.getDriverEntity());
                SocketData<OrderDetailData> socketData = new SocketData<OrderDetailData>();
                socketData.setMsg("订单改变状态");
                socketData.setCode(0);
                socketData.setType(headDataType);
                socketData.setResponse(orderDetailData);


                ObjectMapper mapper = new ObjectMapper();

                String json = mapper.writeValueAsString(socketData);

                sendMsg(socketSession, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
