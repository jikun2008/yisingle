/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yisingle.webapp.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.yisingle.webapp.data.HeartBeatData;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.SocketData;
import com.yisingle.webapp.data.SocketHeadData;

import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.job.OrderJob;
import com.yisingle.webapp.job.PassengerOrderJob;
import com.yisingle.webapp.job.QuartzManager;
import com.yisingle.webapp.service.DriverService;
import com.yisingle.webapp.service.OrderCoordinateService;
import com.yisingle.webapp.service.OrderService;

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
import java.util.Properties;
;

/**
 * @author jikun
 */
@Component
public class SystemWebSocketHandler extends BaseWebSocketHandler implements WebSocketHandler, ApplicationListener {


    @Autowired
    private DriverService driverService;


    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderCoordinateService orderCoordinateService;

    private SimpleLog simpleLog = new SimpleLog(SystemWebSocketHandler.class.getSimpleName());
    ObjectMapper mapper = new ObjectMapper();

    private Map<String, WebSocketSession> driverMap = new HashMap<String, WebSocketSession>();

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String driverId = session.getHandshakeHeaders().getFirst("driverId");
        if (null != driverId && !driverId.equals("")) {
            driverMap.put(driverId, session);
        }

    }


    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if (wss.isOpen()) {
            wss.close();
        }

    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus cs) throws Exception {


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
    public void collatingData(String jsonData, WebSocketSession session) {
        try {


            SocketHeadData headData = mapper.readValue(jsonData, SocketHeadData.class);

            if (headData.getType() == SocketHeadData.Type.HEART_BEAT.value()) {
                SocketData<HeartBeatData> socketData = mapper.readValue(jsonData, new TypeReference<SocketData<HeartBeatData>>() {
                });
                socketData.setMsg("接受心跳数据");

                String json = mapper.writeValueAsString(socketData);
                sendMsg(session, json);
                driverService.saveLocationPointToDriver(socketData.getResponse());

                //如果现在司机当前有订单，对订单的坐标进行保存
                orderCoordinateService.saveOrderDriverLatLongToOrder(socketData.getResponse());


            } else if (headData.getType() == SocketHeadData.Type.ORDER_NEW.value()) {

                SocketData<OrderDetailData> socketData = mapper.readValue(jsonData, new TypeReference<SocketData<OrderDetailData>>() {
                });

                if (null != socketData && null != socketData.getResponse()) {
                    orderService.saveRelyTimeToOrder(socketData.getResponse().getId());
                }


            } else {
                simpleLog.info("type值未知");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendOrderToDriver(String id, OrderEntity orderEntity) {
        try {
            WebSocketSession socketSession = driverMap.get(id);
            if (null != socketSession) {
                OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
                SocketData<OrderDetailData> socketData = new SocketData<OrderDetailData>();
                socketData.setMsg("新订单");
                socketData.setCode(0);
                socketData.setType(SocketHeadData.Type.ORDER_NEW.value());
                socketData.setResponse(orderDetailData);
                String json = mapper.writeValueAsString(socketData);

                sendMsg(socketSession, json);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public void sendPrcieToDriver(String id, OrderEntity orderEntity) {
        try {
            WebSocketSession socketSession = driverMap.get(id);
            if (null != socketSession) {

                OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
                SocketData<OrderDetailData> socketData = new SocketData<OrderDetailData>();
                socketData.setMsg("价格订单发送");
                socketData.setCode(0);
                socketData.setType(SocketHeadData.Type.ORDER_PRICE.value());
                socketData.setResponse(orderDetailData);

                String json = mapper.writeValueAsString(socketData);
                sendMsg(socketSession, json);

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void onApplicationEvent(ApplicationEvent event) {


        try {
            startOrderJob();
            startPassengerOrderJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void startOrderJob() throws SchedulerException {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();

        Scheduler scheduler = getScheduler("orderJob");
        QuartzManager quartzManager = new QuartzManager(scheduler);

        if (!scheduler.isStarted()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1);
            // 每1000毫秒执行一次，重复执行
            quartzManager.addJob("myJob", "test", OrderJob.class, map, 2000l, -1);
            quartzManager.startScheduler();
        }

    }

    private void startPassengerOrderJob() throws SchedulerException {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler("passengerOrderJob");

        Scheduler scheduler = getScheduler("passengerOrderJob");
        QuartzManager quartzManager = new QuartzManager(scheduler);


        if (!scheduler.isStarted()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 2);
            // 每10毫秒执行一次，重复执行
            quartzManager.addJob("passengerJob", "passenger", PassengerOrderJob.class, map, 10000l, -1);
            quartzManager.startScheduler();
        }
    }

    private Scheduler getScheduler(String name) throws SchedulerException {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", name);
        props.put("org.quartz.threadPool.threadCount", "10");
        sf.initialize(props);
        Scheduler scheduler = sf.getScheduler();

        return scheduler;
    }
}
