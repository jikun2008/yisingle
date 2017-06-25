package com.yisingle.webapp.service;

import com.yisingle.webapp.dao.UserDao;
import com.yisingle.webapp.data.LoginRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void saveUsers(List<UserEntity> us) {
        for (UserEntity u : us) {
            userDao.save(u);
        }
    }


    public List<UserEntity> getUser(String username) {
        List<UserEntity> list = userDao.findUserByName(username);
        return list;

    }

    public ResponseData saveUser(LoginRequestData data) {

        ResponseData<UserEntity> responseData = new ResponseData();
        int code;
        String errormsg;


        if (isUserExist(data.getUsername())) {
            code = ResponseData.Code.FAILED.value();
            errormsg = "用户名已存在";
        } else {
            UserEntity entity = new UserEntity();
            entity.setUsername(data.getUsername());
            entity.setPassword(data.getPassword());
            entity.setPhonenum(data.getPhonenum());
            entity.setEmail(data.getEmail());
            userDao.save(entity);
            code = ResponseData.Code.SUCCESS.value();
            errormsg = "";
        }
        responseData.setCode(code);
        responseData.setErrorMsg(errormsg);

        return responseData;
    }


    public ResponseData login(@NotNull LoginRequestData data) {

        ResponseData<UserEntity> responseData = new ResponseData();
        String errormsg;
        int code;


        UserEntity userEntity = getOneUserEntityByName(data.getUsername());

        if (null != userEntity) {
            if (userEntity.getPassword().equals(data.getPassword())) {
                code = ResponseData.Code.SUCCESS.value();
                errormsg = "登陆成功";
            } else {
                code = ResponseData.Code.FAILED.value();
                errormsg = "密码错误";
            }

        } else {
            code = ResponseData.Code.FAILED.value();
            errormsg = "用户不存在";
        }

        responseData.setCode(code);
        responseData.setErrorMsg(errormsg);
        return responseData;

    }


    /**
     * 用户是否存在
     */
    private boolean isUserExist(String name) {
        boolean isExist = false;
        List<UserEntity> list = userDao.findUserByName(name);
        isExist = null != list && !list.isEmpty();
        return isExist;
    }

    public List<UserEntity> getAllUsernames() {
        return userDao.findAll();
    }

    public List<UserEntity> getUserEntity(String name) {
        List<UserEntity> list = userDao.findUserByName(name);

        return list;
    }

    public List<UserEntity> findByPhoneNum(String phoneNum) {
        List<UserEntity> list = userDao.findUserByPhoneNum(phoneNum);

        return list;
    }

    private UserEntity getOneUserEntityByName(String name) {
        List<UserEntity> list = getUserEntity(name);
        UserEntity entity;
        if (null != list && !list.isEmpty()) {
            entity = list.get(0);
        } else {
            entity = null;
        }
        return entity;

    }
}