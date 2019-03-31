package com.rain.demo.Dao;

import com.rain.demo.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testAll(){
        List<Category> cats = categoryMapper.getAllCat();
        for(int i=0;i<cats.size();i++){
            System.out.println(cats.get(i).toString());
        }
    }
}