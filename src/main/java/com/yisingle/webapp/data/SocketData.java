package com.yisingle.webapp.data;

import java.io.Serializable;

/**
 * Created by jikun on 17/7/21.
 */
public class SocketData<T> extends SocketHeadData implements Serializable {


    private T response;


    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
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
