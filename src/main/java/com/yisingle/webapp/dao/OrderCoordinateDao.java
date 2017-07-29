package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.OrderCoordinateEntity;

import java.util.List;


/**
 * Created by jikun on 17/7/27.
 */
public interface OrderCoordinateDao {

    int save(OrderCoordinateEntity entity);

    List<OrderCoordinateEntity> findByOrderId(String orderId);

    List<OrderCoordinateEntity> findByOrderIdAndCalculateType(String orderId, Integer[] types);
}
