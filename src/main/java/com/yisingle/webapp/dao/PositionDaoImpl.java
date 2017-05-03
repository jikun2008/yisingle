package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.PositionEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jikun on 17/5/2.
 */
@Repository
public class PositionDaoImpl implements PositionDao {


    @Autowired
    private SessionFactory sessionFactory;


    public int save(PositionEntity entity) {
        return (Integer) sessionFactory.getCurrentSession().save(entity);

    }

    public void update(PositionEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void delete(PositionEntity entity) {
        sessionFactory.getCurrentSession().delete(entity);

    }

    public List<PositionEntity> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PositionEntity.class);
        return criteria.list();
    }
}
