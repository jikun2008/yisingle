/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yisingle.webapp.websocket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yisingle.webapp.dao.DriverDao;
import com.yisingle.webapp.data.HeartBeatData;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.SocketData;
import com.yisingle.webapp.data.SocketHeadData;
import com.yisingle.webapp.entity.DriverEntity;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.job.OrderJob;
import com.yisingle.webapp.job.QuartzManager;
import com.yisingle.webapp.service.DriverService;
import com.yisingle.webapp.service.OrderCoordinateService;
import com.yisingle.webapp.service.OrderService;
import com.yisingle.webapp.utils.JsonUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
;

/**
 * @author jikun
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler, ApplicationListener {


    @Autowired
    private DriverService driverService;


    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderCoordinateService orderCoordinateService;

    private SimpleLog simpleLog = new SimpleLog(SystemWebSocketHandler.class.getSimpleName());
    private Gson gson = new Gson();


    private Map<String, WebSocketSession> driverMap = new HashMap<String, WebSocketSession>();

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String driverId = session.getHandshakeHeaders().getFirst("driverId");
        if (null != driverId && !driverId.equals("")) {
            driverMap.put(driverId, session);
        }

    }

    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) {

        TextMessage returnMessage = new TextMessage(wsm.getPayload() + "");

        String jsonData = returnMessage.getPayload();
        String errorMsg = "";

        simpleLog.info("websocket服务器接收到数据=" + returnMessage.getPayload());

        try {
            if (JsonUtils.isGoodJson(returnMessage.getPayload())) {

                collatingData(jsonData, wss);
            } else {
                simpleLog.info("数据不合法");
                errorMsg = "传递数据不是json格式";
                sendInvalidData(wss, errorMsg);
            }
        } catch (Exception e) {
            simpleLog.info("" + e.toString());
        }


    }

    private void sendMsg(WebSocketSession session, String info) throws IOException {
        simpleLog.info("websocket服务器发送的数据=" + info);
        TextMessage msg = new TextMessage(info);

        session.sendMessage(msg);
    }


    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if (wss.isOpen()) {
            wss.close();
        }
        System.out.println("websocket connection closed......");
    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus cs) throws Exception {
        System.out.println("websocket connection closed......");

        String driverId = session.getHandshakeHeaders().getFirst("driverId");
        if (null != driverId && !driverId.equals("")) {
            driverMap.remove(driverId);
        }

    }


    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 整理数据
     */
    private void collatingData(String jsonData, WebSocketSession session) throws IOException {
        SocketHeadData headData = gson.fromJson(jsonData, SocketHeadData.class);
        if (headData.getType() == SocketData.Type.HEART_BEAT.value()) {
            SocketData<HeartBeatData> socketData = gson.fromJson(jsonData, new TypeToken<SocketData<HeartBeatData>>() {
            }.getType());
            socketData.setMsg("接受心跳数据");

            String json = new Gson().toJson(socketData);
            sendMsg(session, json);
            driverService.saveLocationPointToDriver(socketData.getResponse());

            //如果现在司机当前有订单，对订单的坐标进行保存
            orderCoordinateService.saveOrderDriverLatLongToOrder(socketData.getResponse());


        } else if (headData.getType() == SocketData.Type.ORDER_NEW.value()) {

            SocketData<OrderDetailData> socketData = gson.fromJson(jsonData, new TypeToken<SocketData<OrderDetailData>>() {
            }.getType());

            if (null != socketData && null != socketData.getResponse()) {
                orderService.saveRelyTimeToOrder(socketData.getResponse().getId());
            }


        } else {
            simpleLog.info("type值未知");
        }
    }


    private void sendInvalidData(WebSocketSession session, String errorMsg) throws IOException {
        SocketHeadData data = new SocketHeadData();
        data.setType(SocketData.Type.ILLEGAL_DATA.value());
        data.setCode(-1);
        data.setMsg(errorMsg);

        String json = new Gson().toJson(data);

        sendMsg(session, json);

    }


    public void sendOrderToDriver(String id, OrderEntity orderEntity) {
        try {

            WebSocketSession socketSession = driverMap.get(id);
            simpleLog.info("orderJob sendOrderToDriver=socketSession---==" + socketSession);
            if (null != socketSession) {

                OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
                SocketData<OrderDetailData> socketData = new SocketData<OrderDetailData>();
                socketData.setMsg("新订单");
                socketData.setCode(0);
                socketData.setType(SocketData.Type.ORDER_NEW.value());
                socketData.setResponse(orderDetailData);

                String json = new Gson().toJson(socketData);
                sendMsg(socketSession, json);

            }
        } catch (IOException e) {
            simpleLog.info("新订单发送报错sendOrderToDriver()" + e.toString());
            e.printStackTrace();
        }
    }


    public void sendPrcieToDriver(String id, OrderEntity orderEntity) {
        try {

            WebSocketSession socketSession = driverMap.get(id);
            simpleLog.info("orderJob sendOrderToDriver=socketSession---==" + socketSession);
            if (null != socketSession) {

                OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
                SocketData<OrderDetailData> socketData = new SocketData<OrderDetailData>();
                socketData.setMsg("价格订单发送");
                socketData.setCode(0);
                socketData.setType(SocketData.Type.ORDER_PRICE.value());
                socketData.setResponse(orderDetailData);

                String json = new Gson().toJson(socketData);
                sendMsg(socketSession, json);

            }
        } catch (IOException e) {
            simpleLog.info("价格订单发送报错sendOrderToDriver()" + e.toString());
            e.printStackTrace();
        }
    }

    public void onApplicationEvent(ApplicationEvent event) {

        simpleLog.info("onApplicationEvent Call Back");
        try {
            startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void startJob() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        QuartzManager quartzManager = new QuartzManager(scheduler);
        simpleLog.info("startJob()");

        if (!scheduler.isStarted()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1);
            // 每1000毫秒执行一次，重复执行
            quartzManager.addJob("myJob", "test", OrderJob.class, map, 2000l, -1);
            quartzManager.startScheduler();
        }

    }
}
