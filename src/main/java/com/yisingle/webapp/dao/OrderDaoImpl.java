package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.OrderEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jikun on 17/6/25.
 */
@Repository
public class OrderDaoImpl implements OrderDao {


    @Autowired
    private SessionFactory sessionFactory;


    public int save(OrderEntity entity) {
        return (Integer) sessionFactory.getCurrentSession().save(entity);

    }

    public void update(OrderEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void delete(OrderEntity entity) {
        sessionFactory.getCurrentSession().delete(entity);

    }

    public List<OrderEntity> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OrderEntity.class);
        return criteria.list();
    }
}
