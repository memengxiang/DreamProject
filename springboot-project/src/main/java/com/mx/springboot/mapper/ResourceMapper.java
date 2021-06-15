package com.mx.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mx.springboot.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mx.springboot.vo.ResourceVo;
import com.mx.springboot.vo.TreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询登陆人的资源
     * @param wrapper
     * @return
     */
    List<ResourceVo> listResource(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<TreeVo> listResourceByRoleId(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("roleId") Long roleId);

}
