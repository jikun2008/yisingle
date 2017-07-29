package com.yisingle.webapp.utils;

import com.yisingle.webapp.service.OrderCoordinateServiceImpl;
import org.apache.commons.logging.impl.SimpleLog;

import java.math.BigDecimal;

/**
 * Created by jikun on 17/7/28.
 */
public class MoneyCalculateUtils {

    private  static  SimpleLog simpleLog = new SimpleLog(MoneyCalculateUtils.class.getSimpleName());

    public static BigDecimal calculatePrice(float allDistance, long time) {
        BigDecimal ditancePrice = new BigDecimal(allDistance).multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000));

        simpleLog.info("calculatePrice-- ditancePrice=" + ditancePrice.doubleValue());
        BigDecimal timePrice = new BigDecimal(time).divide(new BigDecimal(60000)).multiply(new BigDecimal(0.15));

        simpleLog.info("calculatePrice-- timePrice=" + timePrice.doubleValue());
        BigDecimal allPrice = ditancePrice.add(timePrice);
        return allPrice;
    }
}
