package com.rain.demo.Service.impl;

import com.rain.demo.Dao.RegisterMapper;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.entity.Register;
import com.rain.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public List<Register> getAllReg() {
        return registerMapper.getAllReg();
    }

    @Override
    public int insert(Register record) {
        return registerMapper.insert(record);
    }

    @Override
    public Register selectByPrimaryKey(Integer regis_id) {
        return registerMapper.selectByPrimaryKey(regis_id);
    }

    @Override
    public void sendMail(String destination, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("17726110197@163.com");
        message.setSubject("Hello");
        message.setTo(user.getEmail());
        String text = "Nice to meet you:"+user.getName()+"! You are allowed to be registered to be a member of this site! We appreicate it that you can create beautiful things in the future!";
        message.setText(text);
        try{
            javaMailSender.send(message);
            System.out.println("Send successful!");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
