package com.mx.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mx.springboot.entity.Customer;
import com.mx.springboot.service.ICustomerService;
import com.mx.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.mx.springboot.entity.BaseEntity;

import java.util.Map;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author MengXiang Ma
 * @since 2021-06-10
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseEntity {

    @Autowired
    private ICustomerService customerService;


    /**
     * 进入列表页
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "customer/customerList";
    }

    /**
     * 查询列表客户数据
     * @param realName
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public R<Map<String,Object>> list(String realName,String phone,Long page,Long limit){
        LambdaQueryWrapper<Customer> wrapper = Wrappers.<Customer>lambdaQuery()
                .like(StringUtils.isNotBlank(realName), Customer::getRealName, realName)
                .like(StringUtils.isNotBlank(phone), Customer::getPhone, phone)
                .orderByDesc(Customer::getCustomerId);

        Page<Customer> customerPage = customerService.page(new Page<>(page, limit), wrapper);
        //封装到ResultUtil类中
//        HashMap<String,Object> map = new HashMap<String,Object>();
//        map.put("count",customerPage.getTotal());
//        map.put("records",customerPage.getRecords());

        return ResultUtil.buildPage(customerPage);
    }

    /**
     * 添加客户:进入新增页面
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(){
        return "customer/customerAdd";
    }


    /**
     * 新增客户
     * @param customer
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Customer customer){
        return ResultUtil.buildR(customerService.save(customer));
    }


    /**
     * 编辑页面
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){
        Customer customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "customer/customerUpdate";
    }

    /**
     * 修改客户
     * @param customer
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Customer customer){
        return ResultUtil.buildR(customerService.updateById(customer));
    }



    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id){
        return ResultUtil.buildR(customerService.removeById(id));
    }

    /**
     * 客户详情页面
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        Customer customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "customer/customerDetail";
    }

}
