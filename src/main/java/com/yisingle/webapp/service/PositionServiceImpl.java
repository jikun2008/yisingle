package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.PositionDao;
import com.yisingle.webapp.data.PositionRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.PositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jikun on 17/5/2.
 */

@Service("positionService")
@Transactional
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;


    public ResponseData update(PositionRequestData data) {
        ResponseData responseData = new ResponseData();
        int code;
        String errorMsg;

        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setDeviceId(data.getDeviceId());
        positionEntity.setLatitude(data.getLatitude());
        positionEntity.setLongitude(data.getLongitude());
        positionEntity.setPhonenum(data.getPhonenum());
        positionDao.save(positionEntity);
        code = ResponseData.Code.SUCCESS.value();
        errorMsg = "";
        responseData.setCode(code);
        responseData.setErrorMsg(errorMsg);

        return responseData;
    }
}
