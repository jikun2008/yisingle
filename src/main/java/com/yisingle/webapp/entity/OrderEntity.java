package com.yisingle.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by jikun on 17/5/2.
 */
@JsonIgnoreProperties({"userEntity", "driverEntity", "setOrderCoordinateEntity"})
@Entity
@Table(name = "t_order")
public class OrderEntity implements Serializable, Cloneable {


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


    //订单金额
    private BigDecimal orderPrice;


    private int orderState;

    //订单创建时间
    private long createTime;


    //司机端收到回复时间
    private long driverRelyTime;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "driverid")
    private DriverEntity driverEntity;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "orderId")
    private Set<OrderCoordinateEntity> setOrderCoordinateEntity;


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDriverRelyTime() {
        return driverRelyTime;
    }

    public void setDriverRelyTime(long driverRelyTime) {
        this.driverRelyTime = driverRelyTime;
    }

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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Set<OrderCoordinateEntity> getSetOrderCoordinateEntity() {
        return setOrderCoordinateEntity;
    }

    public void setSetOrderCoordinateEntity(Set<OrderCoordinateEntity> setOrderCoordinateEntity) {
        this.setOrderCoordinateEntity = setOrderCoordinateEntity;
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


            WATI_NEW(-1),
            WATI_OLD(0),
            HAVE_TAKE(1),//订单已接受
            DRIVER_ARRIVE(2),//司机已到达
            PASSENGER_IN_CAR(3),//乘客已经上车
            PASSENGER_OUT_CAR(4),//乘客已下车
            HAVE_COMPLETE(5);//订单已经完成
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
