package com.rain.demo.Service;

import com.rain.demo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> getAll(int article_id);
}
