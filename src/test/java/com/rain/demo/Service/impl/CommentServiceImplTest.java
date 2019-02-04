package com.rain.demo.Service.impl;

import com.rain.demo.Service.CommentService;
import com.rain.demo.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void getAll() {
        List<Comment> comments = commentService.getAll(1);
        for(int i=0;i<comments.size();i++){
            System.out.println(comments.get(i).toString());
        }
    }
}