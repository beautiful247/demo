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
    public void insert(){
        Article article = new Article();
        article.setTitle("md insert");
        article.setUser_id(1);
        article.setCategory_id(1);
        article.setPost_time(new Date());
        article.setContent("This is a md insert test!");
        article.setLikes(0);
        article.setComment_account(0);
        article.setMd_content("This is a md insert test!");
        if(articleMapper.insert(article)==0){
            System.out.println("Fai");
        }else{
            System.out.println("Suc");
        }
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