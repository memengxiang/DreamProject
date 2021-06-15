package com.mx.springboot.controller;


import com.mx.springboot.dto.LoginDto;
import com.mx.springboot.service.IAccountService;
import com.mx.springboot.service.IResourceService;
import com.mx.springboot.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IResourceService resourceService;

    /**
     * 用户登陆功能
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(String username, String password, HttpSession httpSession,
                        RedirectAttributes redirectAttributes, Model model){

        LoginDto loginDto = accountService.login(username, password);
        String msg = loginDto.getMsg();
        if (msg == null){
            httpSession.setAttribute("account",loginDto.getAccount());
            List<ResourceVo> resourceVos = resourceService.listResourceByRoleId(loginDto.getAccount().getRoleId());
            model.addAttribute("resources",resourceVos);
        }else {
            redirectAttributes.addFlashAttribute("msg",msg);
        }
        return loginDto.getPath();
    }

    /**
     * 退出登陆方法
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/";

    }
}
