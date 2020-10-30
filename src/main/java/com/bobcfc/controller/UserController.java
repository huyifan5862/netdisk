package com.bobcfc.controller;

import com.bobcfc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String uname,String upassword){
        Subject subject = SecurityUtils.getSubject();
        Map map = new HashMap<>();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(uname, upassword);
        try {
            subject.login(usernamePasswordToken);
            map.put("code","200");
            map.put("msg","登录成功!");
            return map;
        }catch (Exception e){
            map.put("code","400");
            map.put("msg","用户名密码不匹配!");
            return map;
        }


    }

    @RequestMapping("/tohome")
    public String tohome(HttpSession session){
        session.setAttribute("path","/root");
        return "home";
    }




}
