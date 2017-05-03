package com.yisingle.webapp.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jikun on 17/5/2.
 */
@Entity
@Table(name = "t_order")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue()
    private int id;


    @NotEmpty
    private String latitude;//纬度
    @NotEmpty
    private String longitude;//经度
    @NotEmpty
    private String ordername;
    @NotEmpty
    private String destination;//订单目的地名称


    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
