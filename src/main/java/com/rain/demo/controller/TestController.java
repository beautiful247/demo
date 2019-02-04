package com.rain.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("dataTest")
    public String test(Model model, @RequestParam(value = "username",required = false)String name){
        if(name==null){
            return "test/dataTest";
        }else{
            model.addAttribute("name",name);
            return "test/dataTest2";
        }
    }
}
