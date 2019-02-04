package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.User;
import com.rain.demo.entity.User_Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        String demo = "Not Found";
        model.addAttribute("no",demo);

        PageHelper.startPage(pageNum,5);
        List<Article> arts = articleService.getAll();
        List<User_Article> user_articles = new ArrayList();
        User user;
        for(int i=0;i<arts.size();i++){
            user = userMapper.selectByPrimaryKey(arts.get(i).getUser_id());
            user_articles.add(new User_Article(arts.get(i).getUser_id(),
                    user.getName(),
                    arts.get(i).getArticle_id(),
                    arts.get(i).getTitle(),
                    arts.get(i).getCategory_id(),
                    arts.get(i).getPost_time(),
                    arts.get(i).getLikes(),
                    arts.get(i).getComment_account(),
                    arts.get(i).getContent()));
        }
        PageInfo<User_Article> pageInfo = new PageInfo<User_Article>(user_articles);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    @RequestMapping("login")
    public String login(Model model,
                        @RequestParam(value = "user_id",required = false)Integer userId){
        if(userId!=null){
            User user = userMapper.selectByPrimaryKey(userId);
            model.addAttribute("user",user);
            return "index";
        }
        return "login";
    }

    @RequestMapping("register")
    public String register(Model model,
                           @RequestParam(value = "name",required = false)String name,
                           @RequestParam(value = "password",required = false)String password){
        if(name!=null){
            User user = new User(null,name,password,2,null,null);
            String msg = "";
            try{
                userMapper.insert(user);
                msg = "Success";
            }catch (Exception e){
                msg = "Fail";
                model.addAttribute("msg",msg);
                return "register";
            }
            model.addAttribute("msg",msg);
            return "result";
        }
        return "register";
    }
}
