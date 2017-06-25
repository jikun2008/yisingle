package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.PositionDao;
import com.yisingle.webapp.data.CarPositionData;
import com.yisingle.webapp.data.PositionRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.PositionEntity;
import com.yisingle.webapp.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public ResponseData<List<CarPositionData>> nearByCar(String latitude, String longitude) {
        double currentlatitude = Double.parseDouble(latitude);
        double currentlongitude = Double.parseDouble(longitude);
        ResponseData<List<CarPositionData>> responseData = new ResponseData();
        int code;
        String errorMsg;


        List<CarPositionData> list = new ArrayList<CarPositionData>();


        for (int i = 0; i < 15; i++) {

            double random = Math.random() - 0.5;
            double random1 = Math.random() - 0.5;
            System.out.print("测试代码:" + random);
            System.out.print("测试代码:" + random1);
            double nowlatitude = formatDouble2(currentlatitude + random * 0.01, 6);
            double nowlongitude = formatDouble2((currentlongitude + random1 * 0.01), 6);

            float bearing = (float) formatDouble2(Math.random() * 360, 0);
            if (bearing <= 0 || bearing >= 360) {
                bearing = 0;
            }
            list.add(new CarPositionData(nowlatitude, nowlongitude, bearing));
        }


        code = ResponseData.Code.SUCCESS.value();
        errorMsg = "";
        responseData.setCode(code);
        responseData.setResponse(list);
        responseData.setErrorMsg(errorMsg);
        return responseData;
    }

    public static double formatDouble2(double d, int point) {
        // 旧方法，已经不再推荐使用
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);


        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(point, RoundingMode.UP);


        return bg.doubleValue();
    }


}
