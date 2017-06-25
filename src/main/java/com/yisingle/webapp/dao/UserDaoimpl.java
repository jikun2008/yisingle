package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoimpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(UserEntity u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);
    }

    public List<UserEntity> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
        return criteria.list();
    }

    public List<UserEntity> findUserByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_user WHERE username=?").addEntity(UserEntity.class);

        sqlQuery.setParameter(0, name);
        List<UserEntity> list = sqlQuery.list();
        return list;

    }

    public List<UserEntity> findUserByPhoneNum(String phone) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_user WHERE phonenum=?").addEntity(UserEntity.class);

        sqlQuery.setParameter(0, phone);
        List<UserEntity> list = sqlQuery.list();
        return list;
    }
}