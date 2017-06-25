package com.yisingle.webapp.service;

import com.yisingle.webapp.data.PositionRequestData;
import com.yisingle.webapp.data.ResponseData;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jikun on 17/5/2.
 */
public interface PositionService {


    ResponseData update(PositionRequestData data);

    ResponseData nearByCar(String latitude, String longitude);

}
