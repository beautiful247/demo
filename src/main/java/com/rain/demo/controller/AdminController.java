package com.rain.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.Service.CommentService;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Register;
import com.rain.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.List;


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

    @RequestMapping("registerAdmin")
    public String registerAdmin(Model model,
                                @RequestParam(value = "username",required = false)String name,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum) {
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
                model.addAttribute("user", user);
                PageHelper.startPage(pageNum,10);
                List<Register> registers = registerService.getAll();
                PageInfo<Register> pageInfo = new PageInfo<Register>(registers);
                model.addAttribute("pageInfo",pageInfo);
                return "admin/registerAdmin";
            }
        }
        return "404";

    }

    @RequestMapping("agreeRegister")
    public String agreeRegister(Model model,
                                @RequestParam(value = "username",required = false)String name,
                                @RequestParam(value = "password",required = false)String password,
                                @RequestParam(value = "registerId",required = false)Integer register_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
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
                return "admin";
            }
        }
        return "404";
    }

    @RequestMapping("denyRegister")
    public String denyRegister(Model model,
                               @RequestParam(value = "username",required = false)String name,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(value = "registerId",required = false)Integer register_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
                Register register = registerService.selectByPrimaryKey(register_id);
                register.setStatus(2);
                return "admin";
            }
        }
        return "404";
    }

    @RequestMapping("userAdmin")
    public String userAdmin(Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
       User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
                model.addAttribute("user",user);
                List<User> users = userService.getAll();
                PageHelper.startPage(pageNum,10);
                List<Register> registers = registerService.getAll();
                PageInfo<Register> pageInfo = new PageInfo<Register>(registers);
                model.addAttribute("pageInfo",pageInfo);
                return "/admin/userAdmin";
            }
        }
        return "404";
    }

    @RequestMapping("alterUser")
    public String alterUser(Model model,
                            @RequestParam(value = "username",required = false)String name,
                            @RequestParam(value = "password",required = false)String password,
                            @RequestParam(value = "userId",required = false)Integer user_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
                User alter_user = userService.selectByPrimaryKey(user_id);
                model.addAttribute("alter_user",alter_user);
                return "admin/alterUser";
            }
        }
        return "404";
    }

    @RequestMapping("deleteUser")
    public String deleteUser(Model model,
                             @RequestParam(value = "username",required = false)String name,
                             @RequestParam(value = "password",required = false)String password,
                             @RequestParam(value = "userId",required = false)Integer user_id){
        User user = userService.selectByName(name);
        if (user == null) {
            return "redirect:/admin";
        } else {
            if (user.getRole_id()==1&&password.equals(user.getPassword())) {
                userService.deleteByPrimaryKey(user_id);
                return "redirect:/admin/userAdmin?username=name&password=password";
            }
        }
        return "404";
    }


}
