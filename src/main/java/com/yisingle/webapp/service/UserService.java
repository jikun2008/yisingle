package com.yisingle.webapp.service;

import com.yisingle.webapp.data.LoginRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.UserEntity;

import java.util.List;

public interface UserService {
    void saveUsers(List<UserEntity> us);

    ResponseData saveUser(LoginRequestData loginRequestData);

    List<UserEntity> getAllUsernames();

    List<UserEntity> getUserEntity(String name);

    ResponseData login(LoginRequestData data);

    List<UserEntity> findByPhoneNum(String phoneNum);
}