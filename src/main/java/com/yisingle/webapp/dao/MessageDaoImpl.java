package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.MessageEntity;
import com.yisingle.webapp.entity.OrderCoordinateEntity;
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
public class MessageDaoImpl implements MessageDao {


    @Autowired
    private SessionFactory sessionFactory;

    public List<MessageEntity> findMessageEntity(int count) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * from t_message").addEntity(MessageEntity.class);

        sqlQuery.setMaxResults(count);
        List<MessageEntity> list = sqlQuery.list();
        return list;
    }
}
