package com.mx.springboot.service.impl;

import com.mx.springboot.entity.Customer;
import com.mx.springboot.mapper.CustomerMapper;
import com.mx.springboot.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
