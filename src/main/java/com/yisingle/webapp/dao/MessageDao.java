package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.MessageEntity;

import java.util.List;

/**
 * Created by jikun on 17/7/27.
 */
public interface MessageDao {

    List<MessageEntity> findMessageEntity(int count);
}
