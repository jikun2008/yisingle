package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.OrderCoordinateEntity;
import com.yisingle.webapp.entity.OrderEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jikun on 17/7/27.
 */
@Repository
public class OrderCoordinateDaoImpl implements OrderCoordinateDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(OrderCoordinateEntity entity) {
        return (Integer) sessionFactory.getCurrentSession().save(entity);
    }

    public List<OrderCoordinateEntity> findByOrderId(String orderId) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_order_coordinate WHERE  orderId= (:valueId)").addEntity(OrderCoordinateEntity.class);

        sqlQuery.setParameter("valueId", orderId);
        List<OrderCoordinateEntity> list = sqlQuery.list();
        return list;
    }

    public List<OrderCoordinateEntity> findByOrderIdAndCalculateType(String orderId, Integer[] types) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_order_coordinate WHERE calculateType IN (:valueList) AND orderId= (:valueId)").addEntity(OrderCoordinateEntity.class);


        sqlQuery.setParameterList("valueList", types);
        sqlQuery.setParameter("valueId", orderId);
        List<OrderCoordinateEntity> list = sqlQuery.list();
        return list;
    }
}
