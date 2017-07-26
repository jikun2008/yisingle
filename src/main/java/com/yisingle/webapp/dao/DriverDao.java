package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.DriverEntity;

import java.util.List;

public interface DriverDao {
    int save(DriverEntity u);


    DriverEntity findById(int id);

    List<DriverEntity> findAll();

    List<DriverEntity> findDriverByName(String name);


    List<DriverEntity> findDriverByPhoneNum(String phone);

    List<DriverEntity> findDriverByState(int state);
}