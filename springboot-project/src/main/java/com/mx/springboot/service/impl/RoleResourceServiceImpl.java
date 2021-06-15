package com.mx.springboot.service.impl;

import com.mx.springboot.entity.RoleResource;
import com.mx.springboot.mapper.RoleResourceMapper;
import com.mx.springboot.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

}
