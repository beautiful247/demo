package com.rain.demo.controller;

import com.rain.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class Test {

    @RequestMapping("test")
    public String a(HttpSession httpSession,
                    HttpServletRequest httpServletRequest,
                    Model model){
        HttpSession h = httpServletRequest.getSession();
        if(h.getAttribute("user")!=null){
            model.addAttribute("user",h.getAttribute("user"));
            return "test2";
        }
        return "test1";
    }

    @RequestMapping("test1")
    public String test(HttpServletResponse httpServletResponse,
                       HttpServletRequest httpServletRequest,
                       HttpSession httpSession,
                       Model model,
                       @RequestParam(value = "user",required = false)String name,
                       @RequestParam(value = "autoLogin",required = false)boolean a){
        httpSession.setAttribute("user",name);
        return "redirect:/test2";
    }

    @RequestMapping("test2")
    public String testa(HttpServletRequest httpServletRequest,
                        HttpSession httpSession,
                        Model model){
        model.addAttribute("user",httpServletRequest.getSession().getAttribute("user"));
        return "test2";
    }
}
