package com.rain.demo.Service.impl;

import com.rain.demo.Dao.RegisterMapper;
import com.rain.demo.Service.RegisterService;
import com.rain.demo.entity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;

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
}
