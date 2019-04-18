package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Service.*;
import com.rain.demo.entity.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){

        List<User> users = userService.getAll();
        model.addAttribute("users",users);
        PageHelper.startPage(pageNum,10);
        List<Article> arts = articleService.getAll();
        PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
        List<Category> categories = categoryService.getAllCat();
        model.addAttribute("categories",categories);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    @RequestMapping("searchByEditors")
    public String searchByEditors(Model model,
                                  @RequestParam(value = "editorId",required = false)Integer user_id){
        User user = userService.selectByPrimaryKey(user_id);
        List<User> users = userService.getAll();
        model.addAttribute("users",users);
        List<Category> categories = categoryService.getAllCat();
        model.addAttribute("categories",categories);
        List<Article> articles = articleService.selectByAuthor(user.getName());
        model.addAttribute("articles",articles);
        return "searchByEditor";
    }

    @RequestMapping("article")
    public String article(Model model,
                          @RequestParam(value = "article_id",required = true)Integer art_id){
        Article article = articleService.selectByPrimaryKey(art_id);
        List<Comment> comments = commentService.getAll(art_id);
        Category category = categoryService.selectByPrimaryKey(article.getCategory());
        model.addAttribute("category",category);
        model.addAttribute("comments",comments);
        model.addAttribute("article",article);
        return "article";
    }

    @RequestMapping("searchByCategory")
    public String searchByCategory(Model model,
                                   @RequestParam(value = "categoryId",required = true)Integer cat_id){
        List<Article> articles = articleService.getByCategory(cat_id);
        model.addAttribute("articles",articles);
        return "searchByCategory";
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

    @RequestMapping("comment_post")
    public String postComment(Model model,
                            @RequestParam(value = "article_id",required = false)Integer art_id,
                            @RequestParam(value = "user",required = false)String user,
                            @RequestParam(value = "comment_content",required = false)String content){
        Article article = articleService.selectByPrimaryKey(art_id);
        Comment comment = new Comment();
        comment.setArticle_id(art_id);
        comment.setComm_time(new Date());
        comment.setContent(content);
        comment.setUser_name(user);
        commentService.insert(comment);
        article.setComment_account(article.getComment_account()+1);
        articleService.updateByPrimaryKey(article);
        return "redirect:/article?article_id="+art_id;
    }

}
