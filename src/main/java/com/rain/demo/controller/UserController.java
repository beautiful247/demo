package com.rain.demo.controller;

import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("login")
    public String login(Model model,
                        @RequestParam(value = "name",required = false)String name,
                        @RequestParam(value = "password",required = false)String password){
        String msg = "";
        if(name!=null){
            User user = userMapper.selectByName(name);
            if(user.getPassword().equals(password)){
                model.addAttribute("user",user);
                return "redirect:/?username="+user.getName();
            }else{
                msg="Password Error!";
                model.addAttribute("msg",msg);
                return "login";
            }
        }
        return "login";
    }

    @RequestMapping("register")
    public String register(Model model,
                           @RequestParam(value = "name",required = false)String name,
                           @RequestParam(value = "password",required = false)String password){
        String msg = "";
        if(name!=null){
            User user = new User(null,name,password,2,null,null);
            try{
                userMapper.insert(user);
                msg = "Success";
            }catch (Exception e){
                msg = "Fail";
                model.addAttribute("msg",msg);
                return "register";
            }
            model.addAttribute("msg",msg);
            return "redirect:/";
        }
        return "register";
    }
}
