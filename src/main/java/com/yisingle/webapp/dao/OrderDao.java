package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.OrderEntity;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by jikun on 17/5/3.
 */
public interface OrderDao {


    int save(OrderEntity entity);

    void update(OrderEntity entity);

    void delete(OrderEntity entity);

    List<OrderEntity> findAll();

    OrderEntity load(int id);

    OrderEntity find(int id);

    List<OrderEntity> findWaitNewStateOrder();

    List<OrderEntity> findWaitStateAndUserId(Integer[] state, int userid);

    List<OrderEntity> findOrderByDriverId(String driverId);

    List<OrderEntity> findOrderByState(Integer[] states);

    List<OrderEntity> findOrderByDriverIdAndState(Integer[] states, String driverId);
}
