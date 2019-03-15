package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/admin")
public class AdminController {

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
    public String admin(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password){
        if(name!=null){
            User user = userMapper.selectByName(name);
            if(password.equals(user.getPassword())){
                model.addAttribute("user",user);
                return "admin/admin";
            }else{
                return "admin/login";
            }
        }else{
            return "admin/login";
        }
    }

    @RequestMapping("editArticle")
    public String editArticle(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "articleId",required = true)Integer articleId,
                        @RequestParam(value = "changed",required = true)Integer changed) {
        if(changed == 0){
            Article article = articleMapper.selectByPrimaryKey(articleId);
            if(article!=null){
                model.addAttribute("article", article);
                return "admin/edit";
            }
        }
        return "redirect:admin/?username=name&password=password";
    }

    @RequestMapping("deleteArticle")
    public String deleteArticle(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "articleId",required = true)Integer articleId) {
        if(name!=null){
            User user = userMapper.selectByName(name);
            if(user != null){
                articleMapper.deleteByPrimaryKey(articleId);
                return "redirect:admin/articleAdmin?username=name&password=password";
            }else{
                return "404";
            }
        }else{
            return "redirect:admin";
        }
        
    }

    @RequestMapping("articleAdmin")
    public String articleAdmin(Model model,
                               @RequestParam(value = "username",required = false)String name,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        if(name!=null){
            User user = userService.selectByName(name);
            if(password.equals(user.getPassword())){
                PageHelper.startPage(pageNum,10);
                List<Article> arts = articleService.getAll();
                PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("user",user);
                return "admin/articleAdmin";
            }else{
                return "404";
            }
        }else{
            return "redirect:admin";
        }
    }

    @RequestMapping("profile")
    public String profile(Model model,
                          @RequestParam(value = "username",required = false)String name,
                          @RequestParam(value = "password",required = false)String password){
        if(name!=null){
            User user = userService.selectByName(name);
            if(password.equals(user.getPassword())){
                model.addAttribute("user",user);
                return "admin/profile";
            }else{
                return "redirect:/admin";
            }
        }
        return "404";
    }
    


}
