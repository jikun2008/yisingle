package com.yisingle.webapp.job;

import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.service.OrderService;
import com.yisingle.webapp.websocket.SystemWebSocketHandler;
import org.apache.commons.logging.impl.SimpleLog;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

/**
 * Created by jikun on 17/6/26.
 */
public class OrderJob implements Job {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    private SimpleLog simpleLog = new SimpleLog(OrderJob.class.getSimpleName());

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);//可以自动注入

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();


        simpleLog.info("orderJob 工作中");
        OrderEntity orderEntity = orderService.changeOrderWaitNewStateToWatiOldState();

        if (null != orderEntity && null != orderEntity.getDriverEntity()) {

            simpleLog.info("发送全新的订单给司机--司机号码:" + orderEntity.getDriverEntity().getPhonenum());
            systemWebSocketHandler.sendOrderToDriver(orderEntity.getDriverEntity().getId() + "", orderEntity);
        }


        List<OrderEntity> orderEntityList = orderService.checkWaitOldOrder();
        for (OrderEntity oldOrderEntity : orderEntityList) {
            if (null != oldOrderEntity.getDriverEntity()) {

                long time = System.currentTimeMillis() - oldOrderEntity.getDriverRelyTime();

                simpleLog.info("orderJob time=" + time);
                if (time >= 1* 60 * 1000) {
                    //如果大于两分钟那么就发送订单
                    simpleLog.info("orderJob id=" + oldOrderEntity.getDriverEntity().getId());
                    simpleLog.info("orderJob 号码=" + oldOrderEntity.getDriverEntity().getPhonenum());
                    systemWebSocketHandler.sendOrderToDriver(oldOrderEntity.getDriverEntity().getId() + "", oldOrderEntity);
                }


            }

        }

    }
}
