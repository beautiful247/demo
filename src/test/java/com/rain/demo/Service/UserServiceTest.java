package com.rain.demo.Service;

import com.rain.demo.Dao.UserMapper;
import com.rain.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select(){
        List<User> users = userService.getAll();
        for(int i=0;i<users.size();i++){
            System.out.println(users.get(i).toString());
        }
    }
}