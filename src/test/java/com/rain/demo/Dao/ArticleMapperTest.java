package com.rain.demo.Dao;

import com.rain.demo.entity.Article;
import com.rain.demo.entity.User_Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void get(){
        System.out.println(articleMapper.selectByPrimaryKey(1).toString());
    }



    @Test
    public void select(){
        Article article = articleMapper.selectByPrimaryKey(10);
        System.out.println(article.toString());
    }

    @Test
    public void user_article(){
        User_Article user_article = new User_Article(1,
                "admin",
                1,
                "Hi",
                1,
                new Date(),
                0,
                0,
                "Hello",
                "Sure");
        System.out.println(user_article.toString());
    }
}