package com.yisingle.webapp.service;

import com.yisingle.webapp.data.DriverStatisticData;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.UserEntity;

import java.util.List;

/**
 * Created by jikun on 17/6/25.
 */
public interface OrderService {


    ResponseData<OrderDetailData> generateWaitOrder(OrderRequestData requestData, UserEntity userEntity);


    OrderEntity changeOrderWaitNewStateToWatiOldState();

    List<OrderEntity> checkWaitOldOrder();


    ResponseData<OrderDetailData> changOrderState(int orderId, int orderState);


    ResponseData<OrderDetailData> acceptDriverOrder(int orderId);

    ResponseData<OrderDetailData> finishDriverOrder(int orderId);


    ResponseData<OrderDetailData> findOrderByDriverIdAndState(Integer[] states, String driverId);


    void saveRelyTimeToOrder(int id);


    void sendPriceToDriver();


    ResponseData<DriverStatisticData> getOrderConutAndMoney(int drivierId);

    void savePassengerRelyStateToOrder(int id);


    void sendOrderStateChangeToPassenger();


    void sendPriceOrderToPassenger();



}
