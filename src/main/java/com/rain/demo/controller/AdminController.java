package com.rain.demo.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.CategoryMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Category;
import com.rain.demo.entity.Register;
import com.rain.demo.entity.User;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CategoryMapper categoryMapper;

    @RequestMapping("")
    public String admin(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password){
        if(name!=null){
            User user = userService.selectByName(name);
            if(user==null){
                return "admin/login";
            }
            if(password.equals(password)){
                model.addAttribute("user",user);
                return "admin/admin";
            }
        }
        return "admin/login";
    }

    @RequestMapping("postArticle")
    public String editArticle(Model model,
                              @RequestParam(value = "username",required = false)String name,
                              @RequestParam(value = "password",required = false)String password,
                              @RequestParam(value = "title",required = false)String title,
                              @RequestParam(value = "category_id",required = false)Integer cat_id,
                              @RequestParam(value = "digit",required = false)String digit,
                              @RequestParam(value = "editormd-html-textarea",required = false)String content,
                              @RequestParam(value = "editormd-markdown-textarea",required = false)String md_content){
        User user = userService.selectByName(name);
        if(title==null){
            if(user!=null){
                if(password.equals(user.getPassword())){
                    model.addAttribute("cats",categoryMapper.getAllCat());
                    model.addAttribute("user",user);
                    return "admin/post";
                }else{
                    return "404";
                }
            }else{
                return "redirect:/admin";
            }
        }else {
            if(user!=null){
                if(password.equals(user.getPassword())){
                    Article article = new Article();
                    article.setDigit(digit);
                    article.setTitle(title);
                    article.setCategory(cat_id);
                    article.setAuthor(name);
                    article.setLikes(0);
                    article.setComment_account(0);
                    article.setContent(content);
                    article.setMd_content(md_content);
                    article.setPost_time(new Date());
                    articleService.insert(article);
                    return "redirect:/admin?username=" + name + "&password=" + password;
                }else{
                    return "404";
                }
            }else{
                return "redirect:/admin";
            }
        }
    }

    //编辑文章
    @RequestMapping("editArticle")
    public String editArticle(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "articleId",required = false)Integer art_id,
                        @RequestParam(value = "title",required = false)String title,
                        @RequestParam(value = "category_id",required = false)Integer cat_id,
                        @RequestParam(value = "digit",required = false)String digit,
                        @RequestParam(value = "editormd-html-textarea",required = false)String content,
                        @RequestParam(value = "editormd-markdown-textarea",required = false)String md_content) {
        User user = userService.selectByName(name);
        if(user==null){
            return "redirect:/admin";
        }
        if(!password.equals(user.getPassword())||articleService.selectByPrimaryKey(art_id)==null){
            return "404";
        }
        if(!articleService.selectByPrimaryKey(art_id).getAuthor().equals(name)){
            return "404";
        }
        model.addAttribute("user",user);
        model.addAttribute("article",articleService.selectByPrimaryKey(art_id));
        model.addAttribute("category",categoryMapper.getAllCat());
        return "/admin/editArticle";
    }

    @RequestMapping("changeArticle")
    public String changeArticle(Model model,
                              @RequestParam(value = "username",required = false)String name,
                              @RequestParam(value = "password",required = false)String password,
                              @RequestParam(value = "articleId",required = false)Integer art_id,
                              @RequestParam(value = "title",required = false)String title,
                              @RequestParam(value = "category_id",required = false)Integer cat_id,
                              @RequestParam(value = "digit",required = false)String digit,
                              @RequestParam(value = "editormd-html-textarea",required = false)String content,
                              @RequestParam(value = "editormd-markdown-textarea",required = false)String md_content) {

        Article article = articleService.selectByPrimaryKey(art_id);
        article.setTitle(title);
        article.setCategory(cat_id);
        article.setDigit(digit);
        article.setContent(content);
        article.setMd_content(md_content);
        articleService.updateByPrimaryKey(article);
        return "redirect:/admin?username="+name+"&password="+password;
    }

    //删除文章
    @RequestMapping("deleteArticle")
    public String deleteArticle(Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "articleId",required = true)Integer articleId) {
        if(name!=null){
            User user = userMapper.selectByName(name);
            if(user != null && password.equals(user.getPassword())){
                articleMapper.deleteByPrimaryKey(articleId);
                return "redirect:/admin/articleAdmin?username="+name+"&password="+password;
            }else{
                return "404";
            }
        }else{
            return "redirect:admin";
        }
        
    }

    //文章管理
    @RequestMapping("articleAdmin")
    public String articleAdmin(Model model,
                               @RequestParam(value = "username",required = false)String name,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        if(name!=null){
            User user = userService.selectByName(name);
            if(user==null){
                return "admin/login";
            }
            if(password.equals(user.getPassword())){
                PageHelper.startPage(pageNum,10);
                List<Article> arts = articleService.selectByAuthor(user.getName());
                PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("user",user);
                return "admin/articleAdmin";
            }else{
                return "404";
            }
        }else{
            return "redirect:/admin";
        }
    }

    //用户个人信息
    @RequestMapping("profile")
    public String profile(Model model,
                          @RequestParam(value = "username",required = false)String name,
                          @RequestParam(value = "password",required = false)String password){
        if(name!=null){
            User user = userService.selectByName(name);
            if(user==null){
                return "admin/login";
            }
            if(password.equals(user.getPassword())){
                model.addAttribute("user",user);
                return "admin/profile";
            }else{
                return "redirect:/admin";
            }
        }
        return "404";
    }

    //注册申请管理
    @RequestMapping("registerAdmin")
    public String registerAdmin(Model model,
                                @RequestParam(value = "username",required = false)String name,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum) {
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (password.equals(user.getPassword())) {
                model.addAttribute("user", user);
                PageHelper.startPage(pageNum,10);
                List<Register> registers = registerService.getAllReg();
                PageInfo<Register> pageInfo = new PageInfo<Register>(registers);
                model.addAttribute("pageInfo",pageInfo);
                return "admin/registerAdmin";
            }
        }
        return "404";

    }

    //同意注册申请
    @RequestMapping("agreeRegister")
    public String agreeRegister(Model model,
                                @RequestParam(value = "username",required = false)String name,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(value = "registerId",required = false)Integer register_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/";
        } else {
            if (password.equals(user.getPassword())) {
                Register register = registerService.selectByPrimaryKey(register_id);
                register.setStatus(1);
                User new_user = new User();
                new_user.setName(register.getName());
                new_user.setPassword(register.getPassword());
                new_user.setRole_id(1);
                new_user.setPhone(register.getPhone());
                new_user.setEmail(register.getEmail());
                new_user.setCorporation(register.getCorporation());
                userService.insert(new_user);
                return "redirect:/admin/userAdmin?username="+name+"&password="+password;
            }
        }
        return "404";
    }

    //拒绝注册申请
    @RequestMapping("denyRegister")
    public String denyRegister(Model model,
                               @RequestParam(value = "username",required = false)String name,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(value = "registerId",required = false)Integer register_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (password.equals(user.getPassword())) {
                Register register = registerService.selectByPrimaryKey(register_id);
                register.setStatus(2);
                return "redirect:/admin/userAdmin?username="+name+"&password="+password;
            }
        }
        return "404";
    }

    //用户管理
    @RequestMapping("userAdmin")
    public String userAdmin(Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        User user = userService.selectByName(name);
        if(user!=null){
            if(password.equals(user.getPassword())){
                model.addAttribute("user",user);
                PageHelper.startPage(pageNum,10);
                List<User> users = userService.getAll();
                PageInfo<User> pageInfo = new PageInfo<User>(users);
                model.addAttribute("pageInfo",pageInfo);
                return "/admin/userAdmin";
            }else{
                return "redirect:/admin";
            }

        }else{
            return "redirect:/admin";
        }
    }

    //跳转到修改用户信息
    /*
    @RequestMapping("alterUser")
    public String alterUser(Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(value = "userId",required = false)Integer user_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (password.equals(user.getPassword())) {
                User alter_user = userService.selectByPrimaryKey(user_id);
                model.addAttribute("alter_user",alter_user);
                return "admin/alterUser";
            }
        }
        return "404";
    }
    */

    @RequestMapping("changeProfile")
    public String changeProfile(Model model,
                                @RequestParam(value = "user_id",required = true)Integer userId,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(value = "phone",required = false)Integer phone,
                                @RequestParam(value = "email",required = false)String email,
                                @RequestParam(value = "corporation",required = false)String corporation){
        User user = userService.selectByPrimaryKey(userId);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setCorporation(corporation);
        userService.updateByPrimaryKey(user);
        return "redirect:admin";
    }

    //删除用户
    @RequestMapping("deleteUser")
    public String deleteUser(Model model,
                             @RequestParam(value = "username",required = false)String name,
                             @RequestParam(value = "password",required = false)String password,
                             @RequestParam(value = "userId",required = false)Integer user_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (password.equals(user.getPassword())) {
                userService.deleteByPrimaryKey(user_id);
                return "redirect:/admin/userAdmin?username="+name+"&password="+password;
            }
        }
        return "404";
    }

    @RequestMapping("uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam(value = "editormd-image-file",required = false)MultipartFile file,
                                           HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String filePath = "C:\\Users\\Ammaz\\IdeaProjects\\demo\\src\\main\\resources\\static\\imgs\\";
        String imgPath = "../imgs/";
        String fileName = file.getOriginalFilename();

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath+fileName);
            Files.write(path,bytes);
            resultMap.put("success",1);
            resultMap.put("message","上传成功！");
            resultMap.put("url",imgPath+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("success",0);
            resultMap.put("message","Failed!");
        }

        return resultMap;
    }

}
