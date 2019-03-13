package com.rain.demo.Service.impl;

import com.rain.demo.Dao.CommentMapper;
import com.rain.demo.Service.CommentService;
import com.rain.demo.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getAll(int article_id) {
        return commentMapper.getAll(article_id);
    }
}
