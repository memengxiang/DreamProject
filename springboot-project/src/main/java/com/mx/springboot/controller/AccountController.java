package com.mx.springboot.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.entity.Account;
import com.mx.springboot.entity.Role;
import com.mx.springboot.req.AccountQueryReq;
import com.mx.springboot.service.IAccountService;
import com.mx.springboot.service.IRoleService;
import com.mx.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.mx.springboot.entity.BaseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号表 前端控制器
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseEntity {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    /**
     * 跳转到账户列表页面
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "account/accountList";
    }

    /**
     * 查询列表账户数据
     * @param accountQueryReq
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public R<Map<String,Object>> list(AccountQueryReq accountQueryReq){

        QueryWrapper<Account> wrapper = Wrappers.<Account>query();

        wrapper.like(StringUtils.isNotBlank(accountQueryReq.getRealName()),"a.real_name",accountQueryReq.getRealName())
                .like(StringUtils.isNotBlank(accountQueryReq.getEmail()),"a.email",accountQueryReq.getEmail());
        String createTimeRange = accountQueryReq.getCreateTimeRange();
        if (StringUtils.isNotBlank(createTimeRange)){
            String[] split = createTimeRange.split(" - ");
            //  ge相当于大于等于   le 相当于小于等于
            wrapper.ge("a.create_time",split[0]).le("a.create_time",split[1]);
        }
        wrapper.eq("a.deleted",0);
        IPage<Account> accountPage = accountService.accountPage(new Page<>(accountQueryReq.page, accountQueryReq.limit), wrapper);

        return ResultUtil.buildPage(accountPage);
    }

    /**
     * 添加账户:进入新增页面
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(Model model){
        //查询所有角色
        List<Role> roles = roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
        model.addAttribute("roles",roles);
        return "account/accountAdd";
    }


    /**
     * 新增账户信息
     * @param account
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Account account){

        setPasswordAndSalt(account);

        return ResultUtil.buildR(accountService.save(account));
    }

    /**
     * 设计加密密码和加密盐
     * @param account
     */
    public void setPasswordAndSalt(Account account){
        String password = account.getPassword();
        String salt = UUID.fastUUID().toString().replace("-", "");
        MD5 md5 = new MD5(salt.getBytes());
        String digestHex = md5.digestHex(password);
        account.setPassword(digestHex);
        account.setSalt(salt);
    }


    /**
     * 编辑页面
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){

        Account account = accountService.getById(id);
        model.addAttribute("account",account);

        //查询所有角色
        List<Role> roles = roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
        model.addAttribute("roles",roles);
        return "account/accountUpdate";
    }

    /**
     * 修改客户
     * @param account
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Account account){

        if (StringUtils.isNotBlank(account.getPassword())){
            setPasswordAndSalt(account);
        }else {
            account.setPassword(null);
        }

        return ResultUtil.buildR(accountService.updateById(account));
    }



    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id, HttpSession session){
        Account account = (Account) session.getAttribute("account");

        if (account.getAccountId().equals(id)){
            return R.failed("当前账号已经登陆，不能删除!");
        }

        return ResultUtil.buildR(accountService.removeById(id));
    }

    /**
     * 客户详情页面
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        Account account = accountService.getAccountById(id);
        model.addAttribute("account",account);
        return "account/accountDetail";
    }

    /**
     * 检测账号是否存在
     * @param username
     * @return
     */
    @GetMapping({"/{username}","/{username}/{accountId}"})
    @ResponseBody
    public R<Object> checkUsername(@PathVariable String username,@PathVariable(required = false) Long accountId){
        Integer count = accountService.lambdaQuery()
                .eq(Account::getUsername, username)
                .ne(accountId!= null,Account::getAccountId,accountId)
                .count();

        return R.ok(count);
    }

}
