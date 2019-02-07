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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        PageHelper.startPage(pageNum,10);
        List<Article> arts = articleService.getAll();
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
                article.getContent());
        model.addAttribute("user_article",user_article);

        List<Comment> comments = commentService.getAll(art_id);
        model.addAttribute("comments",comments);
        return "article";
    }

    @RequestMapping("post")
    public String postArticle(Model model,
                              @RequestParam(value = "title",required = false)String title,
                              @RequestParam(value = "content",required = false)String content,
                              @RequestParam(value = "username",required = false)String name){
        model.addAttribute("user",userMapper.selectByName(name));

        String msg = "";
        if(title!=null && content!=null){
            Article article = new Article();
            article.setTitle(title);
            article.setUser_id(1);
            article.setCategory_id(1);
            article.setPost_time(new Date());
            article.setContent(content);
            article.setLikes(0);
            article.setComment_account(0);
            try{
                articleMapper.insert(article);
            }catch (Exception e){
                msg = "Fail";
                return "post";
            }
            return "redirect:/index";
        }
        return "post";
    }

}
