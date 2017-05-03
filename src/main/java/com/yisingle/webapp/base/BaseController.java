package com.yisingle.webapp.base;

import com.yisingle.webapp.data.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jikun on 17/5/3.
 */
public class BaseController {


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseData handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> it = set.iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            ConstraintViolation constraintViolation = it.next();
            String className = constraintViolation.getRootBeanClass().getSimpleName();
            String property = constraintViolation.getPropertyPath().toString();
            String msg = constraintViolation.getMessage();
            builder.append("服务器错误:" + className + "." + property + ":" + msg);
        }
        ResponseData responseData = new ResponseData();


        responseData.setCode(ResponseData.Code.FAILED.value());
        responseData.setErrorMsg(builder.toString());
        return responseData;
    }


}
