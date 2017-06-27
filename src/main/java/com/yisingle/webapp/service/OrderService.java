package com.yisingle.webapp.service;

import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.UserEntity;

/**
 * Created by jikun on 17/6/25.
 */
public interface OrderService {


    ResponseData generateWaitOrder(OrderRequestData requestData, UserEntity userEntity);


    void changeOrderState();


    ResponseData checkOrderState();

}
