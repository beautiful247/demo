package com.rain.demo.Service;

import com.rain.demo.entity.Article;
import com.rain.demo.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCat();

    Category selectByPrimaryKey(Integer category_id);
}
