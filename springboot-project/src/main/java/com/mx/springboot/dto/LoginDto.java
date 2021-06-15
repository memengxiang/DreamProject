package com.mx.springboot.dto;


import com.mx.springboot.entity.Account;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * 重定向跳转路径
     */
    private String path;


    /**
     * 提示信息
     */
    private String msg;

    /**
     * 当前登陆人信息
     */
    private Account account;
}
