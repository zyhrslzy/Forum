package com.zyh.forum.controller;

import com.zyh.forum.dto.PageDto;
import com.zyh.forum.entity.Question;
import com.zyh.forum.entity.User;
import com.zyh.forum.mapper.NotificationMapper;
import com.zyh.forum.mapper.UserMapper;
import com.zyh.forum.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//首页
@Controller

public class IndexController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationMapper notificationMapper;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name= "search",  required= false) String search)
    {
        //查找cookies
        Cookie[] cookies = request.getCookies();//cookie为数组，从请求中获取
        if (cookies == null) {
            return "login";
        }
        User user = null;
        //存在，循环查找
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {//判断其name是否与token相等
                String token = cookie.getValue();//取得token的值
                user = userMapper.findBytoken(token);
                if (user != null) {
                	//session获取用户token
                    request.getSession().setAttribute("user", user);
                    
                    //获取未读的消息数量
                    int unreadnum=notificationMapper.getunreadcount(user.getId());
                    request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }
        }
        PageDto<?> pagination = questionService.list(search,page, size);
        model.addAttribute("pagination", pagination);

        //获取阅读量最高的十篇问题
        List<Question> questions= questionService.gettopten();
        model.addAttribute("topquestion",questions);
        return "index";
    }

}

