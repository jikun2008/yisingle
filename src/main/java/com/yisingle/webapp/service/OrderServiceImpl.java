package com.yisingle.webapp.service;

import com.sun.tools.corba.se.idl.constExpr.Or;
import com.yisingle.webapp.dao.DriverDao;
import com.yisingle.webapp.dao.OrderDao;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.DriverEntity;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.UserEntity;

import com.yisingle.webapp.job.OrderJob;
import com.yisingle.webapp.job.QuartzManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;

import java.util.List;
import java.util.Map;


import com.yisingle.webapp.entity.OrderEntity.OrderState.State;

/**
 * Created by jikun on 17/6/25.
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DriverDao driverDao;


    public ResponseData<OrderEntity> generateWaitOrder(OrderRequestData requestData, UserEntity userEntity) {

        ResponseData responseData = new ResponseData();


        List<OrderEntity> list = orderDao.findWaitStateAndUserId(new Integer[]{State.WATI_NEW.value(), State.WATI_OLD.value(), State.HAVE_TAKE.value()}, userEntity.getId());

        if (null != list && list.size() > 0) {

            responseData.setCode(ResponseData.Code.SUCCESS.value());
            OrderEntity orderEntity = list.get(0);

            responseData.setResponse(orderEntity);
            responseData.setErrorMsg("你有未完成的订单请先完成该订单");

            if (orderEntity.getOrderState() == State.WATI_NEW.value()) {
                orderEntity.setOrderState(State.WATI_OLD.value());
            }
            orderDao.save(orderEntity);


        } else {
            OrderEntity entity = new OrderEntity();
            entity.setPhoneNum(requestData.getPhoneNum());
            entity.setStartPlaceName(requestData.getStartPlaceName());
            entity.setEndPlaceName(requestData.getEndPlaceName());
            entity.setStartLongitude(requestData.getStartLongitude());
            entity.setStartLatitude(requestData.getStartLatitude());
            entity.setEndLongitude(requestData.getEndLongitude());
            entity.setEndLatitude(requestData.getEndLatitude());
            entity.setOrderState(OrderEntity.OrderState.State.WATI_NEW.value());
            entity.setUserEntity(userEntity);

            orderDao.save(entity);
            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(entity);
            responseData.setErrorMsg("");


        }
        try {
            startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return responseData;


    }


    private void startJob() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        QuartzManager quartzManager = new QuartzManager(scheduler);

        if (!scheduler.isStarted()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1);
            // 每1000毫秒执行一次，重复执行
            quartzManager.addJob("myJob", "test", OrderJob.class, map, 6000l, -1);
            quartzManager.startScheduler();
        }

    }

    public void changeOrderState() {

        List<OrderEntity> orderEntityList = orderDao.findWaitState();

        if (null == orderEntityList) {
            return;
        }

        if (orderEntityList.size() > 0) {
            OrderEntity orderEntity = orderEntityList.get(0);
            List<DriverEntity> driverEntityList = driverDao.findDriverByState(DriverEntity.DriverState.State.WATI_FOR_ORDER.value());
            if (null != driverEntityList && driverEntityList.size() > 0) {
                orderEntity.setOrderState(State.HAVE_TAKE.value());
                orderEntity.setDriverEntity(driverEntityList.get(0));
            }
            orderDao.update(orderEntity);

        }

    }

    public ResponseData checkOrderState() {

        ResponseData responseData = new ResponseData();
        return null;
    }


}
