package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.DriverEntity;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverDaoimpl implements DriverDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(DriverEntity u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);
    }

    public List<DriverEntity> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DriverEntity.class);
        return criteria.list();
    }

    public List<DriverEntity> findDriverByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_driver WHERE driverName=?").addEntity(DriverEntity.class);

        sqlQuery.setParameter(0, name);
        List<DriverEntity> list = sqlQuery.list();
        return list;

    }

    public List<DriverEntity> findDriverByPhoneNum(String phone) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_driver WHERE phonenum=?").addEntity(DriverEntity.class);
        sqlQuery.setParameter(0, phone);
        List<DriverEntity> list = sqlQuery.list();

        return list;
    }

    public List<DriverEntity> findDriverByState(int state) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_driver WHERE state=?").addEntity(DriverEntity.class);

        sqlQuery.setParameter(0, state);
        List<DriverEntity> list = sqlQuery.list();
        return list;
    }
}