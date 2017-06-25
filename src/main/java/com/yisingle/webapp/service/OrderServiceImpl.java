package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.OrderDao;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jikun on 17/6/25.
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    public ResponseData generateWaitOrder(OrderRequestData requestData, UserEntity userEntity) {

        ResponseData responseData = new ResponseData();


        OrderEntity entity = new OrderEntity();
        entity.setPhoneNum(requestData.getPhoneNum());
        entity.setStartPlaceName(requestData.getStartPlaceName());
        entity.setEndPlaceName(requestData.getEndPlaceName());
        entity.setStartLongitude(requestData.getStartLongitude());
        entity.setStartLatitude(requestData.getStartLatitude());
        entity.setEndLongitude(requestData.getEndLongitude());
        entity.setEndLatitude(requestData.getEndLatitude());
        entity.setOrderState(OrderEntity.OrderState.State.WATI.value());
        entity.setUserEntity(userEntity);
        userEntity.getSetOrderEntity().add(entity);
        orderDao.save(entity);

//        responseData.setResponse(entity);
        responseData.setCode(ResponseData.Code.SUCCESS.value());
        responseData.setErrorMsg("");


        return responseData;


    }

}
