package com.yisingle.webapp.job;

import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.service.OrderService;
import com.yisingle.webapp.websocket.SystemWebSocketHandler;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by jikun on 17/6/26.
 */
public class OrderJob implements Job {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);//可以自动注入

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();


        System.out.println("工作中");
        OrderEntity orderEntity = orderService.changeOrderWaitNewStateToWatiOldState();

        if (null != orderEntity && null != orderEntity.getDriverEntity()) {

            systemWebSocketHandler.sendOrderToDriver(orderEntity.getDriverEntity().getId() + "", orderEntity);
        }

    }
}
