package com.yisingle.webapp.service;

import com.yisingle.webapp.data.DriverLoginRequestData;
import com.yisingle.webapp.data.DriverRegisterRequestData;
import com.yisingle.webapp.data.ResponseData;

/**
 * Created by jikun on 17/6/26.
 */
public interface DriverService {
    ResponseData saveDriver(DriverRegisterRequestData driverRequestData);

    ResponseData loginDriver(DriverLoginRequestData loginRequestData);
}
