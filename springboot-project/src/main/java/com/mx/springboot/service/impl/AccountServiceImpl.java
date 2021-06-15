package com.mx.springboot.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.dto.LoginDto;
import com.mx.springboot.entity.Account;
import com.mx.springboot.mapper.AccountMapper;
import com.mx.springboot.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    /**
     * 用户登陆
     * @param username
     * @param password
     */
    @Override
    public LoginDto login(String username, String password) {
        LoginDto loginDto = new LoginDto();
        loginDto.setPath("redirect:/");//重定向
        //验证用户名
        Account account = lambdaQuery().eq(Account::getUsername, username).one();
        if (account == null){
            loginDto.setMsg("用户名不存在！");
            return loginDto;
        }
        //验证加密密码
        MD5 md5 = new MD5(account.getSalt().getBytes());
        String digestHex = md5.digestHex(password);
        if (! digestHex.equals(account.getPassword())){
            loginDto.setMsg("密码错误！");
            return loginDto;
        }

        loginDto.setAccount(account);
        loginDto.setPath("main");//设置根目录下的主页
        return loginDto;
    }


    /**
     * 分页查询账号信息
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper) {
        return baseMapper.accountPage(page,wrapper);
    }

    /**
     * 根据Id查询账号信息
     * @param id
     * @return
     */
    @Override
    public Account getAccountById(Long id) {
        return baseMapper.selectAccountById(id);
    }
}
