package com.yisingle.webapp.job;

import com.yisingle.webapp.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by jikun on 17/8/7.
 */
public class PassengerOrderJob implements Job {
    @Autowired
    private OrderService orderService;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);//可以自动注入

        sendPriceToPassenger();
    }

    private void sendPriceToPassenger() {
        orderService.sendPriceOrderToPassenger();

    }
}
