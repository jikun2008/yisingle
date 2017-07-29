package com.yisingle.webapp.data;

import java.io.Serializable;

/**
 * Created by jikun on 17/7/24.
 */
public class SocketHeadData  implements Serializable {



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


    public  enum Type {
        // 利用构造函数传参
        ILLEGAL_DATA(-1),HEART_BEAT(1),ORDER_NEW(2),ORDER_PRICE(3);

        // 定义私有变量
        private int code;

        Type(int code) {
            this.code = code;
        }

        public int value() {
            return code;
        }

    }


}
