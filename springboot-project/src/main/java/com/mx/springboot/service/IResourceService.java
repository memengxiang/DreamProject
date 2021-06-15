package com.mx.springboot.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mx.springboot.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mx.springboot.vo.ResourceVo;
import com.mx.springboot.vo.TreeVo;
import org.apache.ibatis.annotations.Param;
import sun.reflect.generics.tree.Tree;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
public interface IResourceService extends IService<Resource> {
    /**
     * 查询登陆人的资源
     * @param roleID
     * @return
     */
    List<ResourceVo> listResourceByRoleId(Long roleID);

    /**
     * 查询系统资源，共前端组件渲染
     * @return
     */
    List<TreeVo> listResource(Long roleId,Integer flag);
}
