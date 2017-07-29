package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.MessageRequestData;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.service.MessageService;
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

/**
 * Created by jikun on 17/7/27.
 */

@Controller
@RequestMapping("/")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送订单
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/driver/message/findMessage")
    @ResponseBody
    public ResponseData findMessage(@Valid @RequestBody MessageRequestData requestData, BindingResult bindingResult) {

        ResponseData data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = messageService.findMessageLimit10();
        }


        return data;
    }

}
