package com.mx.springboot.service;

import com.mx.springboot.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
public interface IRoleService extends IService<Role> {

    /**
     * 新增角色以及角色所拥有的资源
     * @param role
     * @return
     */
    boolean saveRole(Role role);

    /**
     * 修改角色以及角色所拥有的资源
     * @param role
     * @return
     */
    boolean updateRole(Role role);

}
