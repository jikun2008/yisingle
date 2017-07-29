package com.yisingle.webapp.service;

import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.MessageEntity;

import java.util.List;

/**
 * Created by jikun on 17/7/28.
 */
public interface MessageService {

    ResponseData<List<MessageEntity>> findMessageLimit10();
}
