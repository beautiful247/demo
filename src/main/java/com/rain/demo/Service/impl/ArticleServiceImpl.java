package com.rain.demo.Service.impl;

import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> getAll() {
        return articleMapper.getAll();
    }

    @Override
    public int deleteArt(int id) {
        try{
            articleMapper.deleteByPrimaryKey(id);
            return 0;
        }catch (Exception e){
            System.out.println("Error!");
            return -1;
        }
    }
}
