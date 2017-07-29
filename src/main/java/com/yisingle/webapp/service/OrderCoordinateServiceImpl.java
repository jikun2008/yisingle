package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.DriverDao;
import com.yisingle.webapp.dao.OrderCoordinateDao;
import com.yisingle.webapp.dao.OrderDao;
import com.yisingle.webapp.data.HeartBeatData;
import com.yisingle.webapp.entity.DriverEntity;
import com.yisingle.webapp.entity.OrderCoordinateEntity;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.OrderEntity.OrderState.State;

import com.yisingle.webapp.utils.DistanceUtils;
import com.yisingle.webapp.utils.MoneyCalculateUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yisingle.webapp.entity.OrderCoordinateEntity.CalculateType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jikun on 17/7/28.
 */

@Service("OrderCoordinateService")
@Transactional
public class OrderCoordinateServiceImpl implements OrderCoordinateService {

    @Autowired
    private OrderCoordinateDao orderCoordinateDao;


    @Autowired
    private OrderDao orderDao;

    private SimpleLog simpleLog = new SimpleLog(OrderCoordinateServiceImpl.class.getSimpleName());

    public void saveOrderDriverLatLongToOrder(HeartBeatData data) {


        boolean issave = !(data.getLongitude().equals("") && data.getLatitude().equals(""));
        if (issave) {
            List<OrderEntity> orderEntityList = orderDao.findOrderByDriverIdAndState(new Integer[]{State.DRIVER_ARRIVE.value(), State.PASSENGER_IN_CAR.value(), State.PASSENGER_OUT_CAR.value()}, data.getId() + "");
            if (null != orderEntityList && orderEntityList.size() > 0) {

                OrderEntity orderEntity = orderEntityList.get(0);
                OrderCoordinateEntity orderCoordinateEntity = new OrderCoordinateEntity();
                orderCoordinateEntity.setOrderEntity(orderEntity);
                orderCoordinateEntity.setLatitude(data.getLatitude());
                orderCoordinateEntity.setLongitude(data.getLongitude());
                orderCoordinateEntity.setTime(System.currentTimeMillis());
                orderCoordinateEntity.setCalculateType(CalculateType.NOT_CALCULATE.value());
                orderCoordinateDao.save(orderCoordinateEntity);
            }
        }


    }

    public void calculateCostMoney() {


        simpleLog.info("calculateCostMoney");
        List<OrderEntity> orderEntityList = orderDao.findOrderByState(new Integer[]{State.HAVE_TAKE.value(), State.DRIVER_ARRIVE.value(), State.PASSENGER_IN_CAR.value(), State.PASSENGER_OUT_CAR.value()});


        for (OrderEntity orderEntity : orderEntityList) {

            //simpleLog.info("calculateCostMoney--Id"+orderEntity.getId());
            String orderId = orderEntity.getId() + "";
            List<OrderCoordinateEntity> orderCoordinateEntityList = orderCoordinateDao.findByOrderIdAndCalculateType(orderId, new Integer[]{CalculateType.NOT_CALCULATE.value()});

            OrderCoordinateEntity cuurentEntity = null;


            float allDistance = 0;//米
            long time = 0;
            if (null != orderCoordinateEntityList && orderCoordinateEntityList.size() > 0) {
                //simpleLog.info("calculateCostMoney--orderCoordinateEntityList");

                for (int i = 0; i < orderCoordinateEntityList.size(); i++) {


                    if (i - 1 >= 0) {
                        OrderCoordinateEntity pre = orderCoordinateEntityList.get(i - 1);
                        OrderCoordinateEntity now = orderCoordinateEntityList.get(i);
                        float dista = DistanceUtils.calculateLineDistance(pre.getLatitude(), pre.getLongitude(), now.getLatitude(), now.getLongitude());
                        allDistance = allDistance + dista;
                        long twoTime = now.getTime() - pre.getTime();
                        time = time + twoTime;

                        pre.setCalculateType(CalculateType.IS_CALCULATE.value());
                        orderCoordinateDao.save(pre);

                        // simpleLog.info("calculateCostMoney-- time=" + time);

                        //simpleLog.info("calculateCostMoney-- allDistance=" + allDistance);
                    }

                }

            }

            // simpleLog.info("calculateCostMoney--time="+time+"---allDistance=="+allDistance);
            if (orderEntity != null) {
                // simpleLog.info("calculateCostMoney--orderEntity.getCostTime="+ orderEntity.getCostTime());

                BigDecimal nowprice = MoneyCalculateUtils.calculatePrice(allDistance, time);
                time = orderEntity.getCostTime() + time;


                //simpleLog.info("calculateCostMoney--orderEntity.getCostTime="+ orderEntity.getCostTime());


                BigDecimal price = nowprice.add(orderEntity.getOrderPrice());
                simpleLog.info("calculateCostMoney--price=" + price);
                orderEntity.setCostTime(time);
                orderEntity.setOrderPrice(price);
                orderDao.save(orderEntity);
                // simpleLog.info("calculateCostMoney--  orderDao.save(orderEntity)");
            }
        }

    }


}
