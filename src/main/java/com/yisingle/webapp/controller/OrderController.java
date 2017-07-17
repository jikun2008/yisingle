package com.yisingle.webapp.controller;

import com.yisingle.webapp.base.BaseController;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.UserEntity;
import com.yisingle.webapp.service.OrderService;
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


/**
 * Created by jikun on 17/6/25.
 */
@Controller
@RequestMapping("/")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    /**
     * 发送订单
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendOrder")
    @ResponseBody
    public ResponseData sendOrder(@Valid @RequestBody OrderRequestData requestData, BindingResult bindingResult) {

        ResponseData<OrderDetailData> data = new ResponseData();

        if (bindingResult.hasErrors()) {
            data.setErrorMsg(BindingResultUtils.getError(bindingResult));
        } else {
            List<UserEntity> userEntityList = userService.findByPhoneNum(requestData.getPhoneNum());

            if (null != userEntityList && userEntityList.size() == 0) {

                data.setCode(ResponseData.Code.FAILED.value());
                data.setErrorMsg("未找到当前的用户");
            } else {
                UserEntity userEntity = userEntityList.get(0);
                data = orderService.generateWaitOrder(requestData, userEntity);

            }
        }

        return data;
    }
}
