package com.yisingle.webapp.job;

import com.yisingle.webapp.service.OrderService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by jikun on 17/6/26.
 */
public class OrderJob implements Job {

    @Autowired
    private OrderService orderService;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);//可以自动注入

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();


        System.out.println("工作中");
        orderService.changeOrderState();

    }
}
