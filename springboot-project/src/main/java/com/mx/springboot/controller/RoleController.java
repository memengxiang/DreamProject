package com.mx.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.entity.Account;
import com.mx.springboot.entity.Role;
import com.mx.springboot.service.IAccountService;
import com.mx.springboot.service.IResourceService;
import com.mx.springboot.service.IRoleService;
import com.mx.springboot.util.ResultUtil;
import com.mx.springboot.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.mx.springboot.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseEntity {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IAccountService accountService;

    //保存资源Id
    Long ROLEID = null;

    /**
     * 跳转到角色列表页面
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "role/roleList";
    }

    /**
     * 查询列表角色数据
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public R<Map<String,Object>> list(String roleName,Long page,Long limit){
        QueryWrapper<Role> wrapper = Wrappers.<Role>query()
                .like(StringUtils.isNotBlank(roleName),"role_name",roleName);

        Page<Role> resultPage = (Page<Role>) roleService.page(new Page<>(page, limit), wrapper);

        return ResultUtil.buildPage(resultPage);
    }

    /**
     * 进入新增角色界面
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(){
        return "role/roleAdd";
    }

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Role role){
        return ResultUtil.buildR(roleService.saveRole(role));
    }


    /**
     * 查询系统资源，共前端组件渲染
     * @return
     */
    @ResponseBody
    @GetMapping({"listResource","listResource/{roleId}","listResource/{roleId}/{flag}"})
    public R<List<TreeVo>> listResource(@PathVariable(required = false) Long roleId,@PathVariable(required = false) Integer flag){
        return R.ok(resourceService.listResource(roleId,flag));
    }

    /**
     * 进入修改角色界面
     * @return
     */
    @GetMapping("toUpdate/{roleId}")
    public String toUpdate(@PathVariable Long roleId, Model model){
        Role role = roleService.getById(roleId);
        ROLEID = roleId;
        model.addAttribute("role",role);
        return "role/roleUpdate";
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Role role){

        role.setRoleId(ROLEID);

        return ResultUtil.buildR(roleService.updateRole(role));
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id){

        //有账号拥有该角色，不允许删除
        Integer count = accountService.lambdaQuery().eq(Account::getRoleId, id).count();
        if (count > 0){
            return R.failed("有账号拥有该角色，不允许删除");
        }
        //中间的资源角色表数据暂不删除。
        return ResultUtil.buildR(roleService.removeById(id));
    }

    /**
     * 角色详情页面
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "role/roleDetail";
    }

}
