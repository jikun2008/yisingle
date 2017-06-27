package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.DriverLoginRequestData;
import com.yisingle.webapp.data.DriverRegisterRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.service.DriverService;
import com.yisingle.webapp.utils.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by jikun on 17/6/26.
 */
@Controller
@RequestMapping("/")
public class DriverController extends BaseController {

    @Autowired
    private DriverService driverService;

    @RequestMapping(method = RequestMethod.POST, value = "/driver/register")
    @ResponseBody
    public ResponseData register(@Valid @RequestBody DriverRegisterRequestData requestData, BindingResult bindingResult) {

        ResponseData data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = driverService.saveDriver(requestData);
        }


        return data;


    }

    @RequestMapping(method = RequestMethod.POST, value = "/driver/login")
    @ResponseBody
    public ResponseData login(@Valid @RequestBody DriverLoginRequestData requestData, BindingResult bindingResult) {

        ResponseData data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = driverService.loginDriver(requestData);
        }


        return data;


    }
}
