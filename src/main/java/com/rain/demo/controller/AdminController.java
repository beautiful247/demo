package com.rain.demo.controller;

import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("")
    public String admin(Model model,
                        @RequestParam(value = "username",required = true)String name){
        model.addAttribute("user",userMapper.selectByName(name));
        return "admin/admin";
    }

    @RequestMapping("post")
    public String postArticle(Model model,
                              @RequestParam(value = "title",required = false)String title,
                              @RequestParam(value = "editormd-markdown-textarea",required = false)String markdownContent,
                              @RequestParam(value = "editormd-html-textarea",required = false)String htmlContent,
                              @RequestParam(value = "username",required = false)String name){
        model.addAttribute("user",userMapper.selectByName(name));
        String msg = "";
        if(title!=null && htmlContent!=null){
            Article article = new Article();
            article.setTitle(title);
            article.setUser_id(1);
            article.setCategory_id(1);
            article.setPost_time(new Date());
            article.setContent(htmlContent);
            article.setLikes(0);
            article.setComment_account(0);
            article.setMd_content(markdownContent);
            try{
                articleMapper.insert(article);
            }catch (Exception e){
                msg = "Fail";
                return "admin/post";
            }
           return "redirect:/?username="+name;
        }
        return "admin/post";
    }
}
