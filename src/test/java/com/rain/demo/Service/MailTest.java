package com.rain.demo.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void email(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("17726110197@163.com");
        message.setSubject("Hello");
        message.setTo("1275798794@qq.com");
        message.setText("Nice to meet you!");
        try{
            javaMailSender.send(message);
            System.out.println("Send successful!");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
