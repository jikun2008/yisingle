package com.yisingle.webapp.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.Set;

import com.yisingle.webapp.entity.DriverEntity.DriverState.State;

/**
 * Created by jikun on 17/6/26.
 */
@Entity
@Table(name = "t_driver")
public class DriverEntity implements Serializable {

    @Id
    @GeneratedValue()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private String password;//密码

    private String latitude;//纬度

    private String longitude;//经度


    private String deviceId;//设备id

    @Column(nullable = false)
    private String phonenum;//电话号码

    private int state = State.WATI_FOR_ORDER.value();//司机状态  等待中,服务中,下线中

    @OneToMany
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "driverid")
    private Set<OrderEntity> setOrderEntity;

    public Set<OrderEntity> getSetOrderEntity() {
        return setOrderEntity;
    }

    public void setSetOrderEntity(Set<OrderEntity> setOrderEntity) {
        this.setOrderEntity = setOrderEntity;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 订单状态注解
     *
     * @author peida
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface DriverState {

        enum State {


            WATI_FOR_ORDER(0), SERVICE(1), BREAKDOWN(2);
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
}
