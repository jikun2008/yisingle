package com.yisingle.webapp.data;

import com.yisingle.webapp.utils.PredicateFormatString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by jikun on 17/5/2.
 */
public class LoginRequestData implements Serializable {
    @NotEmpty(message = "username不能为空")
    private String username;


    @NotEmpty(message = "password不能为空")
    private String password;


    @NotEmpty(message = "phonenum不能为空")
    @Pattern(regexp = PredicateFormatString.MOBILE, message = "请输入正确的手机号")
    private String phonenum;


    private String email = "test@qq.com";


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
