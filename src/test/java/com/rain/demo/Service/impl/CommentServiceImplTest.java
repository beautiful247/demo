package com.rain.demo.Service.impl;

import com.rain.demo.Service.CommentService;
import com.rain.demo.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void inser(){
        Comment comment = new Comment();
        comment.setUser_name("apple");
        comment.setContent("Hello");
        comment.setComm_time(new Date());
        comment.setArticle_id(1);
        commentService.insert(comment);
    }

    @Test
    public void getAll() {
        List<Comment> comments = commentService.getAll(1);
        for(int i=0;i<comments.size();i++){
            System.out.println(comments.get(i).toString());
        }
    }
}