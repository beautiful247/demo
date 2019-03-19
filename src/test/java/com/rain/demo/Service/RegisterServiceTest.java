package com.rain.demo.Service;

import com.rain.demo.entity.Register;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;

    @Test
    public void getAll(){
        List<Register> test = registerService.getAll();
        for(int i=0;i<test.size();i++){
            System.out.println(test.get(i).toString());
        }
    }

    @Test
    public void insert(){
        Register register = new Register();
        register.setCorporation("facebook");
        register.setDetail("Hello");
        register.setEmail("facebook@facebook.com");
        register.setName("facebook");
        register.setPassword("facebook");
        register.setPhone(123456);
        register.setStatus(0);
        try{
            registerService.insert(register);
        }catch (Exception e){
            System.out.println(e);
        }
    }

}