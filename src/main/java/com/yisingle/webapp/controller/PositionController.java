package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.PositionRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.service.PositionService;
import com.yisingle.webapp.utils.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class PositionController extends BaseController {


    @Autowired
    private PositionService positionService;

    /**
     * 上传当前位置
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateCurrentPosition")
    @ResponseBody
    public ResponseData updateCurrentPosition(@Valid @RequestBody PositionRequestData requestData, BindingResult bindingResult) {
        ResponseData data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            data = positionService.update(requestData);
        }
        return data;

    }
}
