package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.DriverDao;
import com.yisingle.webapp.dao.OrderDao;
import com.yisingle.webapp.dao.UserDao;
import com.yisingle.webapp.data.DriverStatisticData;
import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.DriverEntity;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.OrderEntity.OrderState.State;
import com.yisingle.webapp.entity.UserEntity;
import com.yisingle.webapp.websocket.SystemWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jikun on 17/6/25.
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;


    public ResponseData<OrderDetailData> generateWaitOrder(OrderRequestData requestData, UserEntity userEntity) {

        ResponseData<OrderDetailData> responseData = new ResponseData();


        List<OrderEntity> list = orderDao.findWaitStateAndUserId(new Integer[]{State.WATI_NEW.value(), State.WATI_OLD.value(), State.HAVE_TAKE.value()}, userEntity.getId());

        if (null != list && list.size() > 0) {

            responseData.setCode(ResponseData.Code.SUCCESS.value());
            OrderEntity orderEntity = list.get(0);


            if (orderEntity.getOrderState() == State.WATI_NEW.value()) {
                orderEntity.setOrderState(State.WATI_OLD.value());
            }
            orderDao.save(orderEntity);

            DriverEntity driver = orderEntity.getDriverEntity();

            UserEntity user = orderEntity.getUserEntity();

            OrderDetailData orderDetailData = new OrderDetailData(orderEntity);

            orderDetailData.setDriver(driver);
            orderDetailData.setUser(user);

            responseData.setResponse(orderDetailData);
            responseData.setErrorMsg("你有未完成的订单请先完成该订单");


        } else {
            OrderEntity entity = new OrderEntity();
            entity.setPhoneNum(requestData.getPhoneNum());
            entity.setStartPlaceName(requestData.getStartPlaceName());
            entity.setEndPlaceName(requestData.getEndPlaceName());
            entity.setCreateTime(System.currentTimeMillis());//设置订单生成时间
            entity.setStartLongitude(requestData.getStartLongitude());
            entity.setStartLatitude(requestData.getStartLatitude());
            entity.setEndLongitude(requestData.getEndLongitude());
            entity.setEndLatitude(requestData.getEndLatitude());
            entity.setOrderState(OrderEntity.OrderState.State.WATI_NEW.value());
            entity.setUserEntity(userEntity);

            orderDao.save(entity);

            OrderDetailData orderDetailData = new OrderDetailData(entity);

            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(orderDetailData);
            responseData.setErrorMsg("");


        }
//        try {
//            startJob();
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }

        return responseData;


    }


