package com.mx.springboot.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.dto.LoginDto;
import com.mx.springboot.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
public interface IAccountService extends IService<Account> {


    /**
     * 用户登陆
     * @param username
     * @param password
     */
    LoginDto login(String username, String password);

    /**
     * 分页查询账号信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper);

    /**
     * 根据Id查询账号信息
     * @param id
     * @return
     */
    Account getAccountById(Long id);

}
