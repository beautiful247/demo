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

    @RequestMapping("article")
    public String getArticle(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum,@RequestParam(value = "articleId",required = false)Integer art_id){
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

        PageHelper.startPage(pageNum,5);
        List<Comment> comments = commentService.getAll(art_id);
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(comments);
        model.addAttribute("pageInfo",pageInfo);
        return "article";
    }
}
