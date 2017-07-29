package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.DriverDao;
import com.yisingle.webapp.dao.MessageDao;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jikun on 17/7/28.
 */
@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    public ResponseData<List<MessageEntity>> findMessageLimit10() {
        ResponseData<List<MessageEntity>> responseData = new ResponseData<List<MessageEntity>>();
        List<MessageEntity> messageEntityList = messageDao.findMessageEntity(10);
        if (messageEntityList != null && messageEntityList.size() > 0) {
            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(messageEntityList);
        } else {
            responseData.setCode(ResponseData.Code.FAILED.value());
            responseData.setErrorMsg("没有信息");
        }

        return responseData;
    }
}
