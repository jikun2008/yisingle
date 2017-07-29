package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.*;
import com.yisingle.webapp.entity.DriverEntity;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.service.DriverService;
import com.yisingle.webapp.service.OrderService;
import com.yisingle.webapp.utils.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jikun on 17/6/26.
 */
@Controller
@RequestMapping("/")
public class DriverController extends BaseController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, value = "/driver/register")
    @ResponseBody
    public ResponseData<DriverEntity> register(@Valid @RequestBody DriverRegisterRequestData requestData, BindingResult bindingResult) {

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

        ResponseData<DriverEntity> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = driverService.loginDriver(requestData);
        }


        return data;


    }


    @RequestMapping(method = RequestMethod.POST, value = "/driver/changeDriverState")
    @ResponseBody
    public ResponseData changeDriverState(@Valid @RequestBody DriverStateRequestData requestData, BindingResult bindingResult) {
        ResponseData<DriverEntity> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = driverService.changeDriverState(requestData);
        }
        return data;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/driver/findDriverOrder")
    @ResponseBody
    public ResponseData<OrderDetailData> findDriverOrder(@Valid @RequestBody DriverOrderRequest requestData, BindingResult bindingResult) {
        ResponseData<OrderDetailData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = orderService.findOrderByDriverIdAndState(requestData.getOrderState(), requestData.getDriverId());


        }
        return data;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/driver/acceptDriverOrder")
    @ResponseBody
    public ResponseData<OrderDetailData> acceptDriverOrder(@Valid @RequestBody DriverAccentOrderRequestData requestData, BindingResult bindingResult) {
        ResponseData<OrderDetailData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = orderService.acceptDriverOrder(requestData.getOrderId());
        }
        return data;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/driver/finishDriverOrder")
    @ResponseBody
    public ResponseData<OrderDetailData> finishDriverOrder(@Valid @RequestBody DriverAccentOrderRequestData requestData, BindingResult bindingResult) {
        ResponseData<OrderDetailData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = orderService.finishDriverOrder(requestData.getOrderId());
        }
        return data;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/driver/changeDriverOrderState")
    @ResponseBody
    public ResponseData<OrderDetailData> changeDriverOrderState(@Valid @RequestBody DriverchangeOrderRequest requestData, BindingResult bindingResult) {
        ResponseData<OrderDetailData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = orderService.changOrderState(requestData.getOrderId(), requestData.getOderState());
        }
        return data;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/driver/getTodayStatistics")
    @ResponseBody
    public ResponseData<DriverStatisticData> getTodayStatistics(@Valid @RequestBody DriverStatisticRequestData requestData, BindingResult bindingResult) {
        ResponseData<DriverStatisticData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = orderService.getOrderConutAndMoney(requestData.getDriverId());


        }
        return data;
    }
}
