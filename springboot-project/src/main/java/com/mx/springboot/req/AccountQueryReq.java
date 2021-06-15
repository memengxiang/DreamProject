package com.mx.springboot.req;


import lombok.Data;

@Data
public class AccountQueryReq {
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 时间范围
     */
    public String createTimeRange;

    /**
     * 当前页
     */
    public Long page;

    /**
     * 每页的条数
     */
    public Long limit;


}