//    private void startJob() throws SchedulerException {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//        QuartzManager quartzManager = new QuartzManager(scheduler);
//
//        if (!scheduler.isStarted()) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("id", 1);
//            // 每1000毫秒执行一次，重复执行
//            quartzManager.addJob("myJob", "test", OrderJob.class, map, 6000l, -1);
//            quartzManager.startScheduler();
//        }
//
//    }

    public OrderEntity changeOrderWaitNewStateToWatiOldState() {

        List<OrderEntity> orderEntityList = orderDao.findWaitNewStateOrder();

        OrderEntity orderEntity = null;

        if (null != orderEntityList && orderEntityList.size() > 0) {
            orderEntity = orderEntityList.get(0);
            List<DriverEntity> driverEntityList = driverDao.findDriverByState(DriverEntity.DriverState.State.WATI_FOR_ORDER.value());

            for (DriverEntity driverEntity : driverEntityList) {
                List<OrderEntity> orderEntities = orderDao.findOrderByDriverIdAndState(new Integer[]{State.WATI_OLD.value()}, driverEntity.getId() + "");

                boolean isHaverOrder = orderEntities != null && orderEntities.size() > 0;
                if (!isHaverOrder) {
                    //如果没有订单才分配订单给
                    orderEntity.setOrderState(State.WATI_OLD.value());
                    orderEntity.setDriverEntity(driverEntity);
                    orderDao.update(orderEntity);
                    break;
                }

            }


        }
        return orderEntity;

    }

    public List<OrderEntity> checkWaitOldOrder() {
        List<OrderEntity> orderEntityList = orderDao.findOrderByState(new Integer[]{State.WATI_OLD.value()});
        return orderEntityList;
    }


    public ResponseData<OrderDetailData> changOrderState(int orderId, int orderState) {
        ResponseData<OrderDetailData> responseData = new ResponseData();
        OrderEntity orderEntity = orderDao.find(orderId);
        if (orderEntity != null) {


            orderEntity.setOrderState(orderState);
            orderDao.save(orderEntity);
            UserEntity user = orderEntity.getUserEntity();
            DriverEntity driver = orderEntity.getDriverEntity();

            OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
            orderDetailData.setUser(user);
            orderDetailData.setDriver(driver);
            responseData.setCode(ResponseData.Code.SUCCESS.value());

            responseData.setResponse(orderDetailData);
        } else {
            responseData.setErrorMsg("未找到订单");
        }


        return responseData;
    }


    public ResponseData<OrderDetailData> acceptDriverOrder(int orderId) {
        ResponseData<OrderDetailData> responseData = new ResponseData();
        OrderEntity orderEntity = orderDao.find(orderId);
        if (orderEntity != null) {

            orderEntity.setOrderState(State.HAVE_TAKE.value());


            DriverEntity driverEntity = orderEntity.getDriverEntity();

            driverEntity.setState(DriverEntity.DriverState.State.SERVICE.value());
            orderDao.save(orderEntity);

            driverDao.save(driverEntity);


            UserEntity user = orderEntity.getUserEntity();


            OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
            orderDetailData.setUser(user);
            orderDetailData.setDriver(driverEntity);

            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(orderDetailData);
        } else {
            responseData.setCode(ResponseData.Code.FAILED.value());
            responseData.setErrorMsg("未找到当前订单");
        }
        return responseData;
    }

    public ResponseData<OrderDetailData> finishDriverOrder(int orderId) {
        ResponseData<OrderDetailData> responseData = new ResponseData();
        OrderEntity orderEntity = orderDao.find(orderId);
        if (orderEntity != null) {

            orderEntity.setOrderState(State.HAVE_COMPLETE.value());


            DriverEntity driverEntity = orderEntity.getDriverEntity();

            driverEntity.setState(DriverEntity.DriverState.State.WATI_FOR_ORDER.value());
            orderDao.save(orderEntity);

            driverDao.save(driverEntity);

            UserEntity user = orderEntity.getUserEntity();


            OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
            orderDetailData.setUser(user);
            orderDetailData.setDriver(driverEntity);

            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(orderDetailData);
        } else {
            responseData.setCode(ResponseData.Code.FAILED.value());
            responseData.setErrorMsg("未找到当前订单");
        }
        return responseData;
    }

    public ResponseData<OrderDetailData> findOrderByDriverIdAndState(Integer[] states, String driverId) {
        ResponseData<OrderDetailData> responseData = new ResponseData();
        List<OrderEntity> orderEntityList = orderDao.findOrderByDriverIdAndState(states, driverId);
        if (null != orderEntityList && orderEntityList.size() > 0) {
            OrderEntity orderEntity = orderEntityList.get(0);


            UserEntity user = orderEntity.getUserEntity();
            DriverEntity driver = orderEntity.getDriverEntity();

            OrderDetailData orderDetailData = new OrderDetailData(orderEntity);
            orderDetailData.setUser(user);
            orderDetailData.setDriver(driver);


            responseData.setCode(ResponseData.Code.SUCCESS.value());
            responseData.setResponse(orderDetailData);
        } else {
            responseData.setCode(ResponseData.Code.FAILED.value());
            responseData.setErrorMsg("未找到订单");
        }
        return responseData;

    }

    public void saveRelyTimeToOrder(int id) {

        OrderEntity orderEntity = orderDao.find(Integer.valueOf(id));
        if (null != orderEntity) {
            orderEntity.setDriverRelyTime(System.currentTimeMillis());
            orderDao.save(orderEntity);
        }

    }

    public void sendPrice() {


        List<OrderEntity> orderEntityList = orderDao.findOrderByState(new Integer[]{State.DRIVER_ARRIVE.value(), State.PASSENGER_IN_CAR.value(), State.PASSENGER_OUT_CAR.value()});

        for (OrderEntity orderEntity : orderEntityList) {

            if (null != orderEntity && null != orderEntity.getDriverEntity())
                systemWebSocketHandler.sendPrcieToDriver(orderEntity.getDriverEntity().getId() + "", orderEntity);
        }
    }

    public ResponseData<DriverStatisticData> getOrderConutAndMoney(int drivierId) {

        ResponseData<DriverStatisticData> data = new ResponseData<DriverStatisticData>();
        List<OrderEntity> orderEntityList = orderDao.findTodayOrder(new Integer[]{State.HAVE_COMPLETE.value()}, drivierId + "");

        data.setCode(ResponseData.Code.SUCCESS.value());
        int count = 0;
        BigDecimal money = new BigDecimal(0);
        DriverStatisticData statisticData = new DriverStatisticData(0, money);
        if (orderEntityList != null && orderEntityList.size() > 0) {
            count = orderEntityList.size();
            for (OrderEntity orderEntity : orderEntityList) {
                money = money.add(orderEntity.getOrderPrice());
            }

            statisticData.setToday_order_count(count);
            statisticData.setToday_order_gain(money);


        }
        data.setResponse(statisticData);
        return data;
    }


}
