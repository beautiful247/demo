package com.rain.demo.Service;

import com.rain.demo.Dao.RegisterMapper;
import com.rain.demo.entity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegisterService {
    List<Register> getAll();

    int insert(Register record);

    Register selectByPrimaryKey(Integer regis_id);
}
