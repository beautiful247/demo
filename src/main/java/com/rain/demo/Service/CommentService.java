package com.rain.demo.Service;

import com.rain.demo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    int delectByPrimaryKey(Integer comment_id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer comment_id);

    int updateByPrimaryKey(Comment record);

    List<Comment> getAll(int article_id);
}
