package com.rain.demo.Service.impl;

import com.rain.demo.Dao.CategoryMapper;
import com.rain.demo.Service.CategoryService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCat() {
        return categoryMapper.getAllCat();
    }

    @Override
    public Category selectByPrimaryKey(Integer category_id) {
        return categoryMapper.selectByPrimaryKey(category_id);
    }
}
