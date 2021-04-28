package com.zyh.forum.controller;

import com.zyh.forum.entity.User;
import com.zyh.forum.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//注册
@Controller
public class RegisterController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registercheck")
    public String registercheck(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //随机生成一个token用来当cookies的值
        String token = UUID.randomUUID().toString();
       //user实体类
        User user = new User(); 
        user.setName(username);
        user.setPassword(password);
        user.setToken(token);
       //注册信息插入数据库
        userMapper.insert(user);
        //如果用户注册成功，则把用户信息写进session，直接跳转到主页
        if (userMapper.select(user) != null) {
            response.addCookie(new Cookie("token", token));
            return "redirect:/index";
        } else {
            //注册失败，处理方法先省略
            return "register";
        }
    }
}
