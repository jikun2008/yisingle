package com.yisingle.webapp.data;

import java.io.Serializable;

/**
 * Created by jikun on 17/5/1.
 */
public class ResponseData<T> implements Serializable {
    private int code = Code.FAILED.value();
    private String errorMsg = "";
    private T response;


    public ResponseData(int code, String errorMsg, T response) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.response = response;
    }

    public ResponseData() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;

    }


    public enum Code {
        // 利用构造函数传参
        SUCCESS(0), FAILED(101);

        // 定义私有变量
        private int retcode;

        Code(int retcode) {
            this.retcode = retcode;
        }

        public int value() {
            return retcode;
        }

    }
}
