package com.mx.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号表 Mapper 接口
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 分页查询账号信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page,@Param(Constants.WRAPPER) Wrapper<Account> wrapper);

    /**
     * 根据Id查询账号信息
     * @param id
     * @return
     */
    Account selectAccountById(Long id);

}
