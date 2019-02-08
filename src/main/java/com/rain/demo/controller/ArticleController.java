package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Comment;
import com.rain.demo.entity.User;
import com.rain.demo.entity.User_Article;
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
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        /*
        String demo = "Not Found";
        model.addAttribute("no",demo);
        */
        List<Article> arts = articleService.getAll();

        Collections.sort(arts, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                if(o1.getArticle_id() > o2.getArticle_id()){
                    return -1;
                }
                if(o1.getArticle_id() == o2.getArticle_id()){
                    return 0;
                }
                return 1;
            }
        });
        PageHelper.startPage(pageNum,10);


        PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
        model.addAttribute("pageInfo",pageInfo);
        if(name!=null){
            model.addAttribute("user",userMapper.selectByName(name));
        }
        return "index";
    }

    @RequestMapping("article")
    public String getArticle(Model model,
                             @RequestParam(value = "articleId",required = false)Integer art_id){

        Article article = articleMapper.selectByPrimaryKey(art_id);
        User user = userMapper.selectByPrimaryKey(article.getUser_id());

        User_Article user_article = new User_Article(article.getUser_id(),
                user.getName(),
                article.getArticle_id(),
                article.getTitle(),
                article.getCategory_id(),
                article.getPost_time(),
                article.getLikes(),
                article.getComment_account(),
                article.getContent(),
                article.getMd_content());

        model.addAttribute("user_article",user_article);

        List<Comment> comments = commentService.getAll(art_id);
        model.addAttribute("comments",comments);

        return "article";
    }

}
