package com.yisingle.webapp.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by jikun on 17/5/1.
 */
public class BindingResultUtils {
    public static String getError(BindingResult result) {
        StringBuilder builder = new StringBuilder();
        List<ObjectError> errorList = result.getAllErrors();
        builder.append("参数错误:");
        for (int i = 0; i < errorList.size(); i++) {
            ObjectError objectError = errorList.get(i);
            builder.append(objectError.getDefaultMessage());
            if (i < errorList.size() - 1) {
                builder.append(",");
            }

        }


        return builder.toString();
    }
}
