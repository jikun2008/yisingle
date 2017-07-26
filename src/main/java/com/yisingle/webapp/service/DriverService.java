package com.yisingle.webapp.service;

import com.yisingle.webapp.data.*;
import com.yisingle.webapp.entity.DriverEntity;

/**
 * Created by jikun on 17/6/26.
 */
public interface DriverService {
    ResponseData saveDriver(DriverRegisterRequestData driverRequestData);

    ResponseData<DriverEntity> loginDriver(DriverLoginRequestData loginRequestData);

    ResponseData<DriverEntity> changeDriverState(DriverStateRequestData stateRequestData);


    void saveLocationPointToDriver(HeartBeatData data);


}
