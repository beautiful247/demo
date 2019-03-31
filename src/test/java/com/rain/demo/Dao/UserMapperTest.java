package com.rain.demo.Dao;

import com.rain.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGet() throws Exception{
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.toString());
    }

    @Test
    public void selectName() throws Exception{
        User user = userMapper.selectByName("root");
        System.out.println(user.toString());
    }
}