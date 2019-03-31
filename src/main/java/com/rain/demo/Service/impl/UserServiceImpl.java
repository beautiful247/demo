package com.rain.demo.Service.impl;

import com.rain.demo.Dao.UserMapper;
import com.rain.demo.Service.UserService;
import com.rain.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }


    @Override
    public int deleteByPrimaryKey(Integer user_id) {
        return userMapper.deleteByPrimaryKey(user_id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(Integer user_id) {
        return userMapper.selectByPrimaryKey(user_id);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }
}
