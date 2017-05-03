package com.yisingle.webapp.dao;

import com.yisingle.webapp.entity.UserEntity;

import java.util.List;

public interface UserDao {
    int save(UserEntity u);

    List<UserEntity> findAll();

    List<UserEntity> findUserByName(String name);
}