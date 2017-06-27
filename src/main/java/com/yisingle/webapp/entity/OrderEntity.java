package com.yisingle.webapp.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.*;

/**
 * Created by jikun on 17/5/2.
 */
@Entity
@Table(name = "t_order")
public class OrderEntity implements Serializable,Cloneable {


    @Id
    @GeneratedValue()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "phoneNum不能为空")
    private String phoneNum;

    @NotEmpty(message = "起点纬度不能空")
    private String startLatitude;


    @NotEmpty(message = "起点经度不能空")
    private String startLongitude;


    @NotEmpty(message = "终点纬度不能空")
    private String endLatitude;

    @NotEmpty(message = "终点经度不能空")
    private String endLongitude;


    @NotEmpty(message = "起点名称不能为空")
    private String startPlaceName;


    @NotEmpty(message = "终点名称不能为空")
    private String endPlaceName;


    private int orderState;


    @Lazy
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "driverid")
    private DriverEntity driverEntity;


    public String getStartPlaceName() {
        return startPlaceName;
    }

    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }

    public void setEndPlaceName(String endPlaceName) {
        this.endPlaceName = endPlaceName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public DriverEntity getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(DriverEntity driverEntity) {
        this.driverEntity = driverEntity;
    }

    /**
     * 订单状态注解
     *
     * @author peida
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface OrderState {

        enum State {


            WATI(0), HAVE_TAKE(1), HAVE_COMPLETE(3);
            int state;

            State(int state) {
                this.state = state;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int value() {
                return state;
            }
        }


    }

    @Override
    public Object clone() {
        OrderEntity orderEntity = null;

        try {
            orderEntity = (OrderEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return orderEntity;
    }
}
