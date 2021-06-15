package com.mx.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mx.springboot.entity.Resource;
import com.mx.springboot.mapper.ResourceMapper;
import com.mx.springboot.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mx.springboot.vo.ResourceVo;
import com.mx.springboot.vo.TreeVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public List<ResourceVo> listResourceByRoleId(Long roleID) {
        //查询第一级目录
        QueryWrapper<ResourceVo> query = Wrappers.query();
        query.eq("rr.role_id",roleID).isNull("re.parent_id").orderByAsc("re.sort");
        List<ResourceVo> resourceVos = baseMapper.listResource(query);
        //查询父目录下的子目录
        resourceVos.forEach(r->{
            Long resourceId = r.getResourceId();
            QueryWrapper<Object> subWrapper = Wrappers.query();
            subWrapper.eq("rr.role_id",roleID).eq("re.parent_id",resourceId).orderByAsc("re.sort");
            List<ResourceVo> subResource = baseMapper.listResource(subWrapper);
            if (CollectionUtils.isNotEmpty(subResource)){
                r.setChildren(subResource);
            }
        });
        return resourceVos;
    }


    /**
     * 查询系统资源，共前端组件渲染
     * @return
     */
    @Override
    public List<TreeVo> listResource(Long roleId,Integer flag) {

        //新增
        if (roleId == null){
            LambdaQueryWrapper<Resource> wrapper = Wrappers.<Resource>lambdaQuery().isNull(Resource::getParentId)
                    .orderByAsc(Resource::getSort);

            List<Resource> resources = list(wrapper);

            List<TreeVo> treeVos = resources.stream().map(r -> {
                TreeVo treeVo = new TreeVo();
                treeVo.setId(r.getResourceId());
                treeVo.setTitle(r.getResourceName());

                LambdaQueryWrapper<Resource> subWrapper = Wrappers.<Resource>lambdaQuery().eq(Resource::getParentId, r.getResourceId())
                        .orderByAsc(Resource::getSort);

                List<Resource> subResources = list(subWrapper);
                if (CollectionUtils.isNotEmpty(subResources)) {
                    List<TreeVo> children = subResources.stream().map(sub -> {
                        TreeVo subTreeVo = new TreeVo();
                        subTreeVo.setId(sub.getResourceId());
                        subTreeVo.setTitle(sub.getResourceName());
                        return subTreeVo;
                    }).collect(Collectors.toList());
                    treeVo.setChildren(children);
                }
                return treeVo;
            }).collect(Collectors.toList());
            return treeVos;
        }else {
            //修改方法查询所有资源
            QueryWrapper<Resource> query = Wrappers.query();
            query.eq(flag == 1,"rr.role_id",roleId)
                    .isNull("re.parent_id").orderByAsc("re.sort");
            List<TreeVo> treeVos = baseMapper.listResourceByRoleId(query, roleId);
            treeVos.forEach(t->{
                t.setChecked(false);
                Long id = t.getId();
                QueryWrapper<Object> subWrapper = Wrappers.query();
                subWrapper.eq(flag == 1,"rr.role_id",roleId).eq("re.parent_id",id).orderByAsc("re.sort");
                List<TreeVo> children = baseMapper.listResourceByRoleId(subWrapper, roleId);
                if (CollectionUtils.isNotEmpty(children)){
                    t.setChildren(children);
                }
            });
            return treeVos;
        }
    }
}
