package com.rain.demo.Service.impl;

import com.rain.demo.Service.ArticleService;
import com.rain.demo.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void updateByPrimaryKey() {
        Article article = articleService.selectByPrimaryKey(5);
        article.setComment_account(0);
        article.setCategory(2);
        articleService.updateByPrimaryKey(article);
    }

    @Test
    public void postArticle(){
        Article article = new Article();
        article.setTitle("Style Changed");
        article.setCategory(1);
        article.setAuthor("admin");
        article.setLikes(0);
        article.setComment_account(0);
        article.setContent("<h1>Style Changed</h1>");
        article.setMd_content("# Style");
        article.setPost_time(new Date());
        article.setDigit("How to change style");
        articleService.insert(article);
    }
}