package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Register;
import com.rain.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService registerService;

    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){

        PageHelper.startPage(pageNum,10);
        List<Article> arts = articleService.getAll();
        PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
        model.addAttribute("pageInfo",pageInfo);

        return "index";
    }

    @RequestMapping("article")
    public String article(Model model,
                          @RequestParam(value = "article_id",required = true)Integer art_id){
        Article article = articleService.selectByPrimaryKey(art_id);
        model.addAttribute("article",article);
        return "article";
    }

    @RequestMapping("register")
    public String register(Model model,
                           @RequestParam(value = "username",required = false)String name,
                           @RequestParam(value = "password",required = false)String password,
                           @RequestParam(value = "phone",required = false)Integer phone,
                           @RequestParam(value = "email",required = false)String email,
                           @RequestParam(value = "corporation",required = false)String corporation,
                           @RequestParam(value = "detail",required = false)String detail,
                           @RequestParam(value = "confirm_password",required = false)String confirm_password){
        if(name==null){
            return "register";
        }
        User user = userService.selectByName(name);
        String msg = "";
        if(user!=null){
            msg = "该账号已被注册！";
            return "register";
        }
        if(!password.equals(confirm_password)){
            msg = "密码不一样！";
            return "register";
        }
        Register register = new Register();
        register.setName(name);
        register.setPassword(password);
        register.setPhone(phone);
        register.setEmail(email);
        register.setCorporation(corporation);
        register.setStatus(1);
        register.setDetail(detail);
        registerService.insert(register);
        msg = "请求表提交成功，请等待管理员审核";
        return "register";
    }

}
