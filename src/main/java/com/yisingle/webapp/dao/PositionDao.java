package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.PositionEntity;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by jikun on 17/5/2.
 */
public interface PositionDao {

     int save(PositionEntity entity);

     void update(PositionEntity entity);

     void delete(PositionEntity entity);

     List<PositionEntity> findAll();
}
