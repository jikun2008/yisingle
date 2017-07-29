package com.yisingle.webapp.utils;

import com.yisingle.webapp.service.OrderCoordinateServiceImpl;
import org.apache.commons.logging.impl.SimpleLog;

/**
 * Created by jikun on 17/7/28.
 */
public class DistanceUtils {

    private static SimpleLog simpleLog = new SimpleLog(DistanceUtils.class.getSimpleName());


    public static float calculateLineDistance(String startlatitude, String startlongitude, String endlatitude, String endlongitude) {


        return calculateLineDistance(Double.parseDouble(startlatitude), Double.parseDouble(startlongitude), Double.parseDouble(endlatitude), Double.parseDouble(endlongitude));

    }

    public static float calculateLineDistance(double startlatitude, double startlongitude, double endlatitude, double endlongitude) {

        try {
            double var2 = 0.01745329251994329D;
            double var4 = startlongitude;
            double var6 = startlatitude;
            double var8 = endlongitude;
            double var10 = endlatitude;
            var4 *= 0.01745329251994329D;
            var6 *= 0.01745329251994329D;
            var8 *= 0.01745329251994329D;
            var10 *= 0.01745329251994329D;
            double var12 = Math.sin(var4);
            double var14 = Math.sin(var6);
            double var16 = Math.cos(var4);
            double var18 = Math.cos(var6);
            double var20 = Math.sin(var8);
            double var22 = Math.sin(var10);
            double var24 = Math.cos(var8);
            double var26 = Math.cos(var10);
            double[] var28 = new double[3];
            double[] var29 = new double[3];
            var28[0] = var18 * var16;
            var28[1] = var18 * var12;
            var28[2] = var14;
            var29[0] = var26 * var24;
            var29[1] = var26 * var20;
            var29[2] = var22;
            double var30 = Math.sqrt((var28[0] - var29[0]) * (var28[0] - var29[0]) + (var28[1] - var29[1]) * (var28[1] - var29[1]) + (var28[2] - var29[2]) * (var28[2] - var29[2]));
            return (float) (Math.asin(var30 / 2.0D) * 1.27420015798544E7D);
        } catch (Throwable var32) {
            var32.printStackTrace();
            return 0.0F;
        }

    }

}
