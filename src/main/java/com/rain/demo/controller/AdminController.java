package com.rain.demo.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.CategoryMapper;
import com.rain.demo.Dao.RegisterMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.*;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private RegisterMapper registerMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    //登录
    @RequestMapping("")
    public String admin(HttpSession httpSession,
                        HttpServletResponse httpServletResponse,
                        HttpServletRequest httpServletRequest,
                        Model model,
                        @RequestParam(value = "username",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "autoLogin",required = false)boolean auto){


        //若当前缓存中已有账号登录 跳转到管理页面
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("CookieId")){
                    httpServletResponse.addCookie(cookie);
                    model.addAttribute("user",userService.selectByName(cookie.getValue()));

                    //加入统计信息
                    List<ArticleHeat> heats = articleService.anaHeat();
                    model.addAttribute("heats",heats);
                    return "admin/admin";
                }
            }
        }

        //提交登录表单验证
        if(name!=null){
            User user = userService.selectByName(name);
            if(user==null){
                return "admin/login";
            }
            if(password.equals(password)){
                Cookie cookie = new Cookie("CookieId",name);
                cookie.setPath("/");
                if(auto){
                    cookie.setMaxAge(24*60*60*30);
                }else{
                }
                httpServletResponse.addCookie(cookie);
                model.addAttribute("user",user);
                return "admin/admin";
            }
        }
        return "admin/login";
    }

    //登出
    @RequestMapping("logout")
    public String logout(HttpServletResponse httpServletResponse,
                         HttpServletRequest httpServletRequest){
        Cookie[] cookies =  httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                cookie.setMaxAge(0);
                return "redirect:/admin";
            }
        }
        return "redirect:/admin";
    }

    //发表文章
    @RequestMapping("postArticle")
    public String editArticle(HttpServletResponse httpServletResponse,
                              HttpServletRequest httpServletRequest,
                              Model model,
                              @RequestParam(value = "username",required = false)String name,
                              //@RequestParam(value = "password",required = false)String password,
                              @RequestParam(value = "title",required = false)String title,
                              @RequestParam(value = "category_id",required = false)Integer cat_id,
                              @RequestParam(value = "digit",required = false)String digit,
                              @RequestParam(value = "editormd-html-textarea",required = false)String content,
                              @RequestParam(value = "editormd-markdown-textarea",required = false)String md_content){

        //当前缓存中存在已经登录的用户
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                model.addAttribute("user",userService.selectByName(cookie.getValue()));
                if(title==null){
                    model.addAttribute("cats",categoryMapper.getAllCat());
                    return "admin/post";
                }else{
                    //发表文章
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
                }
                return "redirect:/admin";
            }
        }
        //若当前无用户登录
        return "redirect:/admin";
        /*
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
        */
    }

    //编辑文章
    @RequestMapping("editArticle")
    public String editArticle(HttpServletResponse httpServletResponse,
                              HttpServletRequest httpServletRequest,
                              Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(value = "articleId",required = false)Integer art_id,
                            @RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "category_id",required = false)Integer cat_id,
                            @RequestParam(value = "digit",required = false)String digit,
                            @RequestParam(value = "editormd-html-textarea",required = false)String content,
                            @RequestParam(value = "editormd-markdown-textarea",required = false)String md_content) {
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                if(cookie.getValue().equals(name)){
                    User user = userService.selectByName(cookie.getValue());
                    model.addAttribute("user",user);

                    model.addAttribute("article",articleService.selectByPrimaryKey(art_id));
                    model.addAttribute("category",categoryMapper.getAllCat());
                    return "/admin/editArticle";
                }
            }
        }

        return "redirect:/admin";
    }

    @RequestMapping("changeArticle")
    public String changeArticle(HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest,
                                Model model,
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
        article.setMd_content(md_content);
        articleService.updateByPrimaryKey(article);
        return "redirect:/admin";
    }

    //删除文章
    @RequestMapping("deleteArticle")
    public String deleteArticle(HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest,
                                Model model,
                        @RequestParam(value = "articleId",required = true)Integer articleId) {
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                Article article = articleService.selectByPrimaryKey(articleId);
                if(!cookie.getValue().equals(article.getAuthor())){
                    return "404";
                }
                articleService.deleteArt(articleId);
                return "redirect:/admin/articleAdmin";
            }
        }
        return "redirect:/admin";
    }

    //文章管理
    @RequestMapping("articleAdmin")
    public String articleAdmin(HttpServletResponse httpServletResponse,
                               HttpServletRequest httpServletRequest,
                               Model model,
                               @RequestParam(value = "username",required = false)String name,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                User user = userService.selectByName(cookie.getValue());
                PageHelper.startPage(pageNum,10);
                List<Article> arts = articleService.selectByAuthor(user.getName());
                PageInfo<Article> pageInfo = new PageInfo<Article>(arts);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("user",user);
                return "admin/articleAdmin";
            }
        }
        return "redirect:/admin";
        /*
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
        */
    }

    //用户个人信息
    @RequestMapping("profile")
    public String profile(HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          Model model,
                          @RequestParam(value = "userId",required = false)Integer userId,
                          @RequestParam(value = "username",required = false)String name,
                          @RequestParam(value = "password",required = false)String password){
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                User user = userService.selectByName(cookie.getValue());
                model.addAttribute("user",user);
                return "admin/profile";
            }
        }
        return "redirect:/admin";
        /*
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
        */
    }

    //注册申请管理
    @RequestMapping("registerAdmin")
    public String registerAdmin(HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest,
                                Model model,
                                @RequestParam(value = "username",required = false)String name,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum) {
        Cookie[] cookies =  httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("CookieId")){
                model.addAttribute("user", userService.selectByName(cookie.getValue()));
                PageHelper.startPage(pageNum,10);
                List<Register> registers = registerService.getAllReg();
                PageInfo<Register> pageInfo = new PageInfo<Register>(registers);
                model.addAttribute("pageInfo",pageInfo);
                return "admin/registerAdmin";
            }
        }
        return "redirect:/admin";
        /*
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
         */
    }

    //同意注册申请
    @RequestMapping("agreeRegister")
    public String agreeRegister(HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest,
                                Model model,
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
                registerMapper.updateByPrimaryKey(register);
                User new_user = new User();
                new_user.setName(register.getName());
                new_user.setPassword(register.getPassword());
                new_user.setRole_id(2);
                new_user.setPhone(register.getPhone());
                new_user.setEmail(register.getEmail());
                new_user.setCorporation(register.getCorporation());
                userService.insert(new_user);
                registerService.sendMail(new_user.getEmail(),new_user);
                return "redirect:/admin/userAdmin";
            }
        }
        return "404";
    }

    //拒绝注册申请
    @RequestMapping("denyRegister")
    public String denyRegister(HttpServletResponse httpServletResponse,
                               HttpServletRequest httpServletRequest,
                               Model model,
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
                registerMapper.updateByPrimaryKey(register);
                return "redirect:/admin/registerAdmin";
            }
        }
        return "404";
    }

    //用户管理
    @RequestMapping("userAdmin")
    public String userAdmin(HttpServletResponse httpServletResponse,
                            HttpServletRequest httpServletRequest,
                            Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("CookieId")){
                User user = userService.selectByName(cookie.getValue());
                model.addAttribute("user",user);
                PageHelper.startPage(pageNum,10);
                List<User> users = userService.getAll();
                PageInfo<User> pageInfo = new PageInfo<User>(users);
                model.addAttribute("pageInfo",pageInfo);
                return "/admin/userAdmin";
            }
        }
        return "redirect:/admin";
    }

    //跳转到修改用户信息
    @RequestMapping("alterUser")
    public String alterUser(HttpServletResponse httpServletResponse,
                            HttpServletRequest httpServletRequest,
                            Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(value = "userId",required = false)Integer userId){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (password.equals(user.getPassword())) {
                User alter_user = userService.selectByPrimaryKey(userId);
                model.addAttribute("super_user",user);
                model.addAttribute("user",alter_user);
                return "admin/alterUser";
            }
        }
        return "404";
    }

    //更改用户
    @RequestMapping("changeUserProfile")
    public String changeUserProfile(HttpServletResponse httpServletResponse,
                                    HttpServletRequest httpServletRequest,
                                    Model model,
                                    @RequestParam(value = "userId",required = false)Integer userId,
                                    @RequestParam(value = "admin_user_name",required = false)String name,
                                    @RequestParam(value = "admin_user_pass",required = false)String pass,
                                    @RequestParam(value = "user_name",required = false)String username,
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
        return "redirect:/admin/userAdmin";
    }


    @RequestMapping("changeProfile")
    public String changeProfile(HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest,
                                Model model,
                                @RequestParam(value = "user_name",required = true)String username,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(value = "phone",required = false)Integer phone,
                                @RequestParam(value = "email",required = false)String email,
                                @RequestParam(value = "corporation",required = false)String corporation){
        User user = userService.selectByName(username);
        if(corporation=="This"){
            user.setRole_id(1);
        }else{
            user.setRole_id(2);
        }
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setCorporation(corporation);
        userService.updateByName(user);
        return "redirect:/admin";
    }

    //删除用户
    @RequestMapping("deleteUser")
    public String deleteUser(HttpServletResponse httpServletResponse,
                             HttpServletRequest httpServletRequest,
                             Model model,
                             @RequestParam(value = "username",required = false)String name,
                             @RequestParam(value = "password",required = false)String password,
                             @RequestParam(value = "userId",required = false)Integer user_id){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies==null){
            return "redirect:/admin";
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("CookieId")){
                userService.deleteByPrimaryKey(user_id);
                return "redirect:/admin/userAdmin";
            }
        }
        return "redirect:/admin";
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
