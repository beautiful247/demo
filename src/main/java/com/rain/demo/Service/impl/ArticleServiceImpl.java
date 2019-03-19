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
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAll() {
        return articleMapper.getAll();
    }

    @Override
    public Article selectByPrimaryKey(Integer art_id) {
        return articleMapper.selectByPrimaryKey(art_id);
    }

    @Override
    public int deleteArt(int art_id) {
        return articleMapper.deleteByPrimaryKey(art_id);
    }

    @Override
    public List<Article> selectByAuthor(String author) {
        return articleMapper.selectByAuthor(author);
    }
}
