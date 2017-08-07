package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.LoginRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.UserEntity;
import com.yisingle.webapp.service.UserService;
import com.yisingle.webapp.utils.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping("/json")
    @ResponseBody
    public List<UserEntity> json() {
        return userService.getAllUsernames();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/passenger/register")
    @ResponseBody
    public ResponseData register(@Valid @RequestBody LoginRequestData loginRequestData, BindingResult bindingResult) {

        ResponseData data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = userService.saveUser(loginRequestData);
        }


        return data;


    }


    @RequestMapping(method = RequestMethod.POST, value = "/passenger/login")
    @ResponseBody
    public ResponseData login(@Valid @RequestBody LoginRequestData loginRequestData, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        if (bindingResult.hasErrors()) {
            responseData.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            responseData = userService.login(loginRequestData);
        }

        return responseData;
    }


}