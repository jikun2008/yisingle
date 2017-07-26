package com.yisingle.webapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yisingle.webapp.utils.PredicateFormatString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;
@JsonIgnoreProperties({ "setOrderEntity"})
@Entity
@Table(name = "t_user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "username不能为空")
    private String username;


    @NotEmpty(message = "password不能为空")
    private String password;

    @NotEmpty(message = "phonenum不能为空")
    @Pattern(regexp = PredicateFormatString.MOBILE, message = "请输入正确的手机号")
    private String phonenum;

    @NotEmpty
    @Pattern(regexp = PredicateFormatString.EMAIL, message = "请输入正确的邮箱")
    private String email;

    private String verifycode;

    private Long time;

    @JsonIgnore
    @OneToMany
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "userid")
    private Set<OrderEntity> setOrderEntity;


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

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Set<OrderEntity> getSetOrderEntity() {
        return setOrderEntity;
    }

    public void setSetOrderEntity(Set<OrderEntity> setOrderEntity) {
        this.setOrderEntity = setOrderEntity;
    }
}