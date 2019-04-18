package com.rain.demo.controller;

import com.rain.demo.Service.UserService;
import com.rain.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void changeProfile() {
        User user = userService.selectByPrimaryKey(4);
        user.setPassword("Chiaran@123");
        try{
            userService.updateByPrimaryKey(user);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}