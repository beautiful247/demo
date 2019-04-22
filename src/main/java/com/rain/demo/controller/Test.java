package com.rain.demo.controller;

import com.rain.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class Test {

    @RequestMapping("test")
    public String a(){
        return "test1";
    }

    @RequestMapping("test1")
    public String test(HttpServletResponse httpServletResponse,
                       Model model,
                       @RequestParam(value = "user",required = false)String name,
                       @RequestParam(value = "autoLogin",required = false)boolean a){
        Cookie cookie = new Cookie("CookieId",name);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        httpServletResponse.addCookie(cookie);
        if(a){
            model.addAttribute("user","yes");
            return "test2";
        }
        return "redirect:/test2";
    }

    @RequestMapping("test2")
    public String testa(HttpServletRequest httpServletRequest,
                   Model model){
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println(cookie.getValue());
                if(cookie.getName().equals("CookieId")){
                    model.addAttribute("user",cookie.getValue());
                }
            }
        }

        return "test2";
    }
}
