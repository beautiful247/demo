package com.rain.demo.Service;

import com.rain.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    int deleteByPrimaryKey(Integer user_id);

    int insert(User record);

    User selectByPrimaryKey(Integer user_id);

    int updateByPrimaryKey(User record);

    User selectByName(String name);

    List<User> getAll();

    int updateByName(User record);
}
