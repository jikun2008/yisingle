package com.yisingle.webapp.data;


import java.io.Serializable;

/**
 * Created by jikun on 17/7/21.
 */

public class SocketData<T> implements Serializable {


    private int type;

    private int code;

    private String msg;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private T response;


    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public SocketData() {
        super();
    }

    @Override
    public String toString() {
        return "SocketData{" +
                "type=" + getType() +
                ", code=" + getCode() +
                ", msg='" + getMsg() + '\'' +
                ", response=" + response +
                '}';
    }
}
