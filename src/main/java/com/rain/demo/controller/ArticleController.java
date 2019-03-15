package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Comment;
import com.rain.demo.entity.User;
import com.rain.demo.entity.User_Article;
import org.hibernate.validator.constraints.pl.REGON;
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
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

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
        Article article = articleMapper.selectByPrimaryKey(art_id);
        model.addAttribute("article",article);
        return "article";
    }

}
