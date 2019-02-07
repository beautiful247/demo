package com.rain.demo.Service;

import com.rain.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User selectByName(String name);
}
